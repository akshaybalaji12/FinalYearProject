package project.akshay.finalyear.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.akshay.finalyear.Model.Product;
import project.akshay.finalyear.Utility.PreferencesManager;
import project.akshay.finalyear.R;

public class FishermenActivity extends AppCompatActivity {

    @BindView(R.id.welcomeText)
    TextView welcomeText;

    @BindView(R.id.logoutButton)
    MaterialButton logoutButton;

    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;

    @BindView(R.id.addLayout)
    LinearLayout addLayout;

    @BindView(R.id.productIDLayout)
    LinearLayout productIDLayout;

    @BindView(R.id.productIDText)
    TextView productIDText;

    @BindView(R.id.formLayout)
    LinearLayout formLayout;

    @BindView(R.id.submitButton)
    MaterialButton submitButton;

    @BindView(R.id.doneButton)
    MaterialButton doneButton;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private PreferencesManager preferencesManager;
    private DatabaseReference databaseReference;

    private static final String allowedCharacters = "ABCDEFGHIJKLMOPQRSTUVWXYZ1234567890";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fishermen);

        ButterKnife.bind(this);

        preferencesManager = new PreferencesManager(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();

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

        submitButton.setOnClickListener(view -> {

            TransitionManager.beginDelayedTransition(linearLayout);
            formLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            String productID = generateProductID();

            Product newProduct = new Product(productID);
            newProduct.getSupplyChainArray().add(preferencesManager.getUserID());

            //TODO create Product object

            databaseReference
                    .child("products")
                    .child(productID)
                    .setValue(newProduct)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {

                            TransitionManager.beginDelayedTransition(linearLayout);
                            progressBar.setVisibility(View.GONE);
                            productIDLayout.setVisibility(View.VISIBLE);

                            productIDText.setText(productID);

                        }
                    });

            //TODO error handling for all tasks

        });

        doneButton.setOnClickListener(view -> {

            TransitionManager.beginDelayedTransition(linearLayout);
            productIDLayout.setVisibility(View.GONE);
            addLayout.setVisibility(View.VISIBLE);

        });

    }

    private String generateProductID() {

        final Random random = new Random();
        final StringBuilder stringBuilder = new StringBuilder(10);
        for(int i=0;i<10;++i)
            stringBuilder.append(allowedCharacters.charAt(random.nextInt(allowedCharacters.length())));
        return stringBuilder.toString();

    }
}
