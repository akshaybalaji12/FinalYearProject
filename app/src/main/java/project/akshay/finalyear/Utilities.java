package project.akshay.finalyear;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Toast;

import java.lang.reflect.Field;

public class Utilities {

    private static final int ID_PROCESSOR = 120;
    private static final int ID_FISHERMAN = 122;

    public static void setDefaultFont(Context context,
                                      String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    private static void replaceFont(String staticTypefaceFieldName,
                                   final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void notifyUser(Context context, String message) {

        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();

    }

}
