package com.example.appiness.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appiness.R
import com.example.appiness.data.model.BakersResponseModel


class BakersAdapter(private var bakersList: List<BakersResponseModel>) :
    RecyclerView.Adapter<BakersAdapter.BakersViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BakersViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return BakersViewHolder(v)
    }

    override fun getItemCount(): Int {
        return bakersList.size
    }

    override fun onBindViewHolder(holder: BakersViewHolder, position: Int) {
        holder.bindItems(bakersList[position])
    }

    class BakersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(bakersResponseModel: BakersResponseModel) {
            val title = itemView.findViewById<TextView>(R.id.title)
            val bakersNumber = itemView.findViewById<TextView>(R.id.bakersNumber)
            val by = itemView.findViewById<TextView>(R.id.by)

            title.text = bakersResponseModel.title
            bakersNumber.text = bakersResponseModel.backersNumber
            by.text = bakersResponseModel.by
        }
    }

    fun updateList(list: List<BakersResponseModel>) {
        bakersList = list
        notifyDataSetChanged()
    }

}