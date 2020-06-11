package com.bau.bemoss.mainActivities.chart

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.aachartmodel.aainfographics.AAInfographicsLib.AAChartCreator.AAChartModel
import com.aachartmodel.aainfographics.AAInfographicsLib.AAChartCreator.AAChartType
import com.aachartmodel.aainfographics.AAInfographicsLib.AAChartCreator.AASeriesElement
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.bau.bemoss.R
import kotlinx.android.synthetic.main.activity_chart.*
import org.json.JSONException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChartActivity : AppCompatActivity() {


    private var aaChartModelPieChart = AAChartModel()
    private var aaChartModelElectricity = AAChartModel()
    private var aaChartModelTemperature = AAChartModel()
    private var aaChartModelHumidity = AAChartModel()
    private var aaChartModelPressure = AAChartModel()

    private var arrayListPieChart: ArrayList<Any> = ArrayList<Any>()
    private var arrayListElectricity: ArrayList<Any> = ArrayList<Any>()
    private var arrayListTemperature: ArrayList<Any> = ArrayList<Any>()
    private var arrayListHumidity: ArrayList<Any> = ArrayList<Any>()
    private var arrayListPressure: ArrayList<Any> = ArrayList<Any>()

    private var volleyRequestQueue: RequestQueue? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        title = "Charts"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getDataFromJson()
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDataFromJson() {
        volleyRequestQueue = Volley.newRequestQueue(this)

        var url = "https://bemoss-e8288.firebaseio.com/Device1.json"
        val reqElectricity = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    for (i in 0 until response.length()) {
                        val entry = response.getJSONObject(i)
                        val time = entry.getString("time")
                        val power = entry.getDouble("power")
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS'Z'")
                        val date = LocalDateTime.parse(time, formatter)
                        arrayListElectricity.add(power)
                    }
                    setChartElectricity()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error -> error.printStackTrace() })
        volleyRequestQueue?.add(reqElectricity)

        url = "https://bemoss-e8288.firebaseio.com/Device2.json"
        val reqTemperature = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    for (i in 0 until response.length()) {
                        val entry = response.getJSONObject(i)
                        val time = entry.getString("time")
                        val power = entry.getDouble("power")
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS'Z'")
                        val date = LocalDateTime.parse(time, formatter)
                        arrayListTemperature.add(power)
                    }
                    setChartTemperature()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error -> error.printStackTrace() })
        volleyRequestQueue?.add(reqTemperature)

        url = "https://bemoss-e8288.firebaseio.com/Device3.json"
        val reqHumidity = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    for (i in 0 until response.length()) {
                        val entry = response.getJSONObject(i)
                        val time = entry.getString("time")
                        val power = entry.getDouble("power")
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS'Z'")
                        val date = LocalDateTime.parse(time, formatter)
                        arrayListHumidity.add(power)
                    }
                    setChartHumidity()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error -> error.printStackTrace() })
        volleyRequestQueue?.add(reqHumidity)

        url = "https://bemoss-e8288.firebaseio.com/Device4.json"
        val reqPressure = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    for (i in 0 until response.length()) {
                        val entry = response.getJSONObject(i)
                        val time = entry.getString("time")
                        val power = entry.getDouble("power")
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS'Z'")
                        val date = LocalDateTime.parse(time, formatter)
                        arrayListPressure.add(power)
                    }
                    setChartPressure()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error -> error.printStackTrace() })
        volleyRequestQueue?.add(reqPressure)

    }


    private fun setChartElectricity() {
        aaChartModelElectricity = AAChartModel()
            .chartType(AAChartType.Area)
            .title("Energy Usage - May 2020")
            .titleFontColor("#fff")
//            .subtitle("subtitle")
            .backgroundColor("#7d81f6")
//            .dataLabelsFontColor("#fff")
            .dataLabelsEnabled(false)
            .legendEnabled(false)
            .markerRadius(3.6f)
//            .borderRadius(14f)
            .yAxisTitle("Power (Watt)")
            .categories(arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"))
//            .dataLabelEnabled(false)
            .axesTextColor("#fff")
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("")
                        .data(convertToArray(arrayListElectricity))
                        .color("#ede977")
                )
            )
        AAChartViewElectricity?.aa_drawChartWithChartModel(aaChartModelElectricity)
    }

    private fun setChartTemperature() {

        aaChartModelTemperature = AAChartModel()
            .chartType(AAChartType.Area)
            .title("Energy Usage - April 2020")
            .titleFontColor("#fff")
//            .subtitle("subtitle")
            .backgroundColor("#7d81f6")
//            .dataLabelsFontColor("#fff")
            .dataLabelsEnabled(false)
            .legendEnabled(false)
            .markerRadius(3.6f)
//            .borderRadius(14f)
            .yAxisTitle("Power (Watt)")
            .categories(arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"))
//            .dataLabelEnabled(false)
            .axesTextColor("#fff")
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("")
                        .data(convertToArray(arrayListTemperature))
                        .color("#8de2ff")
                )
            )
        AAChartViewTemperature?.aa_drawChartWithChartModel(aaChartModelTemperature)
    }


    private fun setChartHumidity() {

        aaChartModelHumidity = AAChartModel()
            .chartType(AAChartType.Area)
            .title("Energy Usage - March 2020")
            .titleFontColor("#fff")
//            .subtitle("subtitle")
            .backgroundColor("#7d81f6")
//            .dataLabelsFontColor("#fff")
            .dataLabelsEnabled(false)
            .legendEnabled(false)
            .markerRadius(3.6f)
//            .borderRadius(14f)
            .yAxisTitle("Power (Watt)")
            .categories(arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"))
//            .dataLabelEnabled(false)
            .axesTextColor("#fff")
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("")
                        .data(convertToArray(arrayListHumidity))
                        .color("#ff99e5")
                )
            )
        AAChartViewHumidity?.aa_drawChartWithChartModel(aaChartModelHumidity)
    }

    private fun setChartPressure() {

        aaChartModelPressure = AAChartModel()
            .chartType(AAChartType.Area)
            .title("Energy Usage - February 2020")
            .titleFontColor("#fff")
//            .subtitle("subtitle")
            .backgroundColor("#7d81f6")
//            .dataLabelsFontColor("#fff")
            .dataLabelsEnabled(false)
            .legendEnabled(false)
            .markerRadius(3.6f)
//            .borderRadius(14f)
            .yAxisTitle("Power (Watt)")
            .categories(arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"))
//            .dataLabelEnabled(false)
            .axesTextColor("#fff")
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("")
                        .data(convertToArray(arrayListPressure))
                        .color("#d9bcae")
                )
            )
        AAChartViewPressure?.aa_drawChartWithChartModel(aaChartModelPressure)
    }


    fun setPieChart() {

    }


    private fun convertToArray(receivedArrayList: ArrayList<Any>): Array<Any> {
        var theArray: Array<Any> = Array<Any>(receivedArrayList.size) { 0 }

        // ArrayList to Array Conversion
        for (i in theArray.indices) {
            theArray[i] = receivedArrayList[i]
        }
        return theArray
    }


}









