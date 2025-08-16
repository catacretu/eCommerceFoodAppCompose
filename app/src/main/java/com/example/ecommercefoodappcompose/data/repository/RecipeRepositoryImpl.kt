package com.example.ecommercefoodappcompose.data.repository

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommercefoodappcompose.data.local.dao.FoodDAO
import com.example.ecommercefoodappcompose.data.local.dao.RecipeDAO
import com.example.ecommercefoodappcompose.data.local.model.FoodItem
import com.example.ecommercefoodappcompose.data.local.model.RecipeItem
import com.example.ecommercefoodappcompose.data.remote.entity.ChatRequest
import com.example.ecommercefoodappcompose.data.remote.entity.Message
import com.example.ecommercefoodappcompose.data.remote.service.RecipeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeService: RecipeService,
    private val recipeDao: RecipeDAO,
    private val foodDAO: FoodDAO
) : RecipeRepository {

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
        val myIngredients = withContext(Dispatchers.IO) { foodDAO.getAllFoodItemNames() }
        val ingredientsList = myIngredients.joinToString(", ") { "\"$it\"" }

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
                    "You are a culinary assistant specialized in generating " +
                        "practical and well-known recipes. " +
                        "Avoid unrealistic or nonsensical ingredient combinations. " +
                        "Always return responses in valid JSON format, and make sure each " +
                        "recipe includes a real image URL that exists online."
                ),
                Message(
                    "user",
                    "$searchMsg Format the response as a JSON array. Each object" +
                        " should include: `title` (string), `time` (string), `imageUrl`" +
                        " (string), `ingredients` (list of strings),`instructions` (string)," +
                        " and a `matchedIngredients` field (list of strings) that contains " +
                        "items from the following pantry list " +
                        "if they appear in the ingredients: [$ingredientsList]. " +
                        "For ordered lists in `instructions`, separate steps using `\n`."
                )
            ),
            model = "gpt-3.5-turbo",
            temperature = 0.5
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
                val matchedIngredients = obj.getJSONArray("matchedIngredients").let { arr ->
                    List(arr.length()) { index -> arr.getString(index) }
                }
                RecipeItem(
                    title = obj.getString("title"),
                    time = obj.getString("time"),
                    imageUrl = obj.getString("imageUrl"),
                    ingredients = obj.getJSONArray("ingredients").let { arr ->
                        List(arr.length()) { index -> arr.getString(index) }
                    },
                    matchedIngredients = matchedIngredients,
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

    fun getAllFoodItems(): LiveData<List<FoodItem>> {
        return foodDAO.getAllFoodItems()
    }

    suspend fun getFoodItemsByNames(names: List<String>): List<FoodItem> {
        return withContext(Dispatchers.IO) {
            foodDAO.getFoodItemsByNames(names)
        }
    }

    fun getFavouriteRecipes(): LiveData<List<RecipeItem>> {
        return recipeDao.getFavouriteRecipes()
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
