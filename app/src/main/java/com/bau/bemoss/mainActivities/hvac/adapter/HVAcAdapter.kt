package com.bau.bemoss.mainActivities.hvac.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bau.bemoss.R
import com.bau.bemoss.mainActivities.hvac.HVAcDeviceActivity
import com.bau.bemoss.models.HVAcModel

class HVAcAdapter(private val list: ArrayList<HVAcModel>)
    : RecyclerView.Adapter<HVAcAdapter.MyViewHolder>() {
    private lateinit var mActivity: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        this.mActivity = parent.context
        return MyViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: HVAcModel = list.get(index = position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            intent(HVAcDeviceActivity::class.java, item.id)
        }

    }

    override fun getItemCount(): Int = list.size

    class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.card_list_device_hvac, parent, false)) {

        var tvTitle: TextView? = null
        var tvTempMode: TextView? = null
        private var tvTemperature: TextView? = null
        private var tvDeviceType: TextView? = null
        private var tvDeviceStatus: TextView? = null

        init {
            tvTitle = itemView.findViewById(R.id.tv_card_device_hvac_title)
            tvTempMode = itemView.findViewById(R.id.tv_card_device_hvac_temperature_mode)
            tvTemperature = itemView.findViewById(R.id.tv_card_device_hvac_temperature_degree)
            tvDeviceType = itemView.findViewById(R.id.tv_card_device_hvac_type)
            tvDeviceStatus = itemView.findViewById(R.id.tv_card_device_hvac_status)
        }

        fun bind(item: HVAcModel) {
            tvTitle?.text = item.title
            tvTempMode?.text = item.deviceMode
            tvTemperature?.text = item.temperature
            tvDeviceType?.text = item.deviceType
            if (item.deviceStatus) { tvDeviceStatus?.text = "Status: On" }
            else if (!item.deviceStatus) { tvDeviceStatus?.text = "Status: Off" }
        }
    }

    private fun <T> intent(it: Class<T>, sentValue: Int){
        val intent = Intent(mActivity, it)
        intent.putExtra("itemSelected", sentValue)
        mActivity.startActivity(intent)
    }
}

