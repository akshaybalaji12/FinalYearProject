package project.akshay.finalyear.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.akshay.finalyear.Model.DistributorDetails;
import project.akshay.finalyear.Model.Product;
import project.akshay.finalyear.R;
import project.akshay.finalyear.Utility.PreferencesManager;

public class DistributorActivity extends AppCompatActivity {

    @BindView(R.id.submitButton)
    MaterialButton submitButton;

    @BindView(R.id.welcomeText)
    TextView welcomeText;

    @BindView(R.id.logoutButton)
    MaterialButton logoutButton;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.productIDText)
    TextView productID;

    @BindView(R.id.companyText)
    TextView name;

    @BindView(R.id.dateReceived)
    TextView date;

    @BindView(R.id.deliveredTo)
    TextView deliveredTo;

    private PreferencesManager preferencesManager;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor);

        ButterKnife.bind(this);
        preferencesManager = new PreferencesManager(this);

        welcomeText.setText(preferencesManager.getUserName());

        logoutButton.setOnClickListener(view -> {

            preferencesManager.clearSharedPreferences();
            startActivity(new Intent(DistributorActivity.this, LoginActivity.class));
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            finish();

        });

        submitButton.setOnClickListener(view -> {

            submitButton.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            databaseReference = FirebaseDatabase.getInstance().getReference();
            DatabaseReference productReference = databaseReference.child("products/").child(productID.getText().toString());

            DistributorDetails distributorDetails = new DistributorDetails(
                    preferencesManager.getUserID(),
                    name.getText().toString(),
                    date.getText().toString(),
                    deliveredTo.getText().toString()
            );

            productReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Product product = dataSnapshot.getValue(Product.class);
                    assert product != null;
                    product.setDistributorDetails(distributorDetails);

                    productReference
                            .child("distributorDetails")
                            .setValue(product.getDistributorDetails());

                    Toast.makeText(getApplicationContext(), "Product added", Toast.LENGTH_SHORT).show();

                    name.setText("");
                    date.setText("");
                    deliveredTo.setText("");
                    productID.setText("");

                    progressBar.setVisibility(View.GONE);
                    submitButton.setVisibility(View.VISIBLE);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        });

    }
}
