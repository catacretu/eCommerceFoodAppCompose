package com.example.ecommercefoodappcompose.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.ecommercefoodappcompose.BASE_URL
import com.example.ecommercefoodappcompose.OPEN_URL
import com.example.ecommercefoodappcompose.data.database.FoodDatabase
import com.example.ecommercefoodappcompose.data.local.dao.FoodDAO
import com.example.ecommercefoodappcompose.data.local.dao.RecipeDAO
import com.example.ecommercefoodappcompose.data.remote.FoodService
import com.example.ecommercefoodappcompose.data.remote.RecipeService
import com.example.ecommercefoodappcompose.data.repository.FoodRepository
import com.example.ecommercefoodappcompose.data.repository.FoodRepositoryImpl
import com.example.ecommercefoodappcompose.data.repository.RecipeRepository
import com.example.ecommercefoodappcompose.data.repository.RecipeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @FoodApi
    fun provideFoodRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient().newBuilder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @RecipeApi
    fun provideRecipeRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(OPEN_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideFoodService(@FoodApi retrofit: Retrofit): FoodService {
        return retrofit.create(FoodService::class.java)
    }

    @Provides
    @Singleton
    fun provideRecipeService(@RecipeApi retrofit: Retrofit): RecipeService {
        return retrofit.create(RecipeService::class.java)
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
    fun provideRecipeDao(database: FoodDatabase): RecipeDAO {
        return database.recipeDao()
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

    @Provides
    @Singleton
    fun provideRecipeRepository(
        recipeService: RecipeService,
        recipeDao: RecipeDAO,
        foodDao: FoodDAO
    ): RecipeRepository {
        return RecipeRepositoryImpl(recipeService, recipeDao, foodDao)
    }
}
