package com.inxparticle.shoppinglist.di

import android.content.Context
import androidx.room.Room
import com.inxparticle.shoppinglist.data.local.ShoppingDao
import com.inxparticle.shoppinglist.data.local.ShoppingItemDatabase
import com.inxparticle.shoppinglist.data.remote.PixabayAPI
import com.inxparticle.shoppinglist.other.Constants.BASE_URL
import com.inxparticle.shoppinglist.other.Constants.DATABASE_NAME
import com.inxparticle.shoppinglist.repositories.DefaultShoppingRepository
import com.inxparticle.shoppinglist.repositories.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(context,ShoppingItemDatabase::class.java,DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao: ShoppingDao,
        api: PixabayAPI
    ) = DefaultShoppingRepository(dao, api) as ShoppingRepository

    @Provides
    @Singleton
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()

    @Provides
    @Singleton
    fun providePixabayApi():PixabayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }

}