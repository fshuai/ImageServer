package shot;

import java.io.Serializable;

/**
 * Created by root on 11/18/17.
 */
public class HistData implements Serializable{
    public static final int HISTBINS=16;
    public int[] h;
    public int[] s;
    public int[] v;

    public HistData(){
        h=new int[HISTBINS];
        s=new int[HISTBINS];
        v=new int[HISTBINS];
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        //String result="";
        StringBuffer result=new StringBuffer();
        for(int i=0;i<HISTBINS;i++){
            result.append(h[i]);
            result.append(" ");
        }
        for(int i=0;i<HISTBINS;i++){
            result.append(s[i]);
            result.append(" ");
        }
        for(int i=0;i<HISTBINS;i++){
            result.append(v[i]);
            result.append(" ");
        }
        return result.toString();
    }

}
