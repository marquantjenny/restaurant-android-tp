package fr.isen.MARQUANT.androidrestaurant.network

import com.google.gson.annotations.SerializedName

class Category (@SerializedName("name_fr") val name:String, val items:List<Dish>){}