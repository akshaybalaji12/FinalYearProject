package project.akshay.finalyear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FishermenActivity extends AppCompatActivity {

    @BindView(R.id.welcomeText)
    TextView welcomeText;

    @BindView(R.id.logoutButton)
    MaterialButton logoutButton;

    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;

    @BindView(R.id.addLayout)
    LinearLayout addLayout;

    @BindView(R.id.formLayout)
    LinearLayout formLayout;

    PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fishermen);

        ButterKnife.bind(this);

        preferencesManager = new PreferencesManager(this);

        welcomeText.setText(preferencesManager.getUserName());

        Log.d("LandingActivity","Fishermen");

        logoutButton.setOnClickListener(view -> {

            preferencesManager.clearSharedPreferences();
            startActivity(new Intent(FishermenActivity.this, LoginActivity.class));
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            finish();

        });

        addLayout.setOnClickListener(view -> {

            TransitionManager.beginDelayedTransition(linearLayout);
            addLayout.setVisibility(View.GONE);
            formLayout.setVisibility(View.VISIBLE);

        });

    }
}
