package com.example.jantawifi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SensorDataSource {

    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public SensorDataSource(Context context) {

        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {

        dbHelper.close();
    }

    public long insertSensorData(String sensorType, float sensorValue) {
        ContentValues values = new ContentValues();
        values.put("sensor_type", sensorType);
        values.put("sensor_value", sensorValue);
        return database.insert("sensor_data", null, values);
    }

    public Cursor getAllSensorData() {
        return database.query("sensor_data", null, null, null, null, null, null);
    }


}

