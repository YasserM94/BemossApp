package com.bau.bemoss.mainActivities.plug

import android.app.TimePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import cc.cloudist.acplibrary.ACProgressFlower
import com.bau.bemoss.R
import com.bau.bemoss.helper.MyProgressDialog
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_lighting_device.*
import kotlinx.android.synthetic.main.activity_plug_device.*
import java.text.SimpleDateFormat
import java.util.*

class PlugDeviceActivity : AppCompatActivity() {

    //region Variables Declaration Section

    private var firebaseDatabase: FirebaseDatabase? = null

    private var myRefDatabaseRoot: DatabaseReference? = null
    private var getMyRefDeviceId: DatabaseReference? = null
    private var myRefDeviceType: DatabaseReference? = null

    private var myRefDeviceTitle: DatabaseReference? = null
    private var myRefDeviceStatus: DatabaseReference? = null
    private var myRefSwitchOnTime: DatabaseReference? = null
    private var myRefSwitchOffTime: DatabaseReference? = null

    private var clickedItem: Int = 0
    private var progressDialog: ACProgressFlower? = null

    private var localDeviceTitle: String = "Plug"
    private var localDeviceStatus: Boolean = true
    private var localSwitchOnTime: String = "10:00"
    private var localSwitchOffTime: String = "19:30"

    private lateinit var firebaseDeviceTitle: String
    private var firebaseDeviceStatus: Boolean = false
    private lateinit var firebaseSwitchOnTime: String
    private lateinit var firebaseSwitchOffTime: String

    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plug_device)

        // Get The Id of the RecyclerView Item
        clickedItem = intent.getIntExtra("itemSelected", 0)
        title = "Lighting Device $clickedItem"

        initializeFirebase(clickedItem)
        getDataFromFirebase()
        setLoading(true)

    }

    // Initialize Firebase
    private fun initializeFirebase(deviceId: Int) {
        firebaseDatabase = FirebaseDatabase.getInstance()
        myRefDatabaseRoot = firebaseDatabase!!.getReference("Bemoss Database")
        myRefDeviceType = myRefDatabaseRoot!!.child("Plug Devices")
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
                firebaseSwitchOnTime = snapshot.child("SwitchOnTime").value.toString()
                firebaseSwitchOffTime = snapshot.child("SwitchOffTime").value.toString()

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
                myRefSwitchOnTime = getMyRefDeviceId!!.child("SwitchOnTime")
                myRefSwitchOnTime!!.setValue(localSwitchOnTime)
            }
            4 -> {
                myRefSwitchOffTime = getMyRefDeviceId!!.child("SwitchOffTime")
                myRefSwitchOffTime!!.setValue(localSwitchOffTime)
            }
        }
        Log.d("TAG", "sendDataToFirebase is Okay")
    }

    private fun storeDataLocally() {
        localDeviceTitle = firebaseDeviceTitle
        localDeviceStatus = firebaseDeviceStatus
        localSwitchOnTime = firebaseSwitchOnTime
        localSwitchOffTime = firebaseSwitchOffTime
    }

    // Set the data on The GUI
    private fun setDataOnGUI() {
        setDeviceTitle(localDeviceTitle)
        setDeviceStatus(localDeviceStatus)
        setLocalTime()

        setLoading(false)
        setButtons()
    }


    private fun setDeviceTitle(deviceTitle: String) {
        tv_plug_device_widget_device_status_title.text = deviceTitle
        sendDataToFirebase(1)
    }

    private fun setDeviceStatus(deviceStatus: Boolean) {
        if (deviceStatus) {
            localDeviceStatus = true
            tv_plug_device_widget_device_status_on_off.text = "Status: On"
            btn_plug_device_widget_device_controls_on.setBackgroundResource(R.drawable.selector_options_button_gray)
            btn_plug_device_widget_device_controls_on.setTextColor(Color.parseColor("#000000"))
            btn_plug_device_widget_device_controls_off.setBackgroundResource(R.drawable.selector_options_button_blue)
            btn_plug_device_widget_device_controls_off.setTextColor(Color.parseColor("#ffffff"))
            img_plug_device_widget_device_picture.setImageResource(R.drawable.icon_device_plug_on)
            sendDataToFirebase(2)
        } else if (!deviceStatus) {
            localDeviceStatus = false
            tv_plug_device_widget_device_status_on_off.text = "Status: Off"
            btn_plug_device_widget_device_controls_on.setBackgroundResource(R.drawable.selector_options_button_blue)
            btn_plug_device_widget_device_controls_on.setTextColor(Color.parseColor("#ffffff"))
            btn_plug_device_widget_device_controls_off.setBackgroundResource(R.drawable.selector_options_button_gray)
            btn_plug_device_widget_device_controls_off.setTextColor(Color.parseColor("#000000"))
            img_plug_device_widget_device_picture.setImageResource(R.drawable.icon_device_plug_off)
            sendDataToFirebase(2)
        }
    }

    private fun setLocalTime() {
        tv_plug_device_widget_switch_on_time_selected_time.text = localSwitchOnTime
        tv_plug_device_widget_switch_off_time_selected_time.text = localSwitchOffTime
    }

    private fun setTime(side: Int) {
        if (side == 0) {
            showTimePicker(0)
        } else if (side == 1) {
            showTimePicker(1)
        }
    }


    private fun setButtons() {
        btn_plug_device_widget_device_controls_on.setOnClickListener {
            setDeviceStatus(true)
        }

        btn_plug_device_widget_device_controls_off.setOnClickListener {
            setDeviceStatus(false)
        }

        btn_plug_device_widget_switch_on_time_set_time.setOnClickListener {
            setTime(0)
        }

        btn_plug_device_widget_switch_off_time_set_time.setOnClickListener {
            setTime(1)
        }
    }


    private fun showTimePicker(selected: Int) {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            if (selected == 0) {
                localSwitchOnTime = SimpleDateFormat("HH:mm").format(cal.time)
                sendDataToFirebase(3)
                tv_plug_device_widget_switch_on_time_selected_time.text = localSwitchOnTime
            } else if (selected == 1) {
                localSwitchOffTime = SimpleDateFormat("HH:mm").format(cal.time)
                sendDataToFirebase(4)
                tv_plug_device_widget_switch_off_time_selected_time.text = localSwitchOffTime
            }
        }
        TimePickerDialog(
            this,
            timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
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

