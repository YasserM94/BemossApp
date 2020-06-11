package com.bau.bemoss.mainActivities.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bau.bemoss.R
import com.bau.bemoss.mainActivities.chart.ChartActivity
import com.bau.bemoss.mainActivities.dashboard.adapter.DashboardAdapter
import com.bau.bemoss.mainActivities.hvac.HVAcDeviceActivity
import com.bau.bemoss.models.DashboardModel
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    private lateinit var listOfData: ArrayList<DashboardModel>
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mAdapter: DashboardAdapter

    private lateinit var btnTest: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        initActivity()
        setTestButton()
        btnTest.visibility = View.INVISIBLE

    }

    private fun initActivity() {
        getDate()
        mRecyclerView = findViewById(R.id.recyclerView_activity_dashboard)
        mLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        mRecyclerView.layoutManager = mLayoutManager
        mAdapter = DashboardAdapter(listOfData)
        mRecyclerView.adapter = mAdapter
    }

    private fun getDate() {
        listOfData = arrayListOf()

        // Data of HVAC widget in The Dashboard
        val dashboardModelHVAC = DashboardModel()
        dashboardModelHVAC.id = 1
        dashboardModelHVAC.name = "HVAC Devices"
        dashboardModelHVAC.image = R.drawable.icon_dashboard_thermostat
        dashboardModelHVAC.devicesCount = 4
        listOfData.add(dashboardModelHVAC)

        // Data of Lighting widget in The Dashboard
        val dashboardModelLighting = DashboardModel()
        dashboardModelLighting.id = 2
        dashboardModelLighting.name = "Lighting Devices"
        dashboardModelLighting.image = R.drawable.icon_dashboard_bulb
        dashboardModelLighting.devicesCount = 4
        listOfData.add(dashboardModelLighting)

        // Data of Plug widget in The Dashboard
        val dashboardModelPlug = DashboardModel()
        dashboardModelPlug.id = 3
        dashboardModelPlug.name = "Plug Devices"
        dashboardModelPlug.image = R.drawable.icon_dashboard_plug
        dashboardModelPlug.devicesCount = 3
        listOfData.add(dashboardModelPlug)

        // Data of Sensor widget in The Dashboard
        val dashboardModelSensor = DashboardModel()
        dashboardModelSensor.id = 4
        dashboardModelSensor.name = "Sensor Devices"
        dashboardModelSensor.image = R.drawable.icon_dashboard_sensor
//        dashboardModelSensor.image = "https://farm4.staticflickr.com/3854/32890526884_7dc068fedd_c.jpg"
        dashboardModelSensor.devicesCount = 2
        listOfData.add(dashboardModelSensor)
    }

    private fun setTestButton() {
        btnTest = findViewById(R.id.btn_activity_dashboard_test)
        btnTest.setOnClickListener {
            intent(HVAcDeviceActivity::class.java)
        }

        img_activity_dashboard_chart.setOnClickListener{
            intent(ChartActivity::class.java)
        }

    }

    private fun intentOld() {
        val intent = Intent(this, HVAcDeviceActivity::class.java)
        startActivity(intent)
    }

    private fun <T> intent(it: Class<T>){
        val intent = Intent(this, it)
        startActivity(intent)
    }

}
