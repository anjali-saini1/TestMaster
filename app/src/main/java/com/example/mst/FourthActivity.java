package com.example.mst;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FourthActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private ProgressBar accelerometerProgressBar;
    private ProgressBar gyroscopeProgressBar;
    private ProgressBar proximityProgressBar;
    private ProgressBar lightProgressBar;
    private ProgressBar magneticFieldProgressBar;
    private ProgressBar pressureProgressBar;
    private ProgressBar temperatureProgressBar;
    private ProgressBar humidityProgressBar;
    private TextView accelerometerTextView;
    private TextView gyroscopeTextView;
    private TextView proximityTextView;
    private TextView lightTextView;
    private TextView magneticFieldTextView;
    private TextView pressureTextView;
    private TextView temperatureTextView;
    private TextView humidityTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensors);

        // Initialize SensorManager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Initialize ProgressBars
        accelerometerProgressBar = findViewById(R.id.accelerometer_progress_bar);
        gyroscopeProgressBar = findViewById(R.id.gyroscope_progress_bar);
        proximityProgressBar = findViewById(R.id.proximity_progress_bar);
        lightProgressBar = findViewById(R.id.light_progress_bar);
        magneticFieldProgressBar = findViewById(R.id.magnetic_field_progress_bar);
        pressureProgressBar = findViewById(R.id.pressure_progress_bar);
        temperatureProgressBar = findViewById(R.id.temperature_progress_bar);
        humidityProgressBar = findViewById(R.id.humidity_progress_bar);

        // Initialize TextViews
        accelerometerTextView = findViewById(R.id.accelerometer_text_view);
        gyroscopeTextView = findViewById(R.id.gyroscope_text_view);
        proximityTextView = findViewById(R.id.proximity_text_view);
        lightTextView = findViewById(R.id.light_text_view);
        magneticFieldTextView = findViewById(R.id.magnetic_field_text_view);
        pressureTextView = findViewById(R.id.pressure_text_view);
        temperatureTextView = findViewById(R.id.temperature_text_view);
        humidityTextView = findViewById(R.id.humidity_text_view);

        // Check the functionality of sensors
        checkSensorFunctionality(Sensor.TYPE_ACCELEROMETER, accelerometerProgressBar, accelerometerTextView, "Accelerometer");
        checkSensorFunctionality(Sensor.TYPE_GYROSCOPE, gyroscopeProgressBar, gyroscopeTextView, "Gyroscope");
        checkSensorFunctionality(Sensor.TYPE_PROXIMITY, proximityProgressBar, proximityTextView, "Proximity Sensor");
        checkSensorFunctionality(Sensor.TYPE_LIGHT, lightProgressBar, lightTextView, "Light Sensor");
        checkSensorFunctionality(Sensor.TYPE_MAGNETIC_FIELD, magneticFieldProgressBar, magneticFieldTextView, "Magnetic Field Sensor");
        checkSensorFunctionality(Sensor.TYPE_PRESSURE, pressureProgressBar, pressureTextView, "Pressure Sensor");
        checkSensorFunctionality(Sensor.TYPE_AMBIENT_TEMPERATURE, temperatureProgressBar, temperatureTextView, "Temperature Sensor");
        checkSensorFunctionality(Sensor.TYPE_RELATIVE_HUMIDITY, humidityProgressBar, humidityTextView, "Humidity Sensor");
    }

    private void checkSensorFunctionality(int sensorType, ProgressBar progressBar, TextView textView, String sensorName) {
        Sensor sensor = sensorManager.getDefaultSensor(sensorType);
        if (sensor != null) {
            // Sensor is available
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            // Sensor is not available
            progressBar.setVisibility(ProgressBar.GONE);
            textView.setText(sensorName + " not available on this device.");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Handle sensor events here
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                updateSensorData(event, accelerometerProgressBar, accelerometerTextView);
                break;
            case Sensor.TYPE_GYROSCOPE:
                updateSensorData(event, gyroscopeProgressBar, gyroscopeTextView);
                break;
            case Sensor.TYPE_PROXIMITY:
                updateSensorData(event, proximityProgressBar, proximityTextView);
                break;
            case Sensor.TYPE_LIGHT:
                updateSensorData(event, lightProgressBar, lightTextView);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                updateSensorData(event, magneticFieldProgressBar, magneticFieldTextView);
                break;
            case Sensor.TYPE_PRESSURE:
                updateSensorData(event, pressureProgressBar, pressureTextView);
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                updateSensorData(event, temperatureProgressBar, temperatureTextView);
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                updateSensorData(event, humidityProgressBar, humidityTextView);
                break;
        }
    }

    private void updateSensorData(SensorEvent event, ProgressBar progressBar, TextView textView) {
        float[] values = event.values;
        if (values != null && values.length > 0) {
            float value = values[0];
            int max = (int) event.sensor.getMaximumRange();
            int progress = (int) ((value / max) * 100);
            progressBar.setProgress(progress);
            textView.setText(event.sensor.getName() + " Data: " + value);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used in this example
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister sensor listeners to save power when the activity is paused
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Re-register sensor listeners when the activity is resumed
        // Re-check the functionality of sensors
        checkSensorFunctionality(Sensor.TYPE_ACCELEROMETER, accelerometerProgressBar, accelerometerTextView, "Accelerometer");
        checkSensorFunctionality(Sensor.TYPE_GYROSCOPE, gyroscopeProgressBar, gyroscopeTextView, "Gyroscope");
        checkSensorFunctionality(Sensor.TYPE_PROXIMITY, proximityProgressBar, proximityTextView, "Proximity Sensor");
        checkSensorFunctionality(Sensor.TYPE_LIGHT, lightProgressBar, lightTextView, "Light Sensor");
        checkSensorFunctionality(Sensor.TYPE_MAGNETIC_FIELD, magneticFieldProgressBar, magneticFieldTextView, "Magnetic Field Sensor");
        checkSensorFunctionality(Sensor.TYPE_PRESSURE, pressureProgressBar, pressureTextView, "Pressure Sensor");
        checkSensorFunctionality(Sensor.TYPE_AMBIENT_TEMPERATURE, temperatureProgressBar, temperatureTextView, "Temperature Sensor");
        checkSensorFunctionality(Sensor.TYPE_RELATIVE_HUMIDITY, humidityProgressBar, humidityTextView, "Humidity Sensor");
    }
}


