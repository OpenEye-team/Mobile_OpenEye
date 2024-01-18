package com.txtlabs.openeye.ui.tracker

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.txtlabs.openeye.R
import com.txtlabs.openeye.data.response.DataItem

class ListTrackerAdapter(private val historyList: List<DataItem>) :
    RecyclerView.Adapter<ListTrackerAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tanggalTextView: TextView = itemView.findViewById(R.id.tv_tanngal)
        private val jamTextView: TextView = itemView.findViewById(R.id.tv_jam)
        private val glucoseTextView: TextView = itemView.findViewById(R.id.tv_blood)
        private val mealsTextView: TextView = itemView.findViewById(R.id.tv_meals)

        @SuppressLint("SetTextI18n")
        fun bind(history: DataItem) {
            val date = history.date
            val dateCut = date?.substring(0, 10)

            tanggalTextView.text = dateCut
            jamTextView.text = history.time
            glucoseTextView.text = history.value.toString() + " md/dl"
            mealsTextView.text = history.meal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tracker, parent, false)
        return HistoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val currentArticle = historyList[position]
        holder.bind(currentArticle)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }
}