package com.inxparticle.shoppinglist.repositories

import androidx.lifecycle.LiveData
import com.inxparticle.shoppinglist.data.local.ShoppingItem
import com.inxparticle.shoppinglist.data.remote.response.ImageResponse
import com.inxparticle.shoppinglist.other.Resource
import retrofit2.Response

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItem() : LiveData<List<ShoppingItem>>

    fun observeTotalPrice() : LiveData<Float>

    suspend fun searchForImage(imageQuery: String) : Resource<ImageResponse>
}