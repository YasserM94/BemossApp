package com.bau.bemoss.mainActivities.dashboard.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bau.bemoss.R
import com.bau.bemoss.mainActivities.hvac.HVAcListActivity
import com.bau.bemoss.mainActivities.lighting.LightingListActivity
import com.bau.bemoss.mainActivities.plug.PlugListActivity
import com.bau.bemoss.mainActivities.sensor.SensorListActivity
import com.bau.bemoss.models.DashboardModel
import com.bau.bemoss.models.HVAcModel

class DashboardAdapter(private val list: ArrayList<DashboardModel>)
    : RecyclerView.Adapter<DashboardAdapter.MyViewHolder>() {
    private lateinit var mActivity: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        this.mActivity = parent.context
        return MyViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: DashboardModel = list.get(index = position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            when(item.id) {
                1 -> { intent(HVAcListActivity::class.java) }
                2 -> { intent(LightingListActivity::class.java) }
                3 -> { intent(PlugListActivity::class.java) }
                4 -> { intent(SensorListActivity::class.java) }
                else -> Toast.makeText(it.context, " IDK ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = list.size

    class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.card_dashboard, parent, false)) {

        private var tvTitle: TextView? = null
        private var imgImage: ImageView? = null
        private var tvDeviceCount: TextView? = null

        init {
            tvTitle = itemView.findViewById(R.id.tv_card_dashboard_title)
            imgImage = itemView.findViewById(R.id.img_card_dashboard_icon)
            tvDeviceCount = itemView.findViewById(R.id.tv_card_dashboard_connected_devices)
        }

        fun bind(item: DashboardModel) {
            tvTitle?.text = item.name
            imgImage?.setImageResource(item.image)
//            Picasso.get().load(item.image).into(image)
            val deviceCount = item.devicesCount
            tvDeviceCount?.text = "Devices Connected: $deviceCount"

        }

    }

    private fun <T> intent(it: Class<T>){
        val intent = Intent(mActivity, it)
        mActivity.startActivity(intent)
    }

}
