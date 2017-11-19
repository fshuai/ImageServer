package stream

import hist.Hist
import org.apache.hadoop.io.{BytesWritable, Text}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import shot.HistData
import utils.ImageFileOutputFormat

import scalautils.LoggerLevels

/**
  * Created by root on 11/15/17.
  */
object StreamingHist {

  def imageModel(image:BytesWritable):HistData={
    var h=new Hist(image.getBytes)
    val result=h.getHist
    result
  }

  def main(args: Array[String]): Unit = {
    LoggerLevels.setStreamingLogLevels()
    if (args.length < 3) {
      System.err.println("Usage: ImageStreaming <seconds> <hostname> <port> <hdfs_path>")
      System.exit(1)
    }
    val s = new SparkConf().setMaster("local[2]").setAppName("StreamingHist")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    val sc = new SparkContext(s)
    val ssc = new StreamingContext(sc, Seconds(args(0).toInt))
    val img = new ImageInputDStream(ssc, args(1), args(2).toInt,
      StorageLevel.MEMORY_AND_DISK_SER)//调用重写的 ImageInputDStream 方法读取图片
    val imgMap = img.map(x => (new Text(System.currentTimeMillis().toString), x))
//    imgMap.saveAsNewAPIHadoopFiles("hdfs://master:9000/user/fshuai", "", classOf[Text],
//      classOf[BytesWritable], classOf[ImageFileOutputFormat],
//      ssc.sparkContext.hadoopConfiguration)//调用 ImageFileOutputFormat 方法写入图片

    imgMap.map(x => (x._1, {
      if (x._2.getLength > 0) imageModel(x._2) else "-1"
    }))//获取 key 的值，即图片
      .filter(x => x._2 != "0" && x._2 != "-1")
      .map(x => "{time:" + x._1.toString +","+ x._2 + "},").print()


    ssc.start()
    ssc.awaitTermination()

  }

}
