package com.example.ecommercefoodappcompose.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FoodApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RecipeApi
