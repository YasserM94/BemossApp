package com.bau.bemoss.mainActivities.hvac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bau.bemoss.R
import com.bau.bemoss.mainActivities.hvac.adapter.HVAcAdapter
import com.bau.bemoss.models.HVAcModel
import com.google.firebase.database.*

class HVAcListActivity : AppCompatActivity() {

    private var firebaseDatabase: FirebaseDatabase? = null

    private var myRefDatabaseRoot: DatabaseReference? = null
    private var getRefDeviceType: DatabaseReference? = null

    private lateinit var numberOfDevices: String

    private lateinit var listOfData: ArrayList<HVAcModel>
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mAdapter: HVAcAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hvac_list)

        title = "HVAC Devices"
        initActivity()

    }

    private fun initActivity() {
        getDate()
        setDataOnRecyclerView()

//        listOfData = arrayListOf()
//        initializeFirebase()
//        getDataFromFirebase()

    }

    private fun getDate() {
        listOfData = arrayListOf()

        // Data of HVAc Device Number 1
        val deviceHVAc1 = HVAcModel()
        deviceHVAc1.id = 1
        deviceHVAc1.title = "Living Room"
        deviceHVAc1.deviceMode = "Cool"
        deviceHVAc1.temperature = "18℃"
        deviceHVAc1.deviceType = "Air Conditioner"
        deviceHVAc1.deviceStatus = true
        listOfData.add(deviceHVAc1)

        // Data of HVAc Device Number 2
        val deviceHVAc2 = HVAcModel()
        deviceHVAc2.id = 2
        deviceHVAc2.title = "Kitchen"
        deviceHVAc2.deviceMode = "Heat"
        deviceHVAc2.temperature = "26℃"
        deviceHVAc2.deviceType = "Electric Heater"
        deviceHVAc2.deviceStatus = true
        listOfData.add(deviceHVAc2)

        // Data of HVAc Device Number 3
        val deviceHVAc3 = HVAcModel()
        deviceHVAc3.id = 3
        deviceHVAc3.title = "Bedroom"
        deviceHVAc3.deviceMode = "Heat"
        deviceHVAc3.temperature = "35℃"
        deviceHVAc3.deviceType = "Water Boiler"
        deviceHVAc3.deviceStatus = true
        listOfData.add(deviceHVAc3)

        // Data of HVAc Device Number 4
        val deviceHVAc4 = HVAcModel()
        deviceHVAc4.id = 4
        deviceHVAc4.title = "Bathroom"
        deviceHVAc4.deviceMode = "Heat"
        deviceHVAc4.temperature = "33℃"
        deviceHVAc4.deviceType = "Oil Heater"
        deviceHVAc4.deviceStatus = true
        listOfData.add(deviceHVAc4)

    }

    // Initialize Firebase
    private fun initializeFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance()
        myRefDatabaseRoot = firebaseDatabase!!.getReference("Smart Home")
        getRefDeviceType = myRefDatabaseRoot!!.child("HVAC")
        Log.d("TAG", "initFirebase is Okay")
    }

    fun setDataOnRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerView_activity_hvac_list)
        mLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        mRecyclerView.layoutManager = mLayoutManager
        mAdapter = HVAcAdapter(listOfData)
        mRecyclerView.adapter = mAdapter
    }

    // Get Data from Firebase
    private fun getDataFromFirebase() {
        getRefDeviceType?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("TAG", "Error onCancelled")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                numberOfDevices = snapshot.child("Description").child("numberOfDevices").value.toString()

                // Data of HVAc Devices
                for (i in 0 until numberOfDevices.toInt()) {
                    val deviceHVAc = HVAcModel()
                    deviceHVAc.id = i
                    deviceHVAc.title = snapshot.child("Device$i").child("name").value.toString()
                    deviceHVAc.deviceMode = snapshot.child("Device$i").child("deviceMode").value.toString()
                    deviceHVAc.temperature = snapshot.child("Device$i").child("indoorTemperature").value.toString()
                    deviceHVAc.deviceType = snapshot.child("Device$i").child("deviceType").value.toString()
                    deviceHVAc.deviceStatus = snapshot.child("Device$i").child("status").value as Boolean
                    listOfData.add(deviceHVAc)
                }
                setDataOnRecyclerView()
                Log.d("TAG", "Data From Firebase Received Successfully")
            }
        })
        Log.d("TAG", "getDataFromFirebase is Okay")
    }




}

