package com.bau.bemoss.mainActivities.lighting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bau.bemoss.R
import com.bau.bemoss.mainActivities.lighting.adapter.LightingAdapter
import com.bau.bemoss.models.LightingModel

class LightingListActivity : AppCompatActivity() {

    private lateinit var listOfData: ArrayList<LightingModel>
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mAdapter: LightingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lighting_list)

        title = "Lighting Devices"
        initActivity()

    }

    private fun initActivity() {
        getDate()
        mRecyclerView = findViewById(R.id.recyclerView_activity_lighting_list)
        mLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        mRecyclerView.layoutManager = mLayoutManager
        mAdapter = LightingAdapter(listOfData)
        mRecyclerView.adapter = mAdapter
    }

    private fun getDate() {
        listOfData = arrayListOf()

        // Data of Lighting Device Number 1
        val deviceLighting1 = LightingModel()
        deviceLighting1.id = 1
        deviceLighting1.title = "Living Room"
        deviceLighting1.image = R.drawable.icon_device_bulb_on
        deviceLighting1.deviceType = "Light"
        deviceLighting1.deviceStatus = "Status: ON"
        listOfData.add(deviceLighting1)

        // Data of Lighting Device Number 2
        val deviceLighting2 = LightingModel()
        deviceLighting2.id = 2
        deviceLighting2.title = "Kitchen"
        deviceLighting2.image = R.drawable.icon_device_bulb_on
        deviceLighting2.deviceType = "Light"
        deviceLighting2.deviceStatus = "Status: ON"
        listOfData.add(deviceLighting2)

        // Data of Lighting Device Number 3
        val deviceLighting3 = LightingModel()
        deviceLighting3.id = 3
        deviceLighting3.title = "Bedroom"
        deviceLighting3.image = R.drawable.icon_device_bulb_off
        deviceLighting3.deviceType = "Light"
        deviceLighting3.deviceStatus = "Status: OFF"
        listOfData.add(deviceLighting3)

        // Data of Lighting Device Number 4
        val deviceLighting4 = LightingModel()
        deviceLighting4.id = 4
        deviceLighting4.title = "Bathroom"
        deviceLighting4.image = R.drawable.icon_device_bulb_off
        deviceLighting4.deviceType = "Light"
        deviceLighting4.deviceStatus = "Status: OFF"
        listOfData.add(deviceLighting4)

    }

}

