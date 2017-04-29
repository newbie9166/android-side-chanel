package com.android.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.android.Info;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class SensorInfo implements Info {
    private SensorManager sensorManager;
    private List<Sensor> sensorList = new LinkedList<>();
    private List<Listener> listenerList = new LinkedList<>();

    public SensorInfo(Context ctx) {
        sensorManager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);

        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Collections.addAll(sensorList, accelerometerSensor, gyroscopeSensor);

        for (Sensor sensor : sensorList) {
            List<Float>[] values = (List<Float>[]) new LinkedList[3];
            values[0] = new LinkedList<>();
            values[1] = new LinkedList<>();
            values[2] = new LinkedList<>();
            Listener listener = new Listener(values);
            listenerList.add(listener);
        }

        regListener();
    }
    //        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    //        List<Float>[] accelerometerValues = (List<Float>[]) new ArrayList[3];
    //        valuesList.add(accelerometerValues);
    //        Listener accelemerometerLister = new Listener(accelerometerValues);
    //        listenerList.add(accelemerometerLister);
    //        sensorManager.registerListener(accelemerometerLister, accelerometerSensor, 100);
    //
    //        Sensor gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    //        List<Float>[] gyroscopeValues = (List<Float>[]) new ArrayList[3];
    //        valuesList.add(gyroscopeValues);
    //        Listener gyroscopeListener = new Listener(gyroscopeValues);
    //        listenerList.add(gyroscopeListener);
    //        sensorManager.registerListener(gyroscopeListener, gyroscopeSensor, 100);

    private void regListener() {
        for (int i = 0; i < listenerList.size(); i++) {
            sensorManager.registerListener(listenerList.get(i), sensorList.get(i), 1000000);
        }
    }

    private void unRegListener() {
        for (Listener listener : listenerList) {
            sensorManager.unregisterListener(listener);
        }
    }

    @Override
    public String update() {
//        unRegListener();
        StringBuilder sb = new StringBuilder();
//        for (List<Float>[] values : valuesList) {
        for (Listener listener : listenerList) {
            List<Float>[] values = listener.getValues();
            sb.append(values[0].toString());
            sb.append("\n");
            sb.append(values[1].toString());
            sb.append("\n");
            sb.append(values[2].toString());
            sb.append("\n");
        }

        sb.append("\n");
        regListener();
        return sb.toString();
    }
}

class Listener implements SensorEventListener {
    private List<Float>[] values;

    Listener(List<Float>[] values) {
        this.values = values;
    }

    List<Float>[] getValues() {
        Log.d(TAG, "getValues: "+values[0]);
        return values.clone();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        (values[0]).add(event.values[0]);
        Log.d(TAG, "getValues: "+values[0]);
        (values[1]).add(event.values[1]);
        (values[2]).add(event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}