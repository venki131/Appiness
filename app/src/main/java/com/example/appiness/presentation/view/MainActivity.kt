package com.example.appiness.presentation.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import android.widget.Toolbar
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
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {
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
        observeResponse()
    }

    private fun initRecyclerView(sortedBakersList: List<BakersResponseModel>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = BakersAdapter(sortedBakersList)
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
                //launch recycler adapter
                val bakersList: List<BakersResponseModel> = data as List<BakersResponseModel>
                initRecyclerView(bakersList.sortedBy { it.title })
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val manager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem: SearchView = menu!!.findItem(R.id.search).actionView as SearchView
        searchItem.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onQueryTextChange(query: String?): Boolean {
                Log.d("search result", query)
                return true
            }
        })

        return super.onCreateOptionsMenu(menu);
    }
}
