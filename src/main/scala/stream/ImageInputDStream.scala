package stream

import org.apache.hadoop.io.BytesWritable
import org.apache.spark.Logging
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.receiver.Receiver

/**
  * Created by root on 11/14/17.
  */
class ImageInputDStream(@transient ssc_ : StreamingContext,host: String,port:
Int,storageLevel: StorageLevel) extends
  ReceiverInputDStream[BytesWritable](ssc_) {
  override def getReceiver(): Receiver[BytesWritable] = {
    new ImageReceiver(host,port,storageLevel)
  }
}
