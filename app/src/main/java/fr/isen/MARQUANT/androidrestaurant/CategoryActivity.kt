package fr.isen.MARQUANT.androidrestaurant

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import fr.isen.MARQUANT.androidrestaurant.databinding.ActivityCategoryBinding
import fr.isen.MARQUANT.androidrestaurant.databinding.ActivityHomeBinding

enum class ItemType {
    ENTREES, PLATS, DESSERTS
}

class CategoryActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedItem = intent.getSerializableExtra(HomeActivity.CATEGORY_NAME) as? ItemType
        dislayTitle(selectedItem)

        Log.d( "lifecycle", "onCreate")
    }

    private fun dislayTitle(item: ItemType?){ //?veut dire cette classe ou nul
        when(item) {
            ItemType.ENTREES -> getString(R.string.entrees)
            ItemType.PLATS -> getString(R.string.plats)
            ItemType.DESSERTS -> getString(R.string.desserts)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d( "lifecycle", "onResume")
    }

    override fun onRestart() {
        super.onResume()
        Log.d( "lifecycle", "onRestart")
    }

    override fun onDestroy() {
        Log.d( "lifecycle", "onDestroy")
        super.onDestroy()
    }

}