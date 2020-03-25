package com.tppa.intro.sensors

import android.app.Application
import android.content.SharedPreferences
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tppa.intro.R
import kotlinx.android.synthetic.main.activity_options.optionsApp
import kotlinx.android.synthetic.main.activity_sensors.*

class SensorsActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var proximitySensor: Sensor
    private lateinit var gravitySensor: Sensor
    private lateinit var magneticSensor: Sensor
    private lateinit var gyroscopeSensor: Sensor
    private lateinit var pressureSensor: Sensor

    private fun checkColor(sharedPreferences: SharedPreferences, key: String) {
        when (sharedPreferences.getString(key, "")) {
            "3" -> {
                optionsApp.setBackgroundColor(Color.parseColor("#1D800E"))
            }
            "2" -> {
                optionsApp.setBackgroundColor(Color.parseColor("#FADA5E"))
            }
            "1" -> {
                optionsApp.setBackgroundColor(Color.parseColor("#B90E0A"))
            }
            else -> {
                optionsApp.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
        }
    }

    private val listener: SharedPreferences.OnSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            checkColor(sharedPreferences, key)
        }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(p0: SensorEvent?) {

        when (p0!!.sensor.type) {
            Sensor.TYPE_PROXIMITY -> {
                val value = p0.values[0]
                proximity.text = "Proximity: " + value
            }
            Sensor.TYPE_GRAVITY -> {
                val valueX = p0.values[0]
                val valueY = p0.values[1]
                val valueZ = p0.values[2]
                gravity.text = "Gravity: X: " + valueX + ", Y:" + valueY + ", Z:" + valueZ
            }
            Sensor.TYPE_GYROSCOPE -> {
                val valueX = p0.values[0]
                val valueY = p0.values[1]
                val valueZ = p0.values[2]
                gyroscope.text = "Gyroscope: X: " + valueX + ", Y:" + valueY + ", Z:" + valueZ
            }
            Sensor.TYPE_MAGNETIC_FIELD -> {
                val value = p0.values[0]
                magnetic.text = "Magnetic Field: " + value
            }
            Sensor.TYPE_PRESSURE -> {
                val value = p0.values[0]
                pressure.text = "Pressure: " + value
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensors)

        val sharedPreferences: SharedPreferences = application.getSharedPreferences("com.tppa.sharedpreferences", Application.MODE_PRIVATE)
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        checkColor(sharedPreferences, "color")

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        Log.i("Total sensors: ","" + deviceSensors.size)
        deviceSensors.forEach{
            Log.v("Sensor name: ",""+it)
        }

        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        sensorManager.unregisterListener(this)
        super.onPause()
    }

    override fun onStop() {
        sensorManager.unregisterListener(this)
        super.onStop()
    }
}
