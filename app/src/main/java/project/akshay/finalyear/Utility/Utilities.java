package project.akshay.finalyear.Utility;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.reflect.Field;

import static android.content.Context.WINDOW_SERVICE;

public class Utilities {

    public static final int ID_FISHERMAN = 120;
    public static final int ID_FIRST_RECEIVER = 122;
    public static final int ID_PROCESSOR = 124;
    public static final int  ID_DISTRIBUTOR = 126;
    public static final int ID_SHOP = 128;

    public static final int REQUEST_CODE_QR_CODE = 25;

    public static final String STRING_EXTRA_QR_CODE = "STRING_EXTRA_QR_CODE";

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

    public static int getQRDimension(WindowManager manager) {

        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = Math.min(width, height);
        smallerDimension = smallerDimension * 3 / 4;

        return smallerDimension;

    }

}
