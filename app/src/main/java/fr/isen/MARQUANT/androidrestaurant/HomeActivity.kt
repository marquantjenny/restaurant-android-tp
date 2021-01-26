package fr.isen.MARQUANT.androidrestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.MARQUANT.androidrestaurant.category.CategoryActivity
import fr.isen.MARQUANT.androidrestaurant.category.ItemType
import fr.isen.MARQUANT.androidrestaurant.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.entrees.setOnClickListener {
            statCategoryActivity(ItemType.ENTREES)
        }

        binding.plats.setOnClickListener {
            statCategoryActivity(ItemType.PLATS)
        }

        binding.deserts.setOnClickListener {
            statCategoryActivity(ItemType.DESSERTS)
        }
    }

    private fun statCategoryActivity(item: ItemType) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra(CATEGORY_NAME, item)
        startActivity(intent)
    }

    companion object {
        const val CATEGORY_NAME = "CATEGORY_NAME"
    }

}