package project.akshay.finalyear;

import android.app.Application;

import com.google.firebase.FirebaseApp;

import project.akshay.finalyear.Utility.Utilities;

public class ProjectApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utilities.setDefaultFont(this, "SERIF", "fonts/Product-Sans-Regular.ttf");
        FirebaseApp.initializeApp(this);

    }
}
