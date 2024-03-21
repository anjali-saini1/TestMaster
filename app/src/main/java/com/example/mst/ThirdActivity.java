package com.example.mst;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ThirdActivity extends AppCompatActivity {
    TextView deviceNameTextView, modelNoTextView, ramTextView, cpuTextView, kernelTextView,
            batteryTextView, cameraTextView, displayTextView, networkSignalTextView, soundTextView, storageTextView,
            androidVersionTextView, hardwareTextView;

    Button showInfoButton;

    private TelephonyManager telephonyManager;
    private PhoneStateListener phoneStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobileinfo_main);

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

        showInfoButton = findViewById(R.id.button_show_info);
        showInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    displayMobileInfo();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void displayMobileInfo() {
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        deviceNameTextView.setText("Device Name: " + Build.MODEL);
        modelNoTextView.setText("Model No.: " + Build.ID);
        ramTextView.setText("RAM: " + getRamSize());
        cpuTextView.setText("CPU: " + getCpuInfo());
        kernelTextView.setText("Kernel Version: " + System.getProperty("os.version"));
        batteryTextView.setText("Battery: " + getBatteryInfo());
        cameraTextView.setText("Camera: " + getCameraInfo());
        displayTextView.setText("Display: " + getDisplayInfo());
        soundTextView.setText("Sound: " + getSoundInfo());
        storageTextView.setText("Storage: " + getStorageInfo());
        androidVersionTextView.setText("Android Version: " + Build.VERSION.RELEASE);
        hardwareTextView.setText("Hardware: " + Build.HARDWARE);

        // Check for permissions and start listening to signal strength
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            listenToSignalStrength();
        }
    }

    private void listenToSignalStrength() {
        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                super.onSignalStrengthsChanged(signalStrength);
                int signalLevel = getSignalLevel(signalStrength);
                networkSignalTextView.setText("Network signal: " + signalLevel);
            }
        };
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }

    private int getSignalLevel(SignalStrength signalStrength) {
        if (signalStrength == null) {
            return 0;
        }
        int signalLevel = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            signalLevel = signalStrength.getCellSignalStrengths().get(0).getLevel();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                signalLevel = signalStrength.getLevel();
            }
        }
        return signalLevel;
    }

    private String getRamSize() {
        long totalMemory = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            totalMemory = Runtime.getRuntime().totalMemory();
        }
        return formatSize(totalMemory);
    }


    private String getCpuInfo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return Build.SUPPORTED_ABIS[0];
        } else {
            return Build.CPU_ABI;
        }
    }

    private String getBatteryInfo() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Context context = getApplicationContext();
        Intent batteryStatus = context.registerReceiver(null, ifilter);

        if (batteryStatus == null) {
            return "Battery information not available";
        }

        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

        String batteryStatusText = "Not Charging";
        if (isCharging) {
            batteryStatusText = "Charging";
        }
        if (usbCharge) {
            batteryStatusText += " - USB";
        }
        if (acCharge) {
            batteryStatusText += " - AC";
        }

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = level / (float) scale;
        DecimalFormat df = new DecimalFormat("#.##");
        String batteryPercentage = df.format(batteryPct * 100);

        return batteryStatusText + " - " + batteryPercentage + "%";
    }


    private String getCameraInfo() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            try {
                String[] cameraIds = manager.getCameraIdList();
                StringBuilder cameraInfo = new StringBuilder();
                for (String cameraId : cameraIds) {
                    CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
                    Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                    if (facing != null) {
                        switch (facing) {
                            case CameraCharacteristics.LENS_FACING_FRONT:
                                cameraInfo.append("Front Camera available\n");
                                break;
                            case CameraCharacteristics.LENS_FACING_BACK:
                                cameraInfo.append("Rear Camera available\n");
                                break;
                            case CameraCharacteristics.LENS_FACING_EXTERNAL:
                                cameraInfo.append("External Camera available\n");
                                break;
                        }
                    }
                }
                return cameraInfo.toString();
            } catch (CameraAccessException e) {
                e.printStackTrace();
                return "Camera information not available";
            }
        } else {
            return "No camera available";
        }
    }

    private String getDisplayInfo() {
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        int densityDpi = metrics.densityDpi;
        float refreshRate = display.getRefreshRate();

        return "Resolution: " + widthPixels + "x" + heightPixels + "\n" +
                "Density DPI: " + densityDpi + "\n" +
                "Refresh Rate: " + refreshRate + "Hz";
    }


    private String getSoundInfo() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_RING);
        return "Volume Level: " + volumeLevel;
    }


    private String getStorageInfo() {
        String path = Environment.getDataDirectory().getPath();
        StatFs stat = new StatFs(path);
        long blockSize;
        long availableBlocks;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            blockSize = stat.getBlockSize();
            availableBlocks = stat.getAvailableBlocks();
        }
        long freeSpace = availableBlocks * blockSize;
        return formatSize(freeSpace);
    }

    private String formatSize(long size) {
        if (size <= 0) {
            return "Unknown";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        digitGroups = Math.min(digitGroups, units.length - 1); // Ensure digitGroups is within bounds
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop listening to signal strength when activity is destroyed
        if (telephonyManager != null && phoneStateListener != null) {
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        }
    }
}