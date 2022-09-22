package com.dr.samconpractical.api

import android.content.Context
import android.util.Log
import com.android.volley.Request.Method.GET
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.dr.samconpractical.room.MyDatabase
import com.dr.samconpractical.room.ProductEntity
import com.dr.samconpractical.utils.Cons.BASE_URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class APICall(private val mContext: Context) {

    suspend fun fetchProducts() = coroutineScope {

        val requestQueue = Volley.newRequestQueue(mContext)

        val jsonArrayRequest = JsonArrayRequest(GET, BASE_URL, null, {
            saveToRoom(it)
        }, {
            Log.e("--->", it.message.toString())
        })

        requestQueue.add(jsonArrayRequest)

    }

    private fun saveToRoom(products: JSONArray) {

        val room = MyDatabase.getDatabase(mContext)

        for (i in 0 until products.length()) {

            val productObj = products.getJSONObject(i)

            val id = productObj.optInt("Id", 0)
            val productName = productObj.optString("Name", "")
            val quantity = productObj.optInt("StockQuantity", 0)
            val price = productObj.optInt("Price", 0)
            val images = productObj.optJSONArray("ProductPictures")
            val imageUrl = if (images != null && images.length() != 0) (images[0] as JSONObject).optString("PictureUrl", "") else ""

            CoroutineScope(IO)
                .launch {
                    try {
                        room.productDao().insert(
                            ProductEntity(
                                id,
                                productName,
                                quantity,
                                imageUrl,
                                price,
                                "-----"
                            )
                        )
                    } catch (e: Exception) {
                        room.productDao().update(
                            ProductEntity(
                                id,
                                productName,
                                quantity,
                                imageUrl,
                                price,
                                "-----"
                            )
                        )
                    }
                }

        }


//        for (product in products) {
//
//            Log.v("--->", product.id.toString() + "  " + product.productAttributes)
//
//            val room = MyDatabase.getDatabase(mContext)
//
//

//
//        }


    }

}