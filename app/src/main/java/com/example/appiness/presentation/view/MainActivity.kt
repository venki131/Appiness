package com.example.appiness.presentation.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appiness.R
import com.example.appiness.core.application.di.ViewModelProviderFactory
import com.example.appiness.core.others.Status
import com.example.appiness.data.datasource.RemoteApi
import com.example.appiness.data.model.BakersResponseModel
import com.example.appiness.databinding.ActivityMainBinding
import com.example.appiness.presentation.view.adapter.BakersAdapter
import com.example.appiness.presentation.viewmodels.MainViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_layout.view.*
import java.util.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {
    private lateinit var adapter: BakersAdapter
    private lateinit var bakersList: List<BakersResponseModel>
    private lateinit var activityBinding: ActivityMainBinding

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var remoteApi: RemoteApi

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        activityBinding.lifecycleOwner = this
        mainViewModel = ViewModelProvider(this, providerFactory).get(MainViewModel::class.java)
        activityBinding.viewModel = mainViewModel
        mainViewModel.getBakersData()
        handleProgress(true)
        initSearch()
        initRecyclerView(listOf())
        observeResponse()
        activityBinding.errorLayout.txtTryAgain.setOnClickListener { mainViewModel.getBakersData() }
    }

    private fun initSearch() {
        adapter = BakersAdapter(listOf())
        val manager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem: SearchView = toolbar.menu.findItem(R.id.search).actionView as SearchView
        searchItem.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchItem.queryHint = "Search"
        searchItem.isIconified = false
        searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchItem.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                adapter.updateList(bakersList.filter {
                    it.title.toUpperCase(Locale.ROOT).contains(query!!.toUpperCase(Locale.ROOT))
                })
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
            Status.Failed -> {
                handleProgress(false)
                activityBinding.errorLayout.visibility = View.VISIBLE
                toolbar.menu.findItem(R.id.search).isEnabled = false
            }

            Status.Fail -> {
                handleProgress(false)
                toolbar.menu.findItem(R.id.search).isEnabled = false
                activityBinding.errorLayout.visibility = View.VISIBLE
            }

            Status.Success -> {
                bakersList = data as List<BakersResponseModel>
                mainViewModel.showErrorLayout.value = false
                initRecyclerView(bakersList.sortedBy { it.title })
            }
            Status.ShowProgress, Status.HideProgress -> handleProgress(data as Boolean)
        }
    }

    private fun handleProgress(displayProgress: Boolean) {
        mainViewModel.showProgress.value = displayProgress
    }
}
