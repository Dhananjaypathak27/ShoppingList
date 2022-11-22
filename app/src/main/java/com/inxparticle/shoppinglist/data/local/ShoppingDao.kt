package com.inxparticle.shoppinglist.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)
    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    /**
     * this is not a suspend function becasuse this returns livedata this will not work because room is asynchronous
     * by default so whenever want to return live data with room no need to make it suspend function
     * */
    @Query("SELECT * FROM shopping_items")
    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    @Query("SELECT SUM(price * amount) FROM shopping_items")
    fun observeTotalPrice(): LiveData<Float>

}