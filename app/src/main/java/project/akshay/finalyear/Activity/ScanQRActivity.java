package project.akshay.finalyear.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.Collections;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import project.akshay.finalyear.R;
import project.akshay.finalyear.Utility.Utilities;

public class ScanQRActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView qrCodeScanner;
    private static final int MY_CAMERA_PERMISSION = 128;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        qrCodeScanner = findViewById(R.id.qrScannerView);
        setScannerProps();

    }

    private void setScannerProps() {

        qrCodeScanner.setFormats(Collections.singletonList(BarcodeFormat.QR_CODE));
        qrCodeScanner.setAutoFocus(true);
        qrCodeScanner.setLaserColor(R.color.colorAccent);
        qrCodeScanner.setMaskColor(R.color.colorAccent);
        if(Build.MANUFACTURER.equalsIgnoreCase("HUAWEI")) {
            qrCodeScanner.setAspectTolerance(0.5f);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION);
            }
        }

        qrCodeScanner.startCamera();
        qrCodeScanner.setResultHandler(this);

    }

    @Override
    public void handleResult(Result rawResult) {

        if(rawResult != null) {
            qrCodeScanner.stopCamera();
            Intent intent = new Intent();
            intent.putExtra(Utilities.STRING_EXTRA_QR_CODE,rawResult.getText());
            setResult(RESULT_OK, intent);
            finish();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeScanner.stopCamera();
    }
}
