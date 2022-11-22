package com.inxparticle.shoppinglist.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inxparticle.shoppinglist.data.local.ShoppingItem
import com.inxparticle.shoppinglist.data.remote.response.ImageResponse
import com.inxparticle.shoppinglist.other.Resource
import com.inxparticle.shoppinglist.repositories.ShoppingRepository
import retrofit2.Response

class FakeShoppingRepository : ShoppingRepository{
    private val shoppingItems = mutableListOf<ShoppingItem>()

    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)

    private val observableTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value:Boolean){
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData(){
        observableShoppingItems.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice(): Float {
        return shoppingItems.sumOf { it.price.toDouble() }.toFloat()
    }


    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
        return observableShoppingItems
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observableTotalPrice
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return if(shouldReturnNetworkError){
            Resource.error("Error",null)
        }
        else{
            Resource.success(ImageResponse(listOf(),0,0))
        }
    }
}