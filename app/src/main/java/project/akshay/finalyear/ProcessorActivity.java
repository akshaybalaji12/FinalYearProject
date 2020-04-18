package project.akshay.finalyear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProcessorActivity extends AppCompatActivity {

    @BindView(R.id.welcomeText)
    TextView welcomeText;

    @BindView(R.id.logoutButton)
    MaterialButton logoutButton;

    PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processor);

        ButterKnife.bind(this);

        preferencesManager = new PreferencesManager(this);

        welcomeText.setText(preferencesManager.getUserName());

        Log.d("LandingActivity","Processor");

        logoutButton.setOnClickListener(view -> {

            preferencesManager.clearSharedPreferences();
            startActivity(new Intent(ProcessorActivity.this, LoginActivity.class));
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            finish();

        });

    }
}
