package com.example.jantawifi;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor lightSensor, proximitySensor, accelerometerSensor, gyroscopeSensor;
    private TextView lightView, proxiView, acceleView, gyroView;

    private SensorDataSource dataCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lightView = findViewById(R.id.lightid);
        proxiView = findViewById(R.id.proxiid);
        acceleView = findViewById(R.id.acceleid);
        gyroView = findViewById(R.id.gyroid);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {

            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        }


        dataCollection = new SensorDataSource(this);
        dataCollection.open();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (proximitySensor != null) {
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (accelerometerSensor != null) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (gyroscopeSensor != null) {
            sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == lightSensor) {
            float lightValue = event.values[0];
            lightView.setText("Light Sensor: " + lightValue);
            dataCollection.insertSensorData("Light", lightValue);
        } else if (event.sensor == proximitySensor) {
            float proximityValue = event.values[0];
            proxiView.setText("Proximity Sensor: " + proximityValue);
            dataCollection.insertSensorData("Proximity", proximityValue);
        } else if (event.sensor == accelerometerSensor) {
            float accelX = event.values[0];
            float accelY = event.values[1];
            float accelZ = event.values[2];
            acceleView.setText("Accelerometer (X,Y,Z): " + accelX + ", " + accelY + ", " + accelZ);
            dataCollection.insertSensorData("Accelerometer (X)", accelX);
            dataCollection.insertSensorData("Accelerometer (Y)", accelY);
            dataCollection.insertSensorData("Accelerometer (Z)", accelZ);
        } else if (event.sensor == gyroscopeSensor) {
            float gyroX = event.values[0];
            float gyroY = event.values[1];
            float gyroZ = event.values[2];
            gyroView.setText("Gyroscope (X,Y,Z): " + gyroX + ", " + gyroY + ", " + gyroZ);
            dataCollection.insertSensorData("Gyroscope (X)", gyroX);
            dataCollection.insertSensorData("Gyroscope (Y)", gyroY);
            dataCollection.insertSensorData("Gyroscope (Z)", gyroZ);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used in this example
    }

    public void openChart(View view) {
//
//        Intent intent = new Intent(MainActivity.this, ChartActivity.class);
//        intent.putExtra("sensorType", "Light"); // You can change this to the desired sensor type
//        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataCollection.close();
    }
}