package com.bau.bemoss.mainActivities.sensor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bau.bemoss.R
import com.bau.bemoss.mainActivities.sensor.adapter.SensorAdapter
import com.bau.bemoss.models.SensorModel

class SensorListActivity : AppCompatActivity() {

    private lateinit var listOfData: ArrayList<SensorModel>
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mAdapter: SensorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_list)

        title = "Sensor Devices"
        initActivity()

    }

    private fun initActivity() {
        getDate()
        mRecyclerView = findViewById(R.id.recyclerView_activity_sensor_list)
        mLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        mRecyclerView.layoutManager = mLayoutManager
        mAdapter = SensorAdapter(listOfData)
        mRecyclerView.adapter = mAdapter
    }

    private fun getDate() {
        listOfData = arrayListOf()

        // Data of Sensor Device Number 1
        val deviceSensor1 = SensorModel()
        deviceSensor1.id = 1
        deviceSensor1.title = "Front Door"
        deviceSensor1.image = R.drawable.icon_device_sensor_on
        deviceSensor1.deviceType = "Movement Sensor"
        deviceSensor1.deviceStatus = "Status: ON"
        listOfData.add(deviceSensor1)

        // Data of Sensor Device Number 2
        val deviceSensor2 = SensorModel()
        deviceSensor2.id = 2
        deviceSensor2.title = "Garage Door"
        deviceSensor2.image = R.drawable.icon_device_sensor_off
        deviceSensor2.deviceType = "Movement Sensor"
        deviceSensor2.deviceStatus = "Status: OFF"
        listOfData.add(deviceSensor2)

    }

}
