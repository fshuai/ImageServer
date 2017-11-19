package utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by root on 11/14/17.
 */
public class ImageFileOutFormat extends FileOutputFormat<Text,BytesWritable> {

    @Override
    public org.apache.hadoop.mapreduce.RecordWriter<Text, BytesWritable>
            getRecordWriter(TaskAttemptContext context) throws IOException, InterruptedException {

        Configuration configuration=context.getConfiguration();
        Path path=getDefaultWorkFile(context,"");
        FileSystem fileSystem=path.getFileSystem(configuration);
        FSDataOutputStream out=fileSystem.create(path,false);
        return new ImageFileRecordWriter(out);
    }

    protected class ImageFileRecordWriter extends RecordWriter<Text,BytesWritable>{

        protected DataOutputStream out=null;
        private final byte[] keyValueSeperator;
        private static final String colon=",";

        public ImageFileRecordWriter(String keyValueSeperator,DataOutputStream out){
            this.keyValueSeperator=keyValueSeperator.getBytes();
            this.out=out;
        }

        public ImageFileRecordWriter(DataOutputStream out){
            this(colon,out);
        }

        @Override
        public void write(Text text, BytesWritable bytesWritable) throws IOException, InterruptedException {
            if(bytesWritable!=null){
                out.write(bytesWritable.getBytes());
            }
        }

        @Override
        public void close(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
            out.close();
        }
    }
}


