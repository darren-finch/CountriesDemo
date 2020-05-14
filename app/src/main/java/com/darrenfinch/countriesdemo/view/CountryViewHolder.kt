package com.darrenfinch.countriesdemo.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.darrenfinch.countriesdemo.model.Country
import com.darrenfinch.countriesdemo.util.getProgressDrawable
import com.darrenfinch.countriesdemo.util.loadImage
import kotlinx.android.synthetic.main.item_country.view.*

class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    private val progressDrawable = getProgressDrawable(itemView.context)
    private val countryNameTextView: TextView = itemView.countryName
    private val countryFlagImageView: ImageView = itemView.flagImage
    private val countryCapital: TextView = itemView.countryCapital

    fun bind(countryData: Country)
    {
        countryFlagImageView.loadImage(countryData.flagUrl, progressDrawable)
        countryNameTextView.text = countryData.countryName
        countryCapital.text = countryData.capital
    }
}