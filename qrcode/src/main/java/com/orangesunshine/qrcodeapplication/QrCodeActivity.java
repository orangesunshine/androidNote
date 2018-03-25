package com.orangesunshine.qrcodeapplication;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.lang.ref.WeakReference;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class QrCodeActivity extends AppCompatActivity {
    private final int CODE_REQUEST_CAMERA = 0x0010;
    private final long INTERAL_SCAN_QRCODE = 1000L;
    //views
    private SurfaceView svCamera;
    private TextView tvResult;

    //vars
    private BarcodeDetector mBarcodeDetector;
    private CameraSource mCameraSource;
    private Vibrator mVibrator;
    private DetectorProcessor mDetectorProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        findViews();
        initQrcode();
    }

    private void initQrcode() {
        mBarcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        CameraSource.Builder builder = new CameraSource.Builder(this, mBarcodeDetector)
                .setRequestedPreviewSize(height, width)
                .setRequestedFps(15.0f);

        // make sure that auto focus is an available option
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            mCameraSource = builder.setAutoFocusEnabled(true).build();
        }

        svCamera.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                try {
                    if (PERMISSION_GRANTED != ContextCompat.checkSelfPermission(QrCodeActivity.this, Manifest.permission.CAMERA)) {
                        ActivityCompat.requestPermissions(QrCodeActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_REQUEST_CAMERA);
                        return;
                    }

                    mCameraSource.start(svCamera.getHolder());
                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mCameraSource.stop();
            }
        });

        mDetectorProcessor = new DetectorProcessor(this);
        mBarcodeDetector.setProcessor(mDetectorProcessor);
    }

    protected void onDetector(final String result) {
        if (!TextUtils.isEmpty(result)) {
            mDetectorProcessor.pause = true;
            mVibrator.vibrate(200L);
            tvResult.post(new Runnable() {
                @Override
                public void run() {
                    tvResult.setText(result);
                }
            });
            try {
                Thread.sleep(INTERAL_SCAN_QRCODE);
                mDetectorProcessor.pause = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (CODE_REQUEST_CAMERA == requestCode) {
            if (null != grantResults && grantResults.length > 0 && PERMISSION_GRANTED == grantResults[0]) {
                try {
                    mCameraSource.start(svCamera.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void findViews() {
        svCamera = findViewById(R.id.camera_view);
        tvResult = findViewById(R.id.code_info);
    }

    public static class DetectorProcessor implements Detector.Processor<Barcode> {
        private WeakReference<QrCodeActivity> mReference;
        private boolean pause;

        public DetectorProcessor(QrCodeActivity activity) {
            mReference = new WeakReference<QrCodeActivity>(activity);
        }


        @Override
        public void release() {

        }

        @Override
        public void receiveDetections(Detector.Detections<Barcode> detections) {
            if (pause || null == mReference) return;
            QrCodeActivity qrCodeActivity = mReference.get();
            if (null == qrCodeActivity || qrCodeActivity.isFinishing()) return;
            final SparseArray<Barcode> barcodes = detections.getDetectedItems();

            if (barcodes.size() != 0) {
                // 因为receiveDetections在非UI线程中执行
                qrCodeActivity.onDetector(barcodes.valueAt(0).displayValue);
            }


        }
    }
}
