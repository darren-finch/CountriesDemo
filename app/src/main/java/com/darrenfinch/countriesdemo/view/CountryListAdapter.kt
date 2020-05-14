package com.darrenfinch.countriesdemo.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darrenfinch.countriesdemo.R
import com.darrenfinch.countriesdemo.model.Country

class CountryListAdapter(private val countries: MutableList<Country>) : RecyclerView.Adapter<CountryViewHolder>()
{
    fun updateCountries(newCountries: List<Country>)
    {
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : CountryViewHolder
    {
        return CountryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false))
    }

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) = holder.bind(countries[position])
}