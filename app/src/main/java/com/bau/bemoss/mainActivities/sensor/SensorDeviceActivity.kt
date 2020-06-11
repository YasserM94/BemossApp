package com.bau.bemoss.mainActivities.sensor

import android.R.attr.button
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import cc.cloudist.acplibrary.ACProgressFlower
import com.bau.bemoss.R
import com.bau.bemoss.helper.MyProgressDialog
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_plug_device.*
import kotlinx.android.synthetic.main.activity_sensor_device.*


class SensorDeviceActivity : AppCompatActivity() {

    private var firebaseDatabase: FirebaseDatabase? = null

    private var myRefDatabaseRoot: DatabaseReference? = null
    private var getMyRefDeviceId: DatabaseReference? = null
    private var myRefDeviceType: DatabaseReference? = null

    private var myRefDeviceTitle: DatabaseReference? = null
    private var myRefDeviceStatus: DatabaseReference? = null
    private var myRefSensorType: DatabaseReference? = null
    private var myRefPresenceDetected: DatabaseReference? = null
    private var myRefPresenceUnDetected: DatabaseReference? = null

    private var clickedItem: Int = 0
    private var progressDialog: ACProgressFlower? = null

    private var localDeviceTitle: String = "Sensor"
    private var localDeviceStatus: Boolean = true
    private var localSensorType: String = "Motion"
    private var localPresenceDetected: Boolean = true
    private var localPresenceUnDetected: Boolean = true

    private lateinit var firebaseDeviceTitle: String
    private var firebaseDeviceStatus: Boolean = true
    private lateinit var firebaseSensorType: String
    private var firebasePresenceDetected: Boolean = true
    private var firebasePresenceUnDetected: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_device)

        // Get The Id of the RecyclerView Item
        clickedItem = intent.getIntExtra("itemSelected", 0)
        title = "Sensor Device $clickedItem"

        initializeFirebase(clickedItem)
        getDataFromFirebase()
        setLoading(true)

    }


    // Initialize Firebase
    private fun initializeFirebase(deviceId: Int) {
        firebaseDatabase = FirebaseDatabase.getInstance()
        myRefDatabaseRoot = firebaseDatabase!!.getReference("Bemoss Database")
        myRefDeviceType = myRefDatabaseRoot!!.child("Sensor Devices")
        getMyRefDeviceId = myRefDeviceType!!.child("Device$deviceId")
        Log.d("TAG", "initFirebase is Okay")
    }

    // Get Data from Firebase
    private fun getDataFromFirebase() {
        getMyRefDeviceId?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("TAG", "Error onCancelled")
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                firebaseDeviceTitle = snapshot.child("DeviceTitle").value.toString()
                firebaseDeviceStatus = snapshot.child("DeviceStatus").value as Boolean
                firebaseSensorType = snapshot.child("SensorType").value.toString()
                firebasePresenceDetected = snapshot.child("PresenceDetected").value as Boolean
                firebasePresenceUnDetected = snapshot.child("PresenceUnDetected").value as Boolean

                storeDataLocally()
                setDataOnGUI()

                Log.d("TAG", "Data From Firebase Received Successfully")
            }
        })
        Log.d("TAG", "getDataFromFirebase is Okay")
    }


    private fun createDataInFirebase() {
        sendDataToFirebase(1)
        sendDataToFirebase(2)
        sendDataToFirebase(3)
        sendDataToFirebase(4)
        sendDataToFirebase(5)
    }


    private fun sendDataToFirebase(selected: Int) {
        when (selected) {
            1 -> {
                myRefDeviceTitle = getMyRefDeviceId!!.child("DeviceTitle")
                myRefDeviceTitle!!.setValue(localDeviceTitle)
            }
            2 -> {
                myRefDeviceStatus = getMyRefDeviceId!!.child("DeviceStatus")
                myRefDeviceStatus!!.setValue(localDeviceStatus)
            }
            3 -> {
                myRefSensorType = getMyRefDeviceId!!.child("SensorType")
                myRefSensorType!!.setValue(localSensorType)
            }
            4 -> {
                myRefPresenceDetected = getMyRefDeviceId!!.child("PresenceDetected")
                myRefPresenceDetected!!.setValue(localPresenceDetected)
            }
            5 -> {
                myRefPresenceUnDetected = getMyRefDeviceId!!.child("PresenceUnDetected")
                myRefPresenceUnDetected!!.setValue(localPresenceUnDetected)
            }
        }
        Log.d("TAG", "sendDataToFirebase is Okay")
    }


    private fun storeDataLocally() {
        localDeviceTitle = firebaseDeviceTitle
        localDeviceStatus = firebaseDeviceStatus
        localSensorType = firebaseSensorType
        localPresenceDetected = firebasePresenceDetected
        localPresenceUnDetected = firebasePresenceUnDetected
    }

    // Set the data on The GUI
    private fun setDataOnGUI() {
        setDeviceTitle(localDeviceTitle)
        setDeviceStatus(localDeviceStatus)
        setPresenceDetected(localPresenceDetected)
        setPresenceUnDetected(localPresenceUnDetected)

        setLoading(false)
        setButtons()
    }


    private fun setDeviceTitle(deviceTitle: String) {
        tv_sensor_device_widget_device_status_title.text = deviceTitle
        sendDataToFirebase(1)
    }

    private fun setDeviceStatus(deviceStatus: Boolean) {
        if (deviceStatus) {
            localDeviceStatus = true
            tv_sensor_device_widget_device_status_on_off.text = "Status: On"
            btn_sensor_device_widget_device_controls_on.setBackgroundResource(R.drawable.selector_options_button_gray)
            btn_sensor_device_widget_device_controls_on.setTextColor(Color.parseColor("#000000"))
            btn_sensor_device_widget_device_controls_off.setBackgroundResource(R.drawable.selector_options_button_blue)
            btn_sensor_device_widget_device_controls_off.setTextColor(Color.parseColor("#ffffff"))
            img_sensor_device_widget_device_picture.setImageResource(R.drawable.icon_device_sensor_on)
            sendDataToFirebase(2)
        } else if (!deviceStatus) {
            localDeviceStatus = false
            tv_sensor_device_widget_device_status_on_off.text = "Status: Off"
            btn_sensor_device_widget_device_controls_on.setBackgroundResource(R.drawable.selector_options_button_blue)
            btn_sensor_device_widget_device_controls_on.setTextColor(Color.parseColor("#ffffff"))
            btn_sensor_device_widget_device_controls_off.setBackgroundResource(R.drawable.selector_options_button_gray)
            btn_sensor_device_widget_device_controls_off.setTextColor(Color.parseColor("#000000"))
            img_sensor_device_widget_device_picture.setImageResource(R.drawable.icon_device_sensor_off)
            sendDataToFirebase(2)
        }
    }


    private fun setPresenceActions(side: Int) {
        if (side == 0) {
            if (localPresenceDetected) {
                localPresenceDetected = false
                setPresenceDetected(false)
            } else if (!localPresenceDetected) {
                localPresenceDetected = true
                setPresenceDetected(true)
            }
            sendDataToFirebase(4)
        } else if (side == 1) {
            if (localPresenceUnDetected) {
                localPresenceUnDetected = false
                setPresenceUnDetected(false)
            } else if (!localPresenceUnDetected) {
                localPresenceUnDetected = true
                setPresenceUnDetected(true)
            }
            sendDataToFirebase(5)
        }
    }

    private fun setPresenceDetected(isPresence: Boolean) {
        if (!isPresence) {
            tv_sensor_device_widget_presence_detected_selected_action.text = "No Action"
            btn_sensor_device_widget_presence_detected_set_action.text = "Set Action"
        } else if (isPresence) {
            tv_sensor_device_widget_presence_detected_selected_action.text =
                "Turn On\nEntrance Light"
            btn_sensor_device_widget_presence_detected_set_action.text = "Remove Action"
        }
    }

    private fun setPresenceUnDetected(isPresence: Boolean) {
        if (!isPresence) {
            tv_sensor_device_widget_presence_undetected_selected_action.text = "No Action"
            btn_sensor_device_widget_presence_undetected_set_action.text = "Set Action"
        } else if (isPresence) {
            tv_sensor_device_widget_presence_undetected_selected_action.text =
                "Turn Off\nEntrance Light"
            btn_sensor_device_widget_presence_undetected_set_action.text = "Remove Action"
        }
        sendDataToFirebase(5)
    }


    private fun setButtons() {
        btn_sensor_device_widget_device_controls_on.setOnClickListener {
            setDeviceStatus(true)
        }

        btn_sensor_device_widget_device_controls_off.setOnClickListener {
            setDeviceStatus(false)
        }

        btn_sensor_device_widget_presence_detected_set_action.setOnClickListener {
            setPresenceActions(0)
        }

        btn_sensor_device_widget_presence_undetected_set_action.setOnClickListener {
            setPresenceActions(1)
        }
    }

    private fun setLoading(status: Boolean) {
        if (status) {
            progressDialog = MyProgressDialog().getInstance(this)
            progressDialog!!.show()
        } else if (!status) {
            progressDialog!!.dismiss()
        }
    }

}

