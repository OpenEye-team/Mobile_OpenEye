package com.txtlabs.openeye.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.txtlabs.openeye.R

class ArticleAdapter(private val historyList: List<ExamData>) :
    RecyclerView.Adapter<ArticleAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tv_item_title)
        private val desc: TextView = itemView.findViewById(R.id.tv_item_published_date)
        private val image: ImageView = itemView.findViewById(R.id.img_poster)

        fun bind(history: ExamData) {
            title.text = history.title
            desc.text = history.description
            Glide.with(itemView.context)
                .load(history.image)
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article, parent, false)
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