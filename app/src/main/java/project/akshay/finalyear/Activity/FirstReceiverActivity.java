package project.akshay.finalyear.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.akshay.finalyear.Model.Product;
import project.akshay.finalyear.Utility.PreferencesManager;
import project.akshay.finalyear.R;
import project.akshay.finalyear.Utility.Utilities;

public class FirstReceiverActivity extends AppCompatActivity {

    @BindView(R.id.welcomeText)
    TextView welcomeText;

    @BindView(R.id.logoutButton)
    MaterialButton logoutButton;

    @BindView(R.id.submitButton)
    MaterialButton submitButton;

    private PreferencesManager preferencesManager;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_receiver);

        ButterKnife.bind(this);

        preferencesManager = new PreferencesManager(this);

        welcomeText.setText(preferencesManager.getUserName());

        Log.d("LandingActivity","First Receiver");

        logoutButton.setOnClickListener(view -> {

            preferencesManager.clearSharedPreferences();
            startActivity(new Intent(FirstReceiverActivity.this, LoginActivity.class));
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            finish();

        });

        submitButton.setOnClickListener(view -> {

            databaseReference = FirebaseDatabase.getInstance().getReference();
            DatabaseReference productReference = databaseReference.child("products/").child("27SMZJ2LHV");

            productReference
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Product product = dataSnapshot.getValue(Product.class);
                            assert product != null;

                            product.getSupplyChainArray().add(preferencesManager.getUserID());

                            productReference
                                    .child("supplyChainArray")
                                    .setValue(product.getSupplyChainArray());

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

        });

    }
}
