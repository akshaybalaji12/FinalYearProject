package project.akshay.finalyear.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import project.akshay.finalyear.R;
import project.akshay.finalyear.Model.User;
import project.akshay.finalyear.Utility.Utilities;

public class SignUpActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    TextView titleText;
    //String[] userTypes = {"Fisherman","Processor","Wholesaler","Distributor"};
    @BindView(R.id.nameText)
    TextInputEditText nameText;

    @BindView(R.id.emailText)
    TextInputEditText emailText;

    @BindView(R.id.mobText)
    TextInputEditText mobText;

    @BindView(R.id.passwordText)
    TextInputEditText passwordText;

    @BindView(R.id.confPasswordText)
    TextInputEditText confPasswordText;

    @BindView(R.id.signUpButton)
    MaterialButton signUpButton;

    @BindView(R.id.signUpProgress)
    ProgressBar signUpProgress;

    @BindView(R.id.buttonLayout)
    LinearLayout buttonLayout;

    @BindString(R.string.signUpPageTitle)
    String signUpTitle;

    String name, email, password, confPassword, mob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_app_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        titleText = findViewById(R.id.titleText);
        titleText.setText(signUpTitle);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TransitionManager.beginDelayedTransition(buttonLayout);
                signUpProgress.setVisibility(View.VISIBLE);
                signUpButton.setVisibility(View.GONE);

                name = Objects.requireNonNull(nameText.getText()).toString();
                email = Objects.requireNonNull(emailText.getText()).toString();
                mob = Objects.requireNonNull(mobText.getText()).toString();
                password = Objects.requireNonNull(passwordText.getText()).toString();
                confPassword = Objects.requireNonNull(confPasswordText.getText()).toString();

                if(password.equals(confPassword)) {

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(task -> {

                                if(task.isSuccessful()) {

                                    User user = new User(name,mob,email,124);
                                    databaseReference
                                            .child("users")
                                            .child(firebaseAuth.getCurrentUser().getUid())
                                            .setValue(user);

                                    Utilities.notifyUser(SignUpActivity.this, "Account created");
                                    onBackPressed();

                                }

                            });

                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }
}
