package project.akshay.finalyear.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.akshay.finalyear.Model.Product;
import project.akshay.finalyear.R;
import project.akshay.finalyear.Utility.Utilities;

public class CustomerActivity extends AppCompatActivity {

    @BindView(R.id.qrCodeImage)
    ImageView qrCodeImage;

    @BindView(R.id.text)
    TextView text;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.productLayout)
    LinearLayout productLayout;

    @BindView(R.id.productName)
    TextView name;

    @BindView(R.id.caughtAt)
    TextView caughtAt;

    @BindView(R.id.caughtOn)
    TextView caughtOn;

    @BindView(R.id.processedBy)
    TextView processedBy;

    @BindView(R.id.processedOn)
    TextView processedOn;

    @BindView(R.id.method)
    TextView method;

    @BindView(R.id.expiryDate)
    TextView expiry;



    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        ButterKnife.bind(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        qrCodeImage.setOnClickListener(view -> {

            Intent scanQRIntent = new Intent(CustomerActivity.this, ScanQRActivity.class);
            startActivityForResult(scanQRIntent, Utilities.REQUEST_CODE_QR_CODE);
            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Utilities.REQUEST_CODE_QR_CODE) {

            if(resultCode == RESULT_OK) {

                assert data != null;
                getProductDetails(data.getStringExtra(Utilities.STRING_EXTRA_QR_CODE));
                qrCodeImage.setVisibility(View.GONE);
                text.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

            }

        }
    }

    private void getProductDetails(String productID) {

        DatabaseReference myReference = databaseReference.child("products/");

        myReference.child(productID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Product product = dataSnapshot.getValue(Product.class);
                        assert product != null;

                        name.setText(String.format("Fish type : %s" , product.getFishermanDetails().getFishType()));
                        caughtAt.setText(String.format("Caught at : %s", product.getFishermanDetails().getLocation()));
                        caughtOn.setText(String.format("Caught on : %s", product.getFishermanDetails().getDate()));
                        processedBy.setText(String.format("Processed by : %s", product.getProcessorDetails().getName()));
                        processedOn.setText(String.format("Processed on : %s", product.getProcessorDetails().getReceivedDate()));
                        method.setText(String.format("Processing method : %s", product.getProcessorDetails().getProcessingMethod()));
                        expiry.setText(String.format("Expiry date : %s", product.getProcessorDetails().getExpiryDate()));

                        productLayout.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
}
