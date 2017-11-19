package decoder;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

/**
 * Created by root on 11/15/17.
 */
public class FrameExtract {

    public static void main(String[] args){
        System.out.println(System.getProperty("java.library.path"));
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String path="/root/Videos/test.mp4";
        process(path);
    }

    private static void process(String path){
        VideoCapture cap=new VideoCapture(path);
        System.out.println(cap.isOpened());
        if(cap.isOpened()){
            double frameCount=cap.get(7);
            System.out.println("the total frame count:"+frameCount);
            double fps=cap.get(5);
            System.out.println("the frame rate is:"+fps);
            //length
            double len=frameCount/fps;
            System.out.println("the video length is:"+len);
            Double d_s=new Double(len);
            System.out.println("length:"+d_s.intValue());
            Mat frame=new Mat();
            for(int i=0;i<d_s.intValue();i++){
                cap.set(0,i*1000);
                if(cap.read(frame)){
                    Highgui.imwrite("/root/image/frame/"+i+".jpg",frame);
                }
            }
            System.out.println("done");
        }
        cap.release();
    }
}
