package project.akshay.finalyear;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class ProjectApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utilities.setDefaultFont(this, "SERIF", "fonts/Product-Sans-Regular.ttf");
        FirebaseApp.initializeApp(this);

    }
}
