package com.dr.samconpractical.ui

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dr.samconpractical.R
import com.dr.samconpractical.databinding.RowItemProductsBinding
import com.dr.samconpractical.room.ProductEntity

class AdapterProducts(val list: List<ProductEntity>): RecyclerView.Adapter<AdapterProducts.ProductHolder>() {

    class ProductHolder(itemView: View, val binding: RowItemProductsBinding) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val binding = RowItemProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {

        val _binding = holder.binding

        _binding.price.text = list[position].price.toString()
        _binding.actualPrice.text = "₹" + (list[position].price + 10).toString()
        _binding.actualPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        _binding.picture.load(list[position].image.ifEmpty { R.drawable.dummy_product })

        _binding.title.text = list[position].productName

    }

    override fun getItemCount() = list.size
}