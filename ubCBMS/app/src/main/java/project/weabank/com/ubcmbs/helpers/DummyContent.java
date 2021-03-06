package project.weabank.com.ubcmbs.helpers;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Random;

import project.weabank.com.ubcmbs.R;
import project.weabank.com.ubcmbs.model.NavModel;

/**
 * Created by Kira on 11/26/2016.
 */

public class DummyContent {

    /**
     * ITEMS on Navigation List
     */
    public static ArrayList<NavModel> getDrawerListIcons() {
        ArrayList<NavModel> list = new ArrayList<>();
        list.add(0,new NavModel(0, "", "My Account", R.mipmap.ic_launcher));
        list.add(1,new NavModel(1, "", "Send Money", R.mipmap.ic_launcher));
        list.add(2,new NavModel(2, "", "Withdraw", R.mipmap.ic_launcher));
        list.add(3,new NavModel(3, "", "My Transactions", R.mipmap.ic_launcher));
        list.add(4,new NavModel(4, "", "Settings", R.mipmap.ic_launcher));
        list.add(5,new NavModel(5, "", "Logout", R.mipmap.ic_launcher));
        return list;
    }

    public static Drawable getNavDrawerDrawable(Context ctx, int whichDrawable) {
        switch (whichDrawable) {
            default:
            case 0:
                return ctx.getResources().getDrawable(R.mipmap.ic_launcher);
            case 1:
                return ctx.getResources().getDrawable(R.mipmap.ic_launcher);
            case 2:
                return ctx.getResources().getDrawable(R.mipmap.ic_launcher);
            case 3:
                return ctx.getResources().getDrawable(R.mipmap.ic_launcher);
            case 4:
                // return null;
                return ctx.getResources().getDrawable(R.mipmap.ic_launcher);
            //return ctx.getResources().getDrawable(R.drawable.ic_default_placeholder);
            case 5:
                return ctx.getResources().getDrawable(R.mipmap.ic_launcher);
        }
    }

    private static final Random RANDOM = new Random();
    public static String getRandomProfileDrawable() {
        switch (RANDOM.nextInt(6)) {
            default:
            case 0:
                return "http://www.anime-planet.com/images/characters/sanji_78.jpg";
            case 1:
                return "https://upload.wikimedia.org/wikipedia/en/a/a4/Roronoa_Zoro.jpg";
            case 2:
                return "http://static.tvtropes.org/pmwiki/pub/images/vegetavwx2014_928.jpg";
            case 3:
                return "https://i.ytimg.com/vi/tB-9mhJPzbI/hqdefault.jpg";
            case 4:
                return "http://vignette1.wikia.nocookie.net/onepiece/images/a/af/Tony_Tony_Chopper_Anime_Post_Timeskip_Infobox.png/revision/latest?cb=20130428202154";
        }
    }
}
