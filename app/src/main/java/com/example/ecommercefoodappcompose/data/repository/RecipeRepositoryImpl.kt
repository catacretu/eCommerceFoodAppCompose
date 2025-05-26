package com.example.ecommercefoodappcompose.data.repository

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommercefoodappcompose.data.local.dao.FoodDAO
import com.example.ecommercefoodappcompose.data.local.dao.RecipeDAO
import com.example.ecommercefoodappcompose.data.local.model.FoodItem
import com.example.ecommercefoodappcompose.data.local.model.RecipeItem
import com.example.ecommercefoodappcompose.data.remote.RecipeService
import com.example.ecommercefoodappcompose.data.remote.entity.ChatRequest
import com.example.ecommercefoodappcompose.data.remote.entity.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeService: RecipeService,
    private val recipeDao: RecipeDAO,
    private val foodDAO: FoodDAO
) : RecipeRepository {

    val allFoodItems: LiveData<List<FoodItem>> = foodDAO.getAllFoodItems()

    private val _recipes = MutableLiveData<List<RecipeItem>>()
    val recipes: LiveData<List<RecipeItem>> = _recipes

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading
    var isFavourite = mutableStateOf<Boolean?>(null)
        private set

    override suspend fun fetchRecipes(query: String, additionalSearch: Boolean) {
        val noRecipes = 3
        if (query.isBlank() && _recipes.value!!.isNotEmpty()) {
            _recipes.postValue(emptyList())
            return
        }
        _isLoading.postValue(true)

        val searchMsg =
            if (additionalSearch) {
                "Generate another $noRecipes recipes different from " +
                    "before recipes for $query."
            } else {
                "Generate $noRecipes recipes for $query."
            }
        val request = ChatRequest(
            messages = listOf(
                Message(
                    "system",
                    "You are a helpful assistant that returns recipes in JSON format and" +
                        " each recipe must include a real image URL that exists on the internet.."
                ),
                Message(
                    "user",
                    "$searchMsg Format response as a JSON array with objects " +
                        "containing title, time, imageUrl, ingredients (list), and instructions."
                )
            ),
            model = "gpt-3.5-turbo",
            temperature = 0.7
        )

        try {
            val response = recipeService.getRecipes(request)
            val jsonContent = response.choices.firstOrNull()?.message?.content ?: ""
            if (jsonContent.isBlank()) {
                _recipes.postValue(emptyList())
                return
            }
            val cleanedJson = jsonContent.trim().removeSurrounding("```json", "```").trim()
            val recipesArray = JSONArray(cleanedJson)

            val recipeList = (0 until recipesArray.length()).map {
                val obj = recipesArray.getJSONObject(it)
                RecipeItem(
                    title = obj.getString("title"),
                    time = obj.getString("time"),
                    imageUrl = obj.getString("imageUrl"),
                    ingredients = obj.getJSONArray("ingredients").let { arr ->
                        List(arr.length()) { index -> arr.getString(index) }
                    },
                    instructions = obj.getString("instructions")
                )
            }
            _recipes.postValue(recipeList)
        } catch (e: Exception) {
            _recipes.postValue(emptyList())
        } finally {
            _isLoading.postValue(false)
        }
    }

    suspend fun toggleFavorite(recipe: RecipeItem) {
        val updatedRecipe = recipe.copy(isFavourite = !recipe.isFavourite)

        if (recipe.isFavourite) {
            deleteRecipeById(recipe.id)
            isFavourite.value = false
        } else {
            insertRecipe(updatedRecipe)
            isFavourite.value = true
        }
        _recipes.value = _recipes.value?.map { if (it.title == recipe.title) updatedRecipe else it }
            ?: emptyList()
    }

    private suspend fun insertRecipe(recipe: RecipeItem) {
        withContext(Dispatchers.IO) {
            recipeDao.insertRecipe(recipe)
        }
    }

    fun getAllRecipes(): LiveData<List<RecipeItem>> {
        return recipeDao.getAllRecipes()
    }

    fun getDefaultRecipes(): LiveData<List<RecipeItem>> {
        return recipeDao.getDefaultRecipes()
    }

    fun clearAllRecipes() {
        _recipes.postValue(emptyList())
    }

    private suspend fun deleteRecipeById(recipeId: Int) {
        withContext(Dispatchers.IO) {
            recipeDao.deleteRecipeById(recipeId)
        }
    }

    suspend fun isRecipeFavourite(recipeId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            recipeDao.isRecipeFavourite(recipeId)
        }
    }
}
