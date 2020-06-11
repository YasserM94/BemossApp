package com.bau.bemoss.mainActivities.lighting

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import cc.cloudist.acplibrary.ACProgressFlower
import codes.side.andcolorpicker.converter.*
import codes.side.andcolorpicker.group.PickerGroup
import codes.side.andcolorpicker.group.registerPickers
import codes.side.andcolorpicker.hsl.HSLColorPickerSeekBar
import codes.side.andcolorpicker.model.IntegerHSLColor
import codes.side.andcolorpicker.view.picker.ColorSeekBar
import com.bau.bemoss.R
import com.bau.bemoss.helper.MyProgressDialog
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_hvac_device.*
import kotlinx.android.synthetic.main.activity_lighting_device.*


class LightingDeviceActivity : AppCompatActivity() {

    //region Variables Declaration Section

    private var firebaseDatabase: FirebaseDatabase? = null

    private var myRefDatabaseRoot: DatabaseReference? = null
    private var getMyRefDeviceId: DatabaseReference? = null
    private var myRefDeviceType: DatabaseReference? = null

    private var myRefDeviceTitle: DatabaseReference? = null
    private var myRefDeviceStatus: DatabaseReference? = null

    private var myRefLightType: DatabaseReference? = null
    private var myRefLightColor: DatabaseReference? = null
    private var myRefLightPickerColor: DatabaseReference? = null
    private var myRefLightBrightness: DatabaseReference? = null
    private var myRefLightScene: DatabaseReference? = null

    private var clickedItem: Int = 0
    private var progressDialog: ACProgressFlower? = null

    private var localDeviceTitle: String = "Light"
    private var localDeviceLocation: String = "Room Name"
    private var localDeviceStatus: Boolean = true
    private var localLightType: String = "Philips Hue"
    private var localLightColor: String = "00ff00"
    private var localLightPickerColor: String = "-23040"
    private var localLightBrightness: String = "80"
    private var localLightScene: String = "2"

    private lateinit var firebaseDeviceTitle: String
    private lateinit var firebaseDeviceLocation: String
    private var firebaseDeviceStatus: Boolean = true
    private lateinit var firebaseLightType: String
    private lateinit var firebaseLightColor: String
    private lateinit var firebaseLightPickerColor: String
    private lateinit var firebaseLightBrightness: String
    private lateinit var firebaseLightScene: String

    private val pickerGroup = PickerGroup<IntegerHSLColor>()

    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lighting_device)

        // Get The Id of the RecyclerView Item
        clickedItem = intent.getIntExtra("itemSelected", 0)
        title = "Lighting Device $clickedItem"


        initializeFirebase(clickedItem)
        setLoading(true)
        getDataFromFirebase()


        pickerGroup.also {
            it.registerPickers(
                hueColorPickerSeekBarHue,
                hueColorPickerSeekBarLightness
            )
        }
        colorPicker()
    }


    private fun colorPicker() {
        pickerGroup.addListener(
            object : HSLColorPickerSeekBar.DefaultOnColorPickListener() {
                override fun onColorChanged(
                    picker: ColorSeekBar<IntegerHSLColor>,
                    color: IntegerHSLColor,
                    value: Int
                ) {
                    var colorHEX = String.format(
                        "#%02x%02x%02x",
                        color.getRInt(),
                        color.getGInt(),
                        color.getBInt()
                    )
                    setLightColor(colorHEX, color.toColorInt().toString())
                    Log.d("TAG", "$color picked")
//                    swatchView.setSwatchColor(color)
                }
            }
        )
    }

    // Initialize Firebase
    private fun initializeFirebase(deviceId: Int) {
        firebaseDatabase = FirebaseDatabase.getInstance()
        myRefDatabaseRoot = firebaseDatabase!!.getReference("Bemoss Database")
        myRefDeviceType = myRefDatabaseRoot!!.child("Lighting Devices")
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
                firebaseDeviceLocation = snapshot.child("DeviceLocation").value.toString()
                firebaseDeviceStatus = snapshot.child("DeviceStatus").value as Boolean
                firebaseLightType = snapshot.child("LightType").value.toString()
                firebaseLightColor = snapshot.child("LightColor").value.toString()
                firebaseLightPickerColor = snapshot.child("LightPickerColor").value.toString()
                firebaseLightBrightness = snapshot.child("LightBrightness").value.toString()
                firebaseLightScene = snapshot.child("LightScene").value.toString()

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
                myRefLightType = getMyRefDeviceId!!.child("LightType")
                myRefLightType!!.setValue(localLightType)
            }
            4 -> {
                myRefLightColor = getMyRefDeviceId!!.child("LightColor")
                myRefLightColor!!.setValue(localLightColor)
                myRefLightPickerColor = getMyRefDeviceId!!.child("LightPickerColor")
                myRefLightPickerColor!!.setValue(localLightPickerColor)
            }
            5 -> {
                myRefLightScene = getMyRefDeviceId!!.child("LightScene")
                myRefLightScene!!.setValue(localLightScene)
            }
        }
        Log.d("TAG", "sendDataToFirebase is Okay")
    }


    private fun storeDataLocally() {
        localDeviceTitle = firebaseDeviceTitle
        localDeviceLocation = firebaseDeviceLocation
        localDeviceStatus = firebaseDeviceStatus
        localLightType = firebaseLightType
        localLightColor = firebaseLightColor
        localLightPickerColor = firebaseLightPickerColor
        localLightBrightness = firebaseLightBrightness
        localLightScene = firebaseLightScene
    }

    // Set the data on The GUI
    private fun setDataOnGUI() {
        setDeviceTitle(localDeviceLocation)
        setDeviceStatus(localDeviceStatus)
        setLightColor(localLightColor, localLightPickerColor)
        setLightScene(localLightScene)

        setLoading(false)
        setButtons()
    }

    // Set device title
    private fun setDeviceTitle(deviceTitle: String) {
        tv_lighting_device_widget_device_status_title.text = deviceTitle
        sendDataToFirebase(1)
    }

    // Set device status
    private fun setDeviceStatus(deviceStatus: Boolean) {
        if (deviceStatus) {
            localDeviceStatus = true
            tv_lighting_device_widget_device_status_on_off.text = "Status: On"
            btn_lighting_device_widget_device_controls_on.setBackgroundResource(R.drawable.selector_options_button_gray)
            btn_lighting_device_widget_device_controls_on.setTextColor(Color.parseColor("#000000"))
            btn_lighting_device_widget_device_controls_off.setBackgroundResource(R.drawable.selector_options_button_blue)
            btn_lighting_device_widget_device_controls_off.setTextColor(Color.parseColor("#ffffff"))
            img_lighting_device_widget_device_picture_base.setImageResource(R.drawable.icon_device_bulb_on1)
            img_lighting_device_widget_device_picture.visibility = View.VISIBLE
            sendDataToFirebase(2)
        } else if (!deviceStatus) {
            localDeviceStatus = false
            tv_lighting_device_widget_device_status_on_off.text = "Status: Off"
            btn_lighting_device_widget_device_controls_on.setBackgroundResource(R.drawable.selector_options_button_blue)
            btn_lighting_device_widget_device_controls_on.setTextColor(Color.parseColor("#ffffff"))
            btn_lighting_device_widget_device_controls_off.setBackgroundResource(R.drawable.selector_options_button_gray)
            btn_lighting_device_widget_device_controls_off.setTextColor(Color.parseColor("#000000"))
            img_lighting_device_widget_device_picture_base.setImageResource(R.drawable.icon_device_bulb_off)
            img_lighting_device_widget_device_picture.visibility = View.INVISIBLE
            sendDataToFirebase(2)
        }
    }

    private fun setLightType() {

    }

    private fun setLightColor(selectedColor: String, pickerColor: String) {
        pickerGroup.setColor(IntegerHSLColor().also {
            it.setFromColorInt(pickerColor.toInt())
        })
        localLightColor = selectedColor
        localLightPickerColor = pickerColor
        img_lighting_device_widget_device_picture.setColorFilter(Color.parseColor(selectedColor))
        sendDataToFirebase(4)
    }


    private fun setLightScene(selectedMode: String) {
        when (selectedMode.toInt()) {
            0 -> { // Heat
                btn_lighting_device_widget_device_scene_btn1.setBackgroundResource(R.drawable.selector_options_button_gray)
                btn_lighting_device_widget_device_scene_btn2.setBackgroundResource(R.drawable.selector_options_button_blue)
                btn_lighting_device_widget_device_scene_btn3.setBackgroundResource(R.drawable.selector_options_button_blue)
                btn_lighting_device_widget_device_scene_btn4.setBackgroundResource(R.drawable.selector_options_button_blue)
                btn_lighting_device_widget_device_scene_btn1.setTextColor(Color.parseColor("#000000"))
                btn_lighting_device_widget_device_scene_btn2.setTextColor(Color.parseColor("#ffffff"))
                btn_lighting_device_widget_device_scene_btn3.setTextColor(Color.parseColor("#ffffff"))
                btn_lighting_device_widget_device_scene_btn4.setTextColor(Color.parseColor("#ffffff"))
            }
            1 -> { // Cold
                btn_lighting_device_widget_device_scene_btn1.setBackgroundResource(R.drawable.selector_options_button_blue)
                btn_lighting_device_widget_device_scene_btn2.setBackgroundResource(R.drawable.selector_options_button_gray)
                btn_lighting_device_widget_device_scene_btn3.setBackgroundResource(R.drawable.selector_options_button_blue)
                btn_lighting_device_widget_device_scene_btn4.setBackgroundResource(R.drawable.selector_options_button_blue)
                btn_lighting_device_widget_device_scene_btn1.setTextColor(Color.parseColor("#ffffff"))
                btn_lighting_device_widget_device_scene_btn2.setTextColor(Color.parseColor("#000000"))
                btn_lighting_device_widget_device_scene_btn3.setTextColor(Color.parseColor("#ffffff"))
                btn_lighting_device_widget_device_scene_btn4.setTextColor(Color.parseColor("#ffffff"))
            }
            2 -> { // Off
                btn_lighting_device_widget_device_scene_btn1.setBackgroundResource(R.drawable.selector_options_button_blue)
                btn_lighting_device_widget_device_scene_btn2.setBackgroundResource(R.drawable.selector_options_button_blue)
                btn_lighting_device_widget_device_scene_btn3.setBackgroundResource(R.drawable.selector_options_button_gray)
                btn_lighting_device_widget_device_scene_btn4.setBackgroundResource(R.drawable.selector_options_button_blue)
                btn_lighting_device_widget_device_scene_btn1.setTextColor(Color.parseColor("#ffffff"))
                btn_lighting_device_widget_device_scene_btn2.setTextColor(Color.parseColor("#ffffff"))
                btn_lighting_device_widget_device_scene_btn3.setTextColor(Color.parseColor("#000000"))
                btn_lighting_device_widget_device_scene_btn4.setTextColor(Color.parseColor("#ffffff"))
            }
            3 -> { // Off
                btn_lighting_device_widget_device_scene_btn1.setBackgroundResource(R.drawable.selector_options_button_blue)
                btn_lighting_device_widget_device_scene_btn2.setBackgroundResource(R.drawable.selector_options_button_blue)
                btn_lighting_device_widget_device_scene_btn3.setBackgroundResource(R.drawable.selector_options_button_blue)
                btn_lighting_device_widget_device_scene_btn4.setBackgroundResource(R.drawable.selector_options_button_gray)
                btn_lighting_device_widget_device_scene_btn1.setTextColor(Color.parseColor("#ffffff"))
                btn_lighting_device_widget_device_scene_btn2.setTextColor(Color.parseColor("#ffffff"))
                btn_lighting_device_widget_device_scene_btn3.setTextColor(Color.parseColor("#ffffff"))
                btn_lighting_device_widget_device_scene_btn4.setTextColor(Color.parseColor("#000000"))
            }
        }
        localLightScene = selectedMode
        sendDataToFirebase(5)
    }

    private fun setLoading(status: Boolean) {
        if (status) {
            progressDialog = MyProgressDialog().getInstance(this)
            progressDialog!!.show()
        } else if (!status) {
            progressDialog!!.dismiss()
        }
    }

    private fun setButtons() {
        setStatusBtn()
        setScenesBtn()
    }

    private fun setStatusBtn() {
        btn_lighting_device_widget_device_controls_on.setOnClickListener {
            setDeviceStatus(true)
        }
        btn_lighting_device_widget_device_controls_off.setOnClickListener {
            setDeviceStatus(false)
        }
    }

    private fun setScenesBtn() {
        btn_lighting_device_widget_device_scene_btn1.setOnClickListener {
            setLightScene("0")
        }
        btn_lighting_device_widget_device_scene_btn2.setOnClickListener {
            setLightScene("1")
        }
        btn_lighting_device_widget_device_scene_btn3.setOnClickListener {
            setLightScene("2")
        }
        btn_lighting_device_widget_device_scene_btn4.setOnClickListener {
            setLightScene("3")
        }
    }

    // Set Loading Animation for The Activity
    fun loading() {
        val progressBar = ProgressBar(this)
        progressBar.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        conLayout_lighting_device?.addView(progressBar)
        progressBar.visibility = View.VISIBLE
    }


}

