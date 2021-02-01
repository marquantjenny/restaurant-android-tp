package fr.isen.MARQUANT.androidrestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.MARQUANT.androidrestaurant.category.CategoryActivity
import fr.isen.MARQUANT.androidrestaurant.category.ItemType
import fr.isen.MARQUANT.androidrestaurant.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.entrees.setOnClickListener {
            startCategoryActivity(ItemType.ENTREES)
        }

        binding.plats.setOnClickListener {
            startCategoryActivity(ItemType.PLATS)
        }

        binding.deserts.setOnClickListener {
            startCategoryActivity(ItemType.DESSERTS)
        }
    }

    private fun startCategoryActivity(item: ItemType) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra(CATEGORY_NAME, item)
        startActivity(intent)
    }

    companion object {
        const val CATEGORY_NAME = "CATEGORY_NAME"
    }

}