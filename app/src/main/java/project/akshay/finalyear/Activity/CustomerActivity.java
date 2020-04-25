package project.akshay.finalyear.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

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

                        Utilities.notifyUser(CustomerActivity.this, product.getSupplyChainArray().get(0));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
}
