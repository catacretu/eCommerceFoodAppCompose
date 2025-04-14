package com.example.ecommercefoodappcompose.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.ecommercefoodappcompose.data.local.dao.FoodDAO
import com.example.ecommercefoodappcompose.data.local.model.FoodItem
import com.example.ecommercefoodappcompose.data.remote.FoodService
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val foodService: FoodService,
    private val foodDAO: FoodDAO,
    private val context: Context
) : FoodRepository {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    override suspend fun getFoodItemById(foodItemId: Int): FoodItem {
        return foodDAO.getFoodItemById(foodItemId)
    }

    override fun getAllFoodItems(): LiveData<List<FoodItem>> = liveData(Dispatchers.IO) {
        _isLoading.postValue(true)
        if (isNetworkAvailable(context)) {
            try {
                val response = foodService.getAllFoodItems()
                if (response.isSuccessful) {
                    response.body()?.let { items ->
                        foodDAO.deleteAllFoodItems()
                        foodDAO.saveFoodList(items)
                        emitSource(foodDAO.getAllFoodItems())
                        _isLoading.postValue(false)
                    } ?: run {
                        emit(emptyList())
                        _isLoading.postValue(false)
                    }
                } else {
                    emitSource(foodDAO.getAllFoodItems())
                    _isLoading.postValue(false)
                }
            } catch (e: Exception) {
                emitSource(foodDAO.getAllFoodItems())
                _isLoading.postValue(false)
            }
        } else {
            emitSource(foodDAO.getAllFoodItems())
            _isLoading.postValue(false)
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}
