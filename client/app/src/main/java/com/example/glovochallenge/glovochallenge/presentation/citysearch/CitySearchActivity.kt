package com.example.glovochallenge.glovochallenge.presentation.citysearch

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.glovochallenge.glovochallenge.R
import com.example.glovochallenge.glovochallenge.presentation.citysearch.list.CitySearchAdapter
import com.example.glovochallenge.glovochallenge.presentation.citysearch.model.CitySearchItem
import javax.inject.Inject

class CitySearchActivity: Activity() , CitySearchView {

    @Inject
    lateinit var presenter: CitySearchPresenter

    @Inject
    lateinit var adapter: CitySearchAdapter

    private lateinit var citySearchRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_city_search)

        citySearchRecyclerView = findViewById(R.id.citySearchRecyclerView)

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        citySearchRecyclerView.addItemDecoration(itemDecoration)
        citySearchRecyclerView.layoutManager = LinearLayoutManager(this)
        citySearchRecyclerView.adapter = adapter
    }

    override fun showCityGroup(items: List<CitySearchItem>) {
        adapter.items = items
        adapter.notifyDataSetChanged()
    }
}
