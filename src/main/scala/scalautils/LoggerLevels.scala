package scalautils

import org.apache.log4j.{Level, Logger}
import org.apache.spark.Logging

/**
  * Created by root on 11/17/17.
  */
object LoggerLevels extends Logging{

  def setStreamingLogLevels(): Unit ={
    val log4jInitialized=Logger.getRootLogger.getAllAppenders.hasMoreElements
    if(!log4jInitialized){
      logInfo("setting log level to [WARN] for streaming example."+
        "to override add a custom log4j.properties to the classpath.")
      Logger.getRootLogger.setLevel(Level.WARN)
    }
  }

}
