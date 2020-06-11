package com.bau.bemoss.mainActivities.plug.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bau.bemoss.R
import com.bau.bemoss.mainActivities.hvac.HVAcDeviceActivity
import com.bau.bemoss.mainActivities.plug.PlugDeviceActivity
import com.bau.bemoss.models.PlugModel

class PlugAdapter(private val list: ArrayList<PlugModel>) :
    RecyclerView.Adapter<PlugAdapter.MyViewHolder>() {
    private lateinit var mActivity: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        this.mActivity = parent.context
        return MyViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: PlugModel = list.get(index = position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            intent(PlugDeviceActivity::class.java, item.id)
        }

    }

    override fun getItemCount(): Int = list.size

    class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.card_list_device, parent, false)) {

        private var tvTitle: TextView? = null
        private var imgImage: ImageView? = null
        private var tvDeviceType: TextView? = null
        private var tvDeviceStatus: TextView? = null

        init {
            tvTitle = itemView.findViewById(R.id.tv_card_device_title)
            imgImage = itemView.findViewById(R.id.img_card_device_icon)
            tvDeviceType = itemView.findViewById(R.id.tv_card_device_type)
            tvDeviceStatus = itemView.findViewById(R.id.tv_card_device_status)
        }

        fun bind(item: PlugModel) {
            tvTitle?.text = item.title
            imgImage?.setImageResource(item.image)
            tvDeviceType?.text = item.deviceType
            tvDeviceStatus?.text = item.deviceStatus
        }
    }

    private fun <T> intent(it: Class<T>, sentValue: Int) {
        val intent = Intent(mActivity, it)
        intent.putExtra("itemSelected", sentValue)
        mActivity.startActivity(intent)
    }
}

