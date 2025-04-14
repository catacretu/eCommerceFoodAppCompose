package com.example.ecommercefoodappcompose.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.ecommercefoodappcompose.BASE_URL
import com.example.ecommercefoodappcompose.data.database.FoodDatabase
import com.example.ecommercefoodappcompose.data.local.dao.FoodDAO
import com.example.ecommercefoodappcompose.data.remote.FoodService
import com.example.ecommercefoodappcompose.data.repository.FoodRepository
import com.example.ecommercefoodappcompose.data.repository.FoodRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient().newBuilder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideFoodService(retrofit: Retrofit): FoodService {
        return retrofit.create(FoodService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FoodDatabase {
        return Room.databaseBuilder(
            context,
            FoodDatabase::class.java,
            "food_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideFoodDao(database: FoodDatabase): FoodDAO {
        return database.foodDao()
    }

    @Provides
    @Singleton
    fun provideFoodRepository(
        foodService: FoodService,
        foodDao: FoodDAO,
        context: Context
    ): FoodRepository {
        return FoodRepositoryImpl(foodService, foodDao, context)
    }
}
