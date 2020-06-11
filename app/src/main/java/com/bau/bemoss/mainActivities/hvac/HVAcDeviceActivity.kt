package com.bau.bemoss.mainActivities.hvac

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import cc.cloudist.acplibrary.ACProgressFlower
import com.bau.bemoss.R
import com.bau.bemoss.helper.MyProgressDialog
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_hvac_device.*


class HVAcDeviceActivity : AppCompatActivity() {

    private var firebaseDatabase: FirebaseDatabase? = null

    private var myRefDatabaseRoot: DatabaseReference? = null
    private var getMyRefDeviceId: DatabaseReference? = null
    private var myRefDeviceType: DatabaseReference? = null

    private var myRefDeviceTitle: DatabaseReference? = null
    private var myRefDeviceStatus: DatabaseReference? = null
    private var myRefDeviceMode: DatabaseReference? = null

    private var myRefHVAcType: DatabaseReference? = null
    private var myRefIndoorTemperature: DatabaseReference? = null
    private var myRefThermostatMode: DatabaseReference? = null
    private var myRefColdSetPoint: DatabaseReference? = null
    private var myRefHeatSetPoint: DatabaseReference? = null
    private var myRefAllowOverride: DatabaseReference? = null
    private var myRefFanMode: DatabaseReference? = null

    private var clickedItem: Int = 0
    private var progressDialog: ACProgressFlower? = null

    private var localDeviceTitle: String = "HVAC"
    private var localDeviceStatus: Boolean = true
    private var localDeviceMode: String = "Cool"
    private var localHVAcType: String = "Air Conditioner"
    private var localIndoorTemperature: String = "20"
    private var localThermostatMode: String = "1"
    private var localHeatSetPoint: String = "00"
    private var localColdSetPoint: String = "00"
    private var localAllowOverride: Boolean = false
    private var localFanMode: Boolean = false

    lateinit var firebaseDeviceTitle: String
    var firebaseDeviceStatus: Boolean = true
    lateinit var firebaseDeviceMode: String
    lateinit var firebaseHVAcType: String
    lateinit var firebaseIndoorTemperature: String
    lateinit var firebaseThermostatMode: String
    lateinit var firebaseHeatSetPoint: String
    lateinit var firebaseColdSetPoint: String
    var firebaseAllowOverride: Boolean = false
    var firebaseFanMode: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hvac_device)

        // Get The Id of the RecyclerView Item
        clickedItem = intent.getIntExtra("itemSelected", 0)
        title = "HVAC Device $clickedItem"

        initializeFirebase(clickedItem)
        getDataFromFirebase()
        setButtons()

        setLoading(true)
        img_hvac_device_widget_device_picture.setImageResource(R.drawable.device_hvac_ac1)
    }

    // Initialize Firebase
    private fun initializeFirebase(deviceId: Int) {
        firebaseDatabase = FirebaseDatabase.getInstance()
        myRefDatabaseRoot = firebaseDatabase!!.getReference("Bemoss Database")
        myRefDeviceType = myRefDatabaseRoot!!.child("HVAC")
        getMyRefDeviceId = myRefDeviceType!!.child("Device$deviceId")
        Log.d("TAG", "initFirebase is Okay")
    }

    private fun createDataInFirebase() {
        sendDataToFirebase(1)
        sendDataToFirebase(2)
        sendDataToFirebase(3)
        sendDataToFirebase(4)
        sendDataToFirebase(5)
        sendDataToFirebase(6)
        sendDataToFirebase(7)
        sendDataToFirebase(8)
        sendDataToFirebase(9)
        sendDataToFirebase(10)
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
                firebaseDeviceMode = snapshot.child("DeviceMode").value.toString()
                firebaseHVAcType = snapshot.child("HVAcType").value.toString()
                firebaseIndoorTemperature = snapshot.child("IndoorTemperature").value.toString()
                firebaseHeatSetPoint = snapshot.child("HeatSetPoint").value.toString()
                firebaseColdSetPoint = snapshot.child("ColdSetPoint").value.toString()
                firebaseThermostatMode = snapshot.child("ThermostatMode").value.toString()
                firebaseAllowOverride = snapshot.child("AllowOverride").value as Boolean
                firebaseFanMode = snapshot.child("FanMode").value as Boolean

                storeDataLocally()
                setDataOnGUI()

                Log.d("TAG", "Data From Firebase Received Successfully")
            }
        })
        Log.d("TAG", "getDataFromFirebase is Okay")
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
                myRefDeviceMode = getMyRefDeviceId!!.child("DeviceMode")
                myRefDeviceMode!!.setValue(localDeviceMode)
            }
            4 -> {
                myRefHVAcType = getMyRefDeviceId!!.child("HVAcType")
                myRefHVAcType!!.setValue(localHVAcType)
            }
            5 -> {
                myRefIndoorTemperature = getMyRefDeviceId!!.child("IndoorTemperature")
                myRefIndoorTemperature!!.setValue(localIndoorTemperature)
            }
            6 -> {
                myRefThermostatMode = getMyRefDeviceId!!.child("ThermostatMode")
                myRefThermostatMode!!.setValue(localThermostatMode)
            }
            7 -> {
                myRefHeatSetPoint = getMyRefDeviceId!!.child("HeatSetPoint")
                myRefHeatSetPoint!!.setValue(localHeatSetPoint)
            }
            8 -> {
                myRefColdSetPoint = getMyRefDeviceId!!.child("ColdSetPoint")
                myRefColdSetPoint!!.setValue(localColdSetPoint)
            }
            9 -> {
                myRefAllowOverride = getMyRefDeviceId!!.child("AllowOverride")
                myRefAllowOverride!!.setValue(localAllowOverride)
            }
            10 -> {
                myRefFanMode = getMyRefDeviceId!!.child("FanMode")
                myRefFanMode!!.setValue(localFanMode)
            }
        }
        Log.d("TAG", "sendDataToFirebase is Okay")
    }


    private fun storeDataLocally() {
        localDeviceTitle = firebaseDeviceTitle
        localDeviceStatus = firebaseDeviceStatus
        localDeviceMode = firebaseDeviceMode
        localHVAcType = firebaseHVAcType
        localIndoorTemperature = firebaseIndoorTemperature
        localThermostatMode = firebaseThermostatMode
        localHeatSetPoint = firebaseHeatSetPoint
        localColdSetPoint = firebaseColdSetPoint
        localAllowOverride = firebaseAllowOverride
        localFanMode = firebaseFanMode
    }

    // Set the data on The GUI
    private fun setDataOnGUI() {
        setIndoorTemp(localIndoorTemperature)
        switchThermostatMode(localThermostatMode)
        setHeatSetPoint(localHeatSetPoint)
        setColdSetPoint(localColdSetPoint)
        switchAllowOverride(localAllowOverride)
        switchFanMode(localFanMode)
        setHVAcPicture(localHVAcType)

        setLoading(false)
    }


    fun initMain() {


    }

    // Enable Action Listener for All the Buttons
    private fun setButtons() {
        btn_hvac_device_widget_thermostat_mode_heat.setOnClickListener {
            switchThermostatMode("0")
        }

        btn_hvac_device_widget_thermostat_mode_cold.setOnClickListener {
            switchThermostatMode("1")
        }

        btn_hvac_device_widget_thermostat_mode_off.setOnClickListener {
            switchThermostatMode("2")
        }

        btn_hvac_device_widget_heat_setPoint_up.setOnClickListener {
            changeHeatSetPoint(true)
        }

        btn_hvac_device_widget_heat_setPoint_down.setOnClickListener {
            changeHeatSetPoint(false)
        }

        btn_hvac_device_widget_cold_setPoint_up.setOnClickListener {
            setColdSetPoint(true)
        }

        btn_hvac_device_widget_cold_setPoint_down.setOnClickListener {
            setColdSetPoint(false)
        }

        btn_hvac_device_widget_allow_override_yes.setOnClickListener {
            switchAllowOverride(true)
        }

        btn_hvac_device_widget_allow_override_no.setOnClickListener {
            switchAllowOverride(false)
        }

        btn_hvac_device_widget_fan_mode_on.setOnClickListener {
            switchFanMode(true)
        }

        btn_hvac_device_widget_fan_mode_auto.setOnClickListener {
            switchFanMode(false)
        }
    }


    private fun setIndoorTemp(indoorTemperature: String) {
        tv_hvac_device_widget_indoor_temperature_temp.text = "$indoorTemperature℃"
        sendDataToFirebase(5)
    }

    private fun switchThermostatMode(selectedMode: String) {
        when (selectedMode.toInt()) {
            0 -> { // Heat
                btn_hvac_device_widget_thermostat_mode_heat.setBackgroundResource(R.drawable.selector_options_button_gray)
                btn_hvac_device_widget_thermostat_mode_cold.setBackgroundResource(R.drawable.selector_options_button_blue)
                btn_hvac_device_widget_thermostat_mode_off.setBackgroundResource(R.drawable.selector_options_button_blue)
                btn_hvac_device_widget_thermostat_mode_heat.setTextColor(Color.parseColor("#000000"))
                btn_hvac_device_widget_thermostat_mode_cold.setTextColor(Color.parseColor("#ffffff"))
                btn_hvac_device_widget_thermostat_mode_off.setTextColor(Color.parseColor("#ffffff"))
            }
            1 -> { // Cold
                btn_hvac_device_widget_thermostat_mode_heat.setBackgroundResource(R.drawable.selector_options_button_blue)
                btn_hvac_device_widget_thermostat_mode_cold.setBackgroundResource(R.drawable.selector_options_button_gray)
                btn_hvac_device_widget_thermostat_mode_off.setBackgroundResource(R.drawable.selector_options_button_blue)
                btn_hvac_device_widget_thermostat_mode_heat.setTextColor(Color.parseColor("#ffffff"))
                btn_hvac_device_widget_thermostat_mode_cold.setTextColor(Color.parseColor("#000000"))
                btn_hvac_device_widget_thermostat_mode_off.setTextColor(Color.parseColor("#ffffff"))
            }
            2 -> { // Off
                btn_hvac_device_widget_thermostat_mode_heat.setBackgroundResource(R.drawable.selector_options_button_blue)
                btn_hvac_device_widget_thermostat_mode_cold.setBackgroundResource(R.drawable.selector_options_button_blue)
                btn_hvac_device_widget_thermostat_mode_off.setBackgroundResource(R.drawable.selector_options_button_gray)
                btn_hvac_device_widget_thermostat_mode_heat.setTextColor(Color.parseColor("#ffffff"))
                btn_hvac_device_widget_thermostat_mode_cold.setTextColor(Color.parseColor("#ffffff"))
                btn_hvac_device_widget_thermostat_mode_off.setTextColor(Color.parseColor("#000000"))
            }
        }
        localThermostatMode = selectedMode
        sendDataToFirebase(6)
    }

    private fun setHeatSetPoint(heatPoint: String) {
        tv_hvac_device_widget_heat_setPoint_temp.text = "$heatPoint℃"
    }

    private fun changeHeatSetPoint(upOrDown: Boolean) {
        if (upOrDown) {
            localHeatSetPoint = (localHeatSetPoint.toInt() + 1).toString()
        } else if (!upOrDown) {
            localHeatSetPoint = (localHeatSetPoint.toInt() - 1).toString()
        }
        setHeatSetPoint(localHeatSetPoint)
        sendDataToFirebase(7)
    }

    private fun setColdSetPoint(coldPoint: String) {
        tv_hvac_device_widget_cold_setPoint_temp.text = "$coldPoint℃"
    }

    private fun setColdSetPoint(upOrDown: Boolean) {
        if (upOrDown) {
            localColdSetPoint = (localColdSetPoint.toInt() + 1).toString()
        } else if (!upOrDown) {
            localColdSetPoint = (localColdSetPoint.toInt() - 1).toString()
        }
        setColdSetPoint(localColdSetPoint)
        sendDataToFirebase(8)
    }

    private fun switchAllowOverride(onOrOff: Boolean) {
        if (onOrOff) {
            btn_hvac_device_widget_allow_override_yes.setBackgroundResource(R.drawable.selector_options_button_gray)
            btn_hvac_device_widget_allow_override_no.setBackgroundResource(R.drawable.selector_options_button_blue)
            btn_hvac_device_widget_allow_override_yes.setTextColor(Color.parseColor("#000000"))
            btn_hvac_device_widget_allow_override_no.setTextColor(Color.parseColor("#ffffff"))
            localAllowOverride = true
        } else if (!onOrOff) {
            btn_hvac_device_widget_allow_override_yes.setBackgroundResource(R.drawable.selector_options_button_blue)
            btn_hvac_device_widget_allow_override_no.setBackgroundResource(R.drawable.selector_options_button_gray)
            btn_hvac_device_widget_allow_override_yes.setTextColor(Color.parseColor("#ffffff"))
            btn_hvac_device_widget_allow_override_no.setTextColor(Color.parseColor("#000000"))
            localAllowOverride = false
        }
        sendDataToFirebase(9)
    }

    private fun switchFanMode(onOrAuto: Boolean) {
        if (onOrAuto) {
            btn_hvac_device_widget_fan_mode_on.setBackgroundResource(R.drawable.selector_options_button_gray)
            btn_hvac_device_widget_fan_mode_auto.setBackgroundResource(R.drawable.selector_options_button_blue)
            btn_hvac_device_widget_fan_mode_on.setTextColor(Color.parseColor("#000000"))
            btn_hvac_device_widget_fan_mode_auto.setTextColor(Color.parseColor("#ffffff"))
            localFanMode = true
        } else if (!onOrAuto) {
            btn_hvac_device_widget_fan_mode_on.setBackgroundResource(R.drawable.selector_options_button_blue)
            btn_hvac_device_widget_fan_mode_auto.setBackgroundResource(R.drawable.selector_options_button_gray)
            btn_hvac_device_widget_fan_mode_on.setTextColor(Color.parseColor("#ffffff"))
            btn_hvac_device_widget_fan_mode_auto.setTextColor(Color.parseColor("#000000"))
            localFanMode = false
        }
        sendDataToFirebase(10)
    }

    fun setHVAcPicture(selected: String) {
        if (selected == "Air Conditioner") {
            setDevicePicture(0)
        } else if (selected == "Electric Heater") {
            setDevicePicture(1)
        } else if (selected == "Water Boiler") {
            setDevicePicture(2)
        } else if (selected == "Oil Heater") {
            setDevicePicture(3)
        }

    }

    private fun setDevicePicture(selected: Int) {
        when (selected) {
            0 -> { // Heat
                img_hvac_device_widget_device_picture.setImageResource(R.drawable.device_hvac_ac1)
            }
            1 -> { // Cold
                img_hvac_device_widget_device_picture.setImageResource(R.drawable.device_hvac_ac2)
            }
            2 -> { // Cold
                img_hvac_device_widget_device_picture.setImageResource(R.drawable.device_hvac_ac3)
            }
            3 -> { // Cold
                img_hvac_device_widget_device_picture.setImageResource(R.drawable.device_hvac_ac4)
            }
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

