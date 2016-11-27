package project.weabank.com.ubcmbs.helpers;

import android.content.Context;
import android.media.MediaPlayer;

import project.weabank.com.ubcmbs.R;

/**
 * Created by Kira on 11/27/2016.
 */

public class SoundManager {
    public static MediaPlayer mp = null;
    public final static int COUNTER_BEEP = 0;
    public final static int COUNTER_ERROR = 1;

    public static void playSound(Context ctx, int soundToPlay){

        try{
            if(mp!=null){
                //mp.reset();
                mp.release();
            }
            switch(soundToPlay){
                case COUNTER_BEEP:
                    mp = MediaPlayer.create(ctx, R.raw.counterbeep);
                    break;
                case COUNTER_ERROR:
                    mp = MediaPlayer.create(ctx, R.raw.error);
                    break;
            }
            mp.start();
        }catch(Exception e){
            //EAT MUNA
        }
    }
}
