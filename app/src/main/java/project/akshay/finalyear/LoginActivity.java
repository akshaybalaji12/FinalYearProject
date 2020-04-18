package project.akshay.finalyear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.loginProgress)
    ProgressBar loginProgress;

    @BindView(R.id.loginButton)
    MaterialButton loginButton;

    @BindView(R.id.signUpButton)
    MaterialButton signUpButton;

    @BindView(R.id.loginLayout)
    LinearLayout loginLayout;

    @BindView(R.id.emailText)
    TextInputEditText emailEditText;

    @BindView(R.id.passwordText)
    TextInputEditText passwordEditText;

    @BindView(R.id.emailTextLayout)
    TextInputLayout emailLayout;

    @BindView(R.id.passwordTextLayout)
    TextInputLayout passwordLayout;

    private FirebaseAuth firebaseAuth;
    private PreferencesManager preferencesManager;
    private DatabaseReference databaseReference;

    String emailAddress;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        preferencesManager = new PreferencesManager(this);

        if(preferencesManager.getIsLoggedIn()) {

            updateScreen(preferencesManager.getUserType());

        }

        ButterKnife.bind(this);

        loginButton.setOnClickListener(view -> {

            TransitionManager.beginDelayedTransition(loginLayout);
            loginProgress.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.GONE);

            emailAddress = Objects.requireNonNull(emailEditText.getText()).toString();
            password = Objects.requireNonNull(passwordEditText.getText()).toString();

            firebaseAuth.signInWithEmailAndPassword(emailAddress,password)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {

                            databaseReference = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference myReference = databaseReference.child("users/");

                            final String userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

                            myReference.child(userID)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            User user = dataSnapshot.getValue(User.class);
                                            assert user != null;
                                            preferencesManager.setIsLoggedIn(true);
                                            preferencesManager.setUserType(user.getUserType());
                                            preferencesManager.setUserName(user.getUserName());
                                            preferencesManager.setUserID(userID);
                                            updateScreen(user.getUserType());

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                        } else {
                            Utilities.notifyUser(LoginActivity.this,"Something went wrong, try again!");
                        }
                    })
                    .addOnFailureListener(e -> Utilities.notifyUser(LoginActivity.this, e.getLocalizedMessage()));

        });

        signUpButton.setOnClickListener(view -> {

            startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);

        });

    }

    private void updateScreen(int userType) {

        switch (userType) {

            case Utilities.ID_PROCESSOR:
                startActivity(new Intent(LoginActivity.this, ProcessorActivity.class));
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                finish();
                break;

            case Utilities.ID_FISHERMAN:
                startActivity(new Intent(LoginActivity.this, FishermenActivity.class));
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                finish();
                break;

            case Utilities.ID_DISTRIBUTOR:
                startActivity(new Intent(LoginActivity.this, DistributorActivity.class));
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                finish();
                break;

            case Utilities.ID_FIRST_RECEIVER:
                startActivity(new Intent(LoginActivity.this, FirstReceiverActivity.class));
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                finish();
                break;

            case Utilities.ID_SHOP:
                startActivity(new Intent(LoginActivity.this, ShopActivity.class));
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                finish();
                break;

        }

    }
}
