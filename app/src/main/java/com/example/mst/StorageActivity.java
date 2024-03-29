package com.example.mst;
import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CallLog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StorageActivity extends AppCompatActivity {

    private ProgressBar internalStorageProgressBar;
    private ProgressBar externalStorageProgressBar;
    private TextView internalStorageTextView;
    private TextView externalStorageTextView;
    private TextView callLogTextView;
    private Button downloadReportButton;

    private static final int REQUEST_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storage);

        // Initialize ProgressBars
        internalStorageProgressBar = findViewById(R.id.internal_storage_progress_bar);
        externalStorageProgressBar = findViewById(R.id.external_storage_progress_bar);

        // Initialize TextViews
        internalStorageTextView = findViewById(R.id.internal_storage_text_view);
        externalStorageTextView = findViewById(R.id.external_storage_text_view);
        callLogTextView = findViewById(R.id.call_log_text_view);

        // Initialize Button
        downloadReportButton = findViewById(R.id.download_report_button);
        downloadReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPhoneReportPDF();
            }

            private void createPhoneReportPDF() {
            }
        });

        // Request necessary permissions
        requestPermissions();

        // Test Internal and External Storage
        testInternalStorage();
        testExternalStorage();

        // Display Call Logs
        displayCallLogs();
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, REQUEST_PERMISSION_CODE);
            }
        }
    }

    private void testInternalStorage() {
        File internalDir = getFilesDir();
        long totalInternalStorage = internalDir.getTotalSpace();
        long freeInternalStorage = internalDir.getFreeSpace();
        long usedInternalStorage = totalInternalStorage - freeInternalStorage;
        double usedInternalStorageGB = (double) usedInternalStorage / (1024 * 1024 * 1024);
        double totalInternalStorageGB = (double) totalInternalStorage / (1024 * 1024 * 1024);

        internalStorageProgressBar.setProgress((int) ((usedInternalStorage * 100) / totalInternalStorage));
        internalStorageTextView.setText(String.format(Locale.getDefault(), "Internal Storage: %.2f / %.2f GB", usedInternalStorageGB, totalInternalStorageGB));
    }

    private void testExternalStorage() {
        if (isExternalStorageWritable()) {
            File externalDir = Environment.getExternalStorageDirectory();
            long totalExternalStorage = externalDir.getTotalSpace();
            long freeExternalStorage = externalDir.getFreeSpace();
            long usedExternalStorage = totalExternalStorage - freeExternalStorage;
            double usedExternalStorageGB = (double) usedExternalStorage / (1024 * 1024 * 1024);
            double totalExternalStorageGB = (double) totalExternalStorage / (1024 * 1024 * 1024);

            externalStorageProgressBar.setProgress((int) ((usedExternalStorage * 100) / totalExternalStorage));
            externalStorageTextView.setText(String.format(Locale.getDefault(), "External Storage: %.2f / %.2f GB", usedExternalStorageGB, totalExternalStorageGB));
        } else {
            externalStorageTextView.setText("External Storage: Not available");
        }
    }

    private void displayCallLogs() {
        StringBuilder callLogInfo = new StringBuilder();
        Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        int numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int typeIndex = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int dateIndex = cursor.getColumnIndex(CallLog.Calls.DATE);
        int durationIndex = cursor.getColumnIndex(CallLog.Calls.DURATION);

        while (cursor.moveToNext()) {
            String number = cursor.getString(numberIndex);
            String type = cursor.getString(typeIndex);
            String date = cursor.getString(dateIndex);
            String duration = cursor.getString(durationIndex);
            String callType = null;

            switch (Integer.parseInt(type)) {
                case CallLog.Calls.INCOMING_TYPE:
                    callType = "Incoming";
                    break;
                case CallLog.Calls.OUTGOING_TYPE:
                    callType = "Outgoing";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    callType = "Missed";
                    break;
            }

            // Format date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String formattedDate = dateFormat.format(new Date(Long.parseLong(date)));

            callLogInfo.append("Number: ").append(number).append(", Type: ").append(callType)
                    .append(", Date: ").append(formattedDate).append(", Duration: ").append(duration).append("\n");
        }

        cursor.close();
        callLogTextView.setText(callLogInfo.toString());
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

}