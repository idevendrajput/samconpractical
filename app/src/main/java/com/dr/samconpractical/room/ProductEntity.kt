package com.dr.samconpractical.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "productId")
    val productId: Int,

    @ColumnInfo(name = "productName")
    val productName: String,

    @ColumnInfo(name = "quantity")
    val quantity: Int,

    @ColumnInfo(name = "productImage")
    val image: String,

    @ColumnInfo(name = "price")
    val price: Int,

    @ColumnInfo(name = "deliveryType")
    val deliveryType: String
)