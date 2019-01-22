package com.example.glovochallenge.glovochallenge.presentation.citysearch.list

import android.annotation.SuppressLint
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.glovochallenge.glovochallenge.R
import android.view.LayoutInflater
import com.example.glovochallenge.glovochallenge.presentation.citysearch.model.CitySearchItem
import java.util.*

class CitySearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items : List<CitySearchItem> = emptyList()

    companion object {
        const val TYPE_COUNTRY = 1
        const val TYPE_CITY = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_COUNTRY -> CountryViewHolder(inflateLayout(R.layout.layout_country_row, parent))
            TYPE_CITY -> CountryViewHolder(inflateLayout(R.layout.layout_city_row, parent))
            else -> throw IllegalArgumentException(String.format(Locale.US, "Type %d - not support", viewType))
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val type = getItemViewType(position)
        when(type) {
            TYPE_COUNTRY -> {
                val country = items[position] as CitySearchItem.CountryItem
                val holder = viewHolder as CountryViewHolder
                holder.countryNameTextView.text = country.countryName
            }
            TYPE_CITY -> {
                val city = items[position] as CitySearchItem.CityItem
                val holder = viewHolder as CityViewHolder
                holder.cityNameTextView.text = city.cityName
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is CitySearchItem.CountryItem -> TYPE_COUNTRY
            is CitySearchItem.CityItem -> TYPE_CITY
        }

    }

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val countryNameTextView: TextView = itemView.findViewById(R.id.countryNameTextView)
    }

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityNameTextView: TextView = itemView.findViewById(R.id.cityNameTextView)
    }

    private fun inflateLayout(@LayoutRes layoutRes: Int, parent: ViewGroup): View =
        LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
}