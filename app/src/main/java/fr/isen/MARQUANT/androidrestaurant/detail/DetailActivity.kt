package fr.isen.MARQUANT.androidrestaurant.detail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import fr.isen.MARQUANT.androidrestaurant.R
import fr.isen.MARQUANT.androidrestaurant.basket.Basket
import fr.isen.MARQUANT.androidrestaurant.basket.BasketItem
import fr.isen.MARQUANT.androidrestaurant.category.CategoryActivity
import fr.isen.MARQUANT.androidrestaurant.BaseActivity
import fr.isen.MARQUANT.androidrestaurant.databinding.ActivityDetailBinding
import fr.isen.MARQUANT.androidrestaurant.network.Dish
import kotlin.math.max

class DetailActivity : BaseActivity() {
    companion object {
        const val DISH_EXTRA = "DISH_EXTRA"
        const val ITEMS_COUNT = "ITEM"
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
    }
    lateinit var binding: ActivityDetailBinding
    private var itemCount = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dish = intent.getSerializableExtra(DISH_EXTRA) as? Dish
        dish?.let{
            setupView(it)
        }
    }
    private fun setupView(dish: Dish){
        binding.dishTitleView.text = dish.name
        binding.ingredientTextView.text = dish.ingredients.map { it.name }?.joinToString(", ")
        binding.viewPager.adapter = PhotoAdapter(this,dish.images)
        refreshShop(dish)

        binding.less.setOnClickListener {
            itemCount = max(1,itemCount - 1)
            refreshShop(dish)
        }

        binding.more.setOnClickListener {
            itemCount += 1
            refreshShop(dish)
        }
        binding.shopButton.setOnClickListener{
            addToBasket(dish, itemCount)
        }
    }

    private fun refreshShop(dish: Dish){
        val price = itemCount * dish.prices.first().price.toFloat()
        binding.itemCount.text = itemCount.toString()
        binding.shopButton.text = "${getString(R.string.total)} $price€"
    }

    private fun addToBasket(dish: Dish, count: Int) {
        val basket = Basket.getBasket(this)
        basket.addItem(BasketItem(dish, count))
        basket.save(this)
        refreshMenu(basket)
        Snackbar.make(binding.root, getString(R.string.basket_validation), Snackbar.LENGTH_LONG).show()
    }

    private fun refreshMenu(basket: Basket){
        val itemsCount = basket.items
        val sharedPreferences = getSharedPreferences(CategoryActivity.USER_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(ITEMS_COUNT, itemCount)
        editor.apply()
        invalidateOptionsMenu()
    }
}