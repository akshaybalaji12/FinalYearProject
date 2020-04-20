package project.akshay.finalyear.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.akshay.finalyear.R;

public class CustomerActivity extends AppCompatActivity {

    @BindView(R.id.qrCodeImage)
    ImageView qrCodeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        ButterKnife.bind(this);

        qrCodeImage.setOnClickListener(view -> {

            startActivity(new Intent(CustomerActivity.this, ScanQRActivity.class));
            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);

        });

    }
}
