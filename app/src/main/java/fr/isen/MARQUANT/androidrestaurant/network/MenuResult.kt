package fr.isen.MARQUANT.androidrestaurant.network

import com.google.gson.annotations.SerializedName

class MenuResult (val data: List<Category>) {}
class Category (@SerializedName("name_fr") val name:String, val items:List<Item>){}
class Item(
    @SerializedName("name_fr") val name:String,
    val images:List<String>,
    val ingredients:List<Ingredient>,
    val prices:List<Price>){}
class Ingredient(@SerializedName("name_fr") val name:String){}
class Price(val price:String){}