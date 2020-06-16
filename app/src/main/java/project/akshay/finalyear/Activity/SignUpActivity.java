package project.akshay.finalyear.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import project.akshay.finalyear.Fragment.BottomDialogFragment;
import project.akshay.finalyear.Interface.FragmentInterface;
import project.akshay.finalyear.R;
import project.akshay.finalyear.Model.User;
import project.akshay.finalyear.Utility.Utilities;

public class SignUpActivity extends AppCompatActivity implements FragmentInterface {

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private BottomDialogFragment bottomDialogFragment;

    TextView titleText;

    @BindView(R.id.parentLayout)
    RelativeLayout parentLayout;

    @BindView(R.id.nameText)
    TextInputEditText nameText;

    @BindView(R.id.emailText)
    TextInputEditText emailText;

    @BindView(R.id.mobText)
    TextInputEditText mobText;

    @BindView(R.id.userRole)
    TextInputEditText userTypeText;

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
    int userType = 0;

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

        userTypeText.setOnClickListener(view -> {

            bottomDialogFragment = new BottomDialogFragment(SignUpActivity.this);
            bottomDialogFragment.setFragmentInterface(SignUpActivity.this);
            bottomDialogFragment.show(getSupportFragmentManager(), "BottomDialogFragment");

        });

        signUpButton.setOnClickListener(view -> {

            toggleButtonAnimation(true);

            name = nameText.getText().toString();
            email = emailText.getText().toString();
            mob = mobText.getText().toString();
            password = passwordText.getText().toString();
            confPassword = confPasswordText.getText().toString();

            if(!checkEmptyTexts()) {

                if(password.equals(confPassword) && userType != 0) {

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(task -> {

                                if(task.isSuccessful()) {

                                    User user = new User(name,mob,email,userType);
                                    databaseReference
                                            .child("users")
                                            .child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                                            .setValue(user);

                                    Utilities.notifyUser(SignUpActivity.this, "Account created");
                                    onBackPressed();

                                }

                            })
                            .addOnFailureListener(e -> {

                                if(e instanceof FirebaseAuthException) {

                                    String errorCode = ((FirebaseAuthException) e).getErrorCode();

                                    switch (errorCode) {

                                        case "ERROR_INVALID_EMAIL":
                                            emailText.setError("The email address is badly formatted.");
                                            emailText.requestFocus();
                                            break;

                                        case "ERROR_EMAIL_ALREADY_IN_USE":
                                            emailText.setError("The email address is already in use by another account.");
                                            emailText.requestFocus();
                                            break;

                                    }

                                } else if(e instanceof FirebaseNetworkException) {

                                    Snackbar.make(parentLayout, "Check your internet connection.", Snackbar.LENGTH_INDEFINITE)
                                            .setAction(R.string.retry, view1 -> signUpButton.callOnClick())
                                            .show();

                                }

                                toggleButtonAnimation(false);

                            });

                } else {

                    toggleButtonAnimation(false);

                    if(userType == 0) {

                        userTypeText.callOnClick();

                    } else {

                        confPasswordText.setError("Password does not match", null);
                        confPasswordText.requestFocus();

                    }

                }

            } else {

                toggleButtonAnimation(false);

            }

        });

    }

    private boolean checkEmptyTexts() {

        String errorText = "Cannot not be empty.";

        if(name.equals("")) {
            nameText.setError(errorText);
        }

        if(email.equals("")) {
            emailText.setError(errorText);
        }

        if(mob.equals("")) {
            mobText.setError(errorText);
        }

        if(password.equals("")) {
            passwordText.setError(errorText, null);
        }

        return name.equals("") || email.equals("") || mob.equals("") || password.equals("");

    }

    private void toggleButtonAnimation(boolean toggle) {

        TransitionManager.beginDelayedTransition(buttonLayout);

        if (toggle) {
            signUpProgress.setVisibility(View.VISIBLE);
            signUpButton.setVisibility(View.GONE);
        } else {
            signUpProgress.setVisibility(View.GONE);
            signUpButton.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }

    @Override
    public void callBackMethod(String result) {

        bottomDialogFragment.dismiss();
        userTypeText.setText(result);

        int pos = Utilities.userRoles.indexOf(result);
        userType = Utilities.userRoleID.get(pos);

    }

}
