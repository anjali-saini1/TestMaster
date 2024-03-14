package com.example.mst;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    TextView deviceNameTextView, modelNoTextView, ramTextView, cpuTextView, kernelTextView,
            batteryTextView, cameraTextView, displayTextView, networkSignalTextView, soundTextView, storageTextView,
            androidVersionTextView, hardwareTextView;

    Button showInfoButton;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobileinfo_main);

        // Initialize TextViews
        deviceNameTextView = findViewById(R.id.textView5);
        modelNoTextView = findViewById(R.id.textView6);
        ramTextView = findViewById(R.id.textView10);
        cpuTextView = findViewById(R.id.textView11);
        kernelTextView = findViewById(R.id.textView12);
        batteryTextView = findViewById(R.id.textView14);
        cameraTextView = findViewById(R.id.textView16);
        displayTextView = findViewById(R.id.textView18);
        networkSignalTextView = findViewById(R.id.textView19);
        soundTextView = findViewById(R.id.textView20);
        storageTextView = findViewById(R.id.textView8);
        androidVersionTextView = findViewById(R.id.textView15);
        hardwareTextView = findViewById(R.id.textView17);

        // Initialize Button
        showInfoButton = findViewById(R.id.button_show_info);

        // Set onClickListener for the button
        showInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call method to display mobile information
                displayMobileInfo();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void displayMobileInfo() {
        // Get TelephonyManager instance to access device information
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        // Set device name
        deviceNameTextView.setText("Device Name: " + Build.MODEL);

        // Set model number
        modelNoTextView.setText("Model No.: " + Build.ID);

        // Set IMEI number

        // Set RAM size
        ramTextView.setText("RAM: " + getRamSize());

        // Set CPU details
        cpuTextView.setText("CPU: " + getCpuInfo());

        // Set kernel version
        kernelTextView.setText("Kernel Version: " + System.getProperty("os.version"));

        // Set battery details
        batteryTextView.setText("Battery: " + getBatteryInfo());

        // Set camera details
        cameraTextView.setText("Camera: " + getCameraInfo());

        // Set display details
        displayTextView.setText("Display: " + getDisplayInfo());

        // Set network signal details
        networkSignalTextView.setText("Network signal: " + getNetworkSignalInfo());

        // Set sound details
        soundTextView.setText("Sound: " + getSoundInfo());

        // Set storage details
        storageTextView.setText("Storage: " + getStorageInfo());

        // Set Android version
        androidVersionTextView.setText("Android Version: " + Build.VERSION.RELEASE);

        // Set hardware details
        hardwareTextView.setText("Hardware: " + Build.HARDWARE);
    }

    // Method to get RAM size
    private String getRamSize() {
        // Implement logic to get RAM size here
        return "8 GB"; // Placeholder value
    }

    // Method to get CPU info
    private String getCpuInfo() {
        // Implement logic to get CPU info here
        return "Qualcomm SM6125"; // Placeholder value
    }

    // Method to get battery info
    private String getBatteryInfo() {
        // Implement logic to get battery info here
        return "4100 mAh"; // Placeholder value
    }

    // Method to get camera info
    private String getCameraInfo() {
        // Implement logic to get camera info here
        return "12 MP + 8 MP"; // Placeholder value
    }

    // Method to get display info
    private String getDisplayInfo() {
        // Implement logic to get display info here
        return "6.4 inches, AMOLED"; // Placeholder value
    }

    // Method to get network signal info
    private String getNetworkSignalInfo() {
        // Implement logic to get network signal info here
        return "Excellent"; // Placeholder value
    }

    // Method to get sound info
    private String getSoundInfo() {
        // Implement logic to get sound info here
        return "Dolby Atmos"; // Placeholder value
    }

    // Method to get storage info
    private String getStorageInfo() {
        // Implement logic to get storage info here
        return "256 GB";
    }
}
