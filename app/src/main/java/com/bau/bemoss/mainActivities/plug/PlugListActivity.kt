package com.bau.bemoss.mainActivities.plug

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bau.bemoss.R
import com.bau.bemoss.mainActivities.plug.adapter.PlugAdapter
import com.bau.bemoss.models.PlugModel

class PlugListActivity : AppCompatActivity() {

    private lateinit var listOfData: ArrayList<PlugModel>
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mAdapter: PlugAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plug_list)

        title = "Plug Devices"
        initActivity()

    }

    private fun initActivity() {
        getDate()
        mRecyclerView = findViewById(R.id.recyclerView_activity_plug_list)
        mLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        mRecyclerView.layoutManager = mLayoutManager
        mAdapter = PlugAdapter(listOfData)
        mRecyclerView.adapter = mAdapter
    }

    private fun getDate() {
        listOfData = arrayListOf()

        // Data of Plug Device Number 1
        val devicePlug1 = PlugModel()
        devicePlug1.id = 1
        devicePlug1.title = "Living Room"
        devicePlug1.image = R.drawable.icon_device_plug_on
        devicePlug1.deviceType = "TV"
        devicePlug1.deviceStatus = "Status: ON"
        listOfData.add(devicePlug1)

        // Data of Plug Device Number 2
        val devicePlug2 = PlugModel()
        devicePlug2.id = 2
        devicePlug2.title = "Kitchen"
        devicePlug2.image = R.drawable.icon_device_plug_on
        devicePlug2.deviceType = "Fridge"
        devicePlug2.deviceStatus = "Status: ON"
        listOfData.add(devicePlug2)

        // Data of Plug Device Number 3
        val devicePlug3 = PlugModel()
        devicePlug3.id = 3
        devicePlug3.title = "Kitchen"
        devicePlug3.image = R.drawable.icon_device_plug_off
        devicePlug3.deviceType = "Coffee Machine"
        devicePlug3.deviceStatus = "Status: OFF"
        listOfData.add(devicePlug3)

    }

}
