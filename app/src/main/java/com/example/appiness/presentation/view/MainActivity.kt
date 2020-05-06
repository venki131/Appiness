package com.example.appiness.presentation.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appiness.R
import com.example.appiness.core.application.di.ViewModelProviderFactory
import com.example.appiness.core.others.Status
import com.example.appiness.data.datasource.RemoteApi
import com.example.appiness.data.model.BakersResponseModel
import com.example.appiness.presentation.view.adapter.BakersAdapter
import com.example.appiness.presentation.viewmodels.MainViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {
    private lateinit var adapter: BakersAdapter
    private lateinit var bakersList: List<BakersResponseModel>

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var remoteApi: RemoteApi

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this, providerFactory).get(MainViewModel::class.java)
        mainViewModel.getBakersData()
        initSearch()
        observeResponse()
    }

    private fun initSearch() {
        val manager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem: SearchView = toolbar.menu.findItem(R.id.search).actionView as SearchView
        searchItem.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchItem.queryHint = "Search"
        searchItem.isIconified = false
        searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("search result", query)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                Log.d("search result", query)
                return true
            }
        })
    }

    private fun initRecyclerView(sortedBakersList: List<BakersResponseModel>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = BakersAdapter(sortedBakersList)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = adapter
    }

    private fun observeResponse() {
        mainViewModel.bakersResponseLiveData().observeForever {
            update(it.status, it.data)
        }
    }

    private fun update(status: Status, data: Any?) {
        when (status) {
            Status.Failed -> Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()


            Status.Fail -> Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()

            Status.Success -> {
                bakersList = data as List<BakersResponseModel>
                initRecyclerView(bakersList.sortedBy { it.title })
                handleProgress(false)
            }
            Status.ShowProgress, Status.HideProgress -> handleProgress(data as Boolean)
        }
    }

    private fun handleProgress(displayProgress: Boolean) {
        if (displayProgress) {
            mainViewModel.showProgress.setValue(true)
        } else {
            mainViewModel.showProgress.setValue(false)
        }
    }
}
