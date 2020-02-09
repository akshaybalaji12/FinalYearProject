package project.akshay.finalyear;

import android.app.Application;

public class ProjectApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utilities.setDefaultFont(this, "SERIF", "fonts/Product-Sans-Regular.ttf");

    }
}
