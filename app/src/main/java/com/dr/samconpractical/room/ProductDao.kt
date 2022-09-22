package com.dr.samconpractical.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {

    @Insert
    suspend fun insert(entity: ProductEntity)

    @Update
    suspend fun update(entity: ProductEntity)

    @Delete
    fun delete(entity: ProductEntity)

    @Query("SELECT *From products")
    fun getProducts(): List<ProductEntity>

    @Query("SELECT *From products")
    fun getProductsLive(): LiveData<List<ProductEntity>>

}