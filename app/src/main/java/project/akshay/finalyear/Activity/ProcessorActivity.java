package project.akshay.finalyear.Activity;

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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

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
import project.akshay.finalyear.Utility.PreferencesManager;
import project.akshay.finalyear.R;
import project.akshay.finalyear.Utility.Utilities;

public class ProcessorActivity extends AppCompatActivity {

    @BindView(R.id.welcomeText)
    TextView welcomeText;

    @BindView(R.id.logoutButton)
    MaterialButton logoutButton;

    @BindView(R.id.generateButton)
    MaterialButton generateButton;

    @BindView(R.id.qrImageView)
    ImageView qrImageView;

    private PreferencesManager preferencesManager;
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

        generateButton.setOnClickListener(view -> {

            int dimensionQR = Utilities.getQRDimension((WindowManager) Objects.requireNonNull(getSystemService(WINDOW_SERVICE)));
            qrgEncoder = new QRGEncoder("27SMZJ2LHV", null, QRGContents.Type.TEXT, dimensionQR);

            try {

                qrBitmap = qrgEncoder.getBitmap();
                qrImageView.setImageBitmap(qrBitmap);

            } catch (Exception e) {

                Log.d("QR Encoder exception ", Objects.requireNonNull(e.getMessage()));

            }

            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                /*try {
                    boolean save = new QRGSaver().save(savePath, "Akshay", qrBitmap, QRGContents.ImageType.IMAGE_JPEG);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

                try {
                    saveImage(qrBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                ActivityCompat.requestPermissions(ProcessorActivity.this , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }

        });

    }

    private void saveImage(Bitmap qrBitmap) throws IOException {

        OutputStream fos;
        String storagePath = Environment.DIRECTORY_PICTURES + File.separator + "Project";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            ContentResolver resolver = getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "27SMZJ2LHV" + ".jpg");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, storagePath);
            Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));

        } else {
            File image = new File(storagePath, "27SMZJ2LHV" + ".jpg");
            fos = new FileOutputStream(image);
        }

        qrBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        Objects.requireNonNull(fos).close();

    }
}
