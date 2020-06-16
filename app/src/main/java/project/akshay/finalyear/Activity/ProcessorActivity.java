package project.akshay.finalyear.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;
import butterknife.BindView;
import butterknife.ButterKnife;
import project.akshay.finalyear.Model.ProcessorDetails;
import project.akshay.finalyear.Model.Product;
import project.akshay.finalyear.Utility.PreferencesManager;
import project.akshay.finalyear.R;
import project.akshay.finalyear.Utility.Utilities;

public class ProcessorActivity extends AppCompatActivity {

    @BindView(R.id.welcomeText)
    TextView welcomeText;

    @BindView(R.id.productIDText)
    TextView productID;

    @BindView(R.id.companyText)
    TextView company;

    @BindView(R.id.dateReceived)
    TextView dateReceived;

    @BindView(R.id.processingMethod)
    TextView processing;

    @BindView(R.id.dateDelivered)
    TextView dateDelivered;

    @BindView(R.id.expiryDate)
    TextView expiryDate;

    @BindView(R.id.deliveredTo)
    TextView deliveredTo;

    @BindView(R.id.submitButton)
    MaterialButton submitButton;

    @BindView(R.id.logoutButton)
    MaterialButton logoutButton;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private PreferencesManager preferencesManager;
    private DatabaseReference databaseReference;
    private QRGEncoder qrgEncoder;
    private Bitmap qrBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processor);

        ButterKnife.bind(this);

        preferencesManager = new PreferencesManager(this);

        welcomeText.setText(preferencesManager.getUserName());

        Log.d("LandingActivity","Processor");

        logoutButton.setOnClickListener(view -> {

            preferencesManager.clearSharedPreferences();
            startActivity(new Intent(ProcessorActivity.this, LoginActivity.class));
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            finish();

        });

        submitButton.setOnClickListener(view -> {

            submitButton.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            databaseReference = FirebaseDatabase.getInstance().getReference();
            DatabaseReference productReference = databaseReference.child("products/").child(productID.getText().toString());

            ProcessorDetails processorDetails = new ProcessorDetails(
                    preferencesManager.getUserID(),
                    company.getText().toString(),
                    dateReceived.getText().toString(),
                    processing.getText().toString(),
                    dateDelivered.getText().toString(),
                    expiryDate.getText().toString(),
                    deliveredTo.getText().toString()
            );

            productReference
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Product product = dataSnapshot.getValue(Product.class);
                            assert product != null;
                            product.setProcessorDetails(processorDetails);

                            productReference
                                    .child("processorDetails")
                                    .setValue(product.getProcessorDetails());

                            int dimensionQR = Utilities.getQRDimension((WindowManager) Objects.requireNonNull(getSystemService(WINDOW_SERVICE)));
                            qrgEncoder = new QRGEncoder(productID.getText().toString(), null, QRGContents.Type.TEXT, dimensionQR);

                            try {

                                qrBitmap = qrgEncoder.getBitmap();

                            } catch (Exception e) {

                                Log.d("QR Encoder exception ", Objects.requireNonNull(e.getMessage()));

                            }

                            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                                try {
                                    saveImage(qrBitmap);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                ActivityCompat.requestPermissions(ProcessorActivity.this , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                            }

                            Toast.makeText(getApplicationContext(), "Product added and QR generated", Toast.LENGTH_LONG).show();

                            productID.setText("");
                            company.setText("");
                            dateDelivered.setText("");
                            dateReceived.setText("");
                            processing.setText("");
                            expiryDate.setText("");
                            deliveredTo.setText("");

                            progressBar.setVisibility(View.GONE);
                            submitButton.setVisibility(View.VISIBLE);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


        });

    }

    private void saveImage(Bitmap qrBitmap) throws IOException {

        OutputStream fos;
        String storagePath = Environment.DIRECTORY_PICTURES + File.separator + "Project";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            ContentResolver resolver = getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, productID.getText().toString() + ".jpg");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, storagePath);
            Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));

        } else {

            String imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .toString() + File.separator + "Project";

            File file = new File(imageDir);
            if(!file.exists()) {
                Utilities.printLogMessages("ImageDir created: ", String.valueOf(file.mkdir()));
            }

            File image = new File(imageDir, productID.getText().toString() + ".jpg");
            fos = new FileOutputStream(image);

        }

        qrBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        Objects.requireNonNull(fos).flush();
        Objects.requireNonNull(fos).close();

    }
}
