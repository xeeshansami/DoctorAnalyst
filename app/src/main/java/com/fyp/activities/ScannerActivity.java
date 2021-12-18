package com.fyp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.fyp.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends Activity implements ZXingScannerView.ResultHandler {

    public static final String EXCLUDED_FORMAT = "ExcludedFormat";
    private static final String TAG = ScannerActivity.class.getSimpleName();
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        setStatusBarTranslucent(true);
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        String result = rawResult.getText();
        BarcodeFormat format = rawResult.getBarcodeFormat();

        Log.v(TAG, "Scanned code: " + rawResult.getText());
        Log.v(TAG, "Scanend code type: " + rawResult.getBarcodeFormat().toString());

        //Return error
        if (result == null) {
            setResult(RESULT_CANCELED, returnErrorCode(result, format));
            finish();
        }

        if (result.isEmpty()) {
            setResult(RESULT_CANCELED, returnErrorCode(result, format));
            finish();
        }

        //Return correct code
        setResult(RESULT_OK, returnCorrectCode(result, format));
        finish();
    }

    private Intent returnErrorCode(String result, BarcodeFormat format) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(ScannerConstants.ERROR_INFO, getResources().getString(R.string.scanner_error_message));
        return returnIntent;
    }

    private Intent returnCorrectCode(String result, BarcodeFormat format) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(ScannerConstants.SCAN_RESULT, result);

        if (format.equals(BarcodeFormat.QR_CODE)) {
            returnIntent.putExtra(ScannerConstants.SCAN_RESULT_TYPE, ScannerConstants.QR_SCAN);
        } else {
            returnIntent.putExtra(ScannerConstants.SCAN_RESULT_TYPE, ScannerConstants.BAR_SCAN);
        }

        return returnIntent;
    }

    public void excludeFormats(BarcodeFormat item) {
        Collection<BarcodeFormat> defaultFormats = mScannerView.getFormats();
        List<BarcodeFormat> formats = new ArrayList<>();
        for (BarcodeFormat format : defaultFormats) {
            if (!format.equals(item)) {
                formats.add(format);
            }
        }
        mScannerView.setFormats(formats);
    }

    public interface ScannerConstants {
        public static final String SCAN_MODES = "SCAN_MODES";
        public static final String SCAN_RESULT = "SCAN_RESULT";
        public static final String SCAN_RESULT_TYPE = "SCAN_RESULT_TYPE";
        public static final String ERROR_INFO = "ERROR_INFO";
        public static final int BAR_SCAN = 0;
        public static final int QR_SCAN = 1;
    }
}