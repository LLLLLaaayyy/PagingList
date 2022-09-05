package com.lay.paginglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lay.paging.PagingList
import com.lay.paging.business.ListMode
import com.lay.paging.callback.IPagingCallback
import com.lay.paging.model.DatePagingModel
import com.lay.paging.model.SimplePagingModel

class MainActivity : AppCompatActivity() {

    private lateinit var rvList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = mutableListOf<String>()
        for (index in 0..10) {
            list.add("${index + 1}")
        }

        rvList = findViewById(R.id.rv_list)
        val pagingList = PagingList<String>()
        val adapter = MyAdapter()
        pagingList.bindView(this, rvList, adapter, ListMode.SIMPLE)
        pagingList.setScrollListener(object : IPagingCallback {
            override fun scrollEnd() {
                Log.e("MainActivity", "scrollEnd--")
            }

            override fun scrollRefresh() {
                Log.e("MainActivity", "scrollRefresh--")
                pagingList.bindData(list.map {
                    SimplePagingModel<String>(
                        "-1", it
                    )
                })
            }

            override fun scrolling() {
                Log.e("MainActivity", "scrolling--")
            }

        })
        pagingList.bindData(list.map {
            SimplePagingModel<String>(
                "1", it
            )
        })
    }
}