package com.dr.samconpractical

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.dr.samconpractical.api.APICall
import com.dr.samconpractical.databinding.ActivityMainBinding
import com.dr.samconpractical.room.MyDatabase
import com.dr.samconpractical.room.ProductEntity
import com.dr.samconpractical.ui.AdapterProducts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var room: MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        room = MyDatabase.getDatabase(this)

        CoroutineScope(IO)
            .launch { APICall(this@MainActivity).fetchProducts() }

        Handler(Looper.getMainLooper())
            .post {
                setProducts()
            }

    }

    private fun setProducts() {

        room.productDao().getProductsLive().observe(this
        ) {

            binding.resultCount.text =  "${it.size} Products Found"

            if (it.isEmpty()) {
                binding.noData.visibility = View.VISIBLE
            } else {
                binding.noData.visibility = View.GONE
                binding.pb.visibility = View.GONE
            }

            binding.rv.adapter = AdapterProducts(it)

        }

    }

}