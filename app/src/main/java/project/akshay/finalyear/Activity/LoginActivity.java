package project.akshay.finalyear.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.akshay.finalyear.Utility.PreferencesManager;
import project.akshay.finalyear.R;
import project.akshay.finalyear.Model.User;
import project.akshay.finalyear.Utility.Utilities;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.parentLayout)
    LinearLayout parentLayout;

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
                    .addOnFailureListener(e -> {

                        if(e instanceof FirebaseNetworkException) {

                            Snackbar.make(parentLayout, "Check your internet connection.", Snackbar.LENGTH_INDEFINITE)
                                    .setAction(R.string.retry, view1 -> {

                                        signUpButton.callOnClick();

                                    })
                                    .show();

                        } else if(e instanceof FirebaseAuthException) {

                            String errorCode = ((FirebaseAuthException) e).getErrorCode();

                            switch (errorCode) {

                                case "ERROR_INVALID_EMAIL":
                                    emailEditText.setError("The email address is badly formatted.");
                                    emailEditText.requestFocus();
                                    break;

                                case "ERROR_WRONG_PASSWORD":
                                    passwordEditText.setError("The password is invalid.");
                                    passwordEditText.requestFocus();
                                    break;

                                case "ERROR_INVALID_CREDENTIAL":
                                    Utilities.notifyUser(LoginActivity.this, "The credentials has either expired or is invalid.");
                                    break;

                                case "ERROR_USER_NOT_FOUND":
                                    Utilities.notifyUser(LoginActivity.this, "You're not yet registered.");
                                    break;

                                case "ERROR_USER_DISABLED":
                                    Utilities.notifyUser(LoginActivity.this, "This account has been disabled.");
                                    break;

                            }

                        }

                    });

        });

        signUpButton.setOnClickListener(view -> {

            startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);

        });

    }

    private void updateScreen(int userType) {

        /*startActivity(new Intent(LoginActivity.this, CustomerActivity.class));
        overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
        finish();*/

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
