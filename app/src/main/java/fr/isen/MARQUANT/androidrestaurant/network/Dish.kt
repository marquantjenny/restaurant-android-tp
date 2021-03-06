package fr.isen.MARQUANT.androidrestaurant.network

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Dish(
        @SerializedName("name_fr") val name:String,
        val images:List<String>,
        val ingredients:List<Ingredient>,
        val prices:List<Price>
): Serializable {
        fun getThumbnaiUrl(): String? {
                return if(images.isNotEmpty() && images[0].isNotEmpty()){
                        images[0]
                }else {
                        null
                }
        }
}