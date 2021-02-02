package fr.isen.MARQUANT.androidrestaurant.category

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import fr.isen.MARQUANT.androidrestaurant.utils.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.MARQUANT.androidrestaurant.BaseActivity
import fr.isen.MARQUANT.androidrestaurant.CategoryAdapter
import fr.isen.MARQUANT.androidrestaurant.detail.DetailActivity
import fr.isen.MARQUANT.androidrestaurant.HomeActivity
import fr.isen.MARQUANT.androidrestaurant.R
import fr.isen.MARQUANT.androidrestaurant.databinding.ActivityCategoryBinding
import fr.isen.MARQUANT.androidrestaurant.network.Dish
import fr.isen.MARQUANT.androidrestaurant.network.MenuResult
import fr.isen.MARQUANT.androidrestaurant.network.NetworkConstant
import org.json.JSONObject

enum class ItemType {
    ENTREES, PLATS, DESSERTS;

    companion object {
        fun categoryTitle(item: ItemType?) : String {
            return when(item) {
                ENTREES -> "Entrées"
                PLATS -> "Plats"
                DESSERTS -> "Desserts"
                else -> ""
            }
        }
    }
}

class CategoryActivity : BaseActivity() {
    private  lateinit var bindind: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindind = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(bindind.root)

        val selectedItem = intent.getSerializableExtra(HomeActivity.CATEGORY_NAME) as? ItemType

        bindind.swipeLayout.setOnRefreshListener {
            resetCache()
            makeRequest(selectedItem)
        }

        bindind.categoryTitle.text = getCategoryTitle(selectedItem)

        loadList(listOf<Dish>())
        makeRequest(selectedItem)
        Log.d( "lifecycle", "onCreate")
    }

    private fun makeRequest(selectedItem: ItemType?){
        resultFromCache()?.let {
            //la requete est en cache
            parseResult(it,selectedItem)
        } ?: run {
            val loader = Loader()
            loader.show(this, "récupération du menu")

            val queue = Volley.newRequestQueue(this)
            val url = NetworkConstant.BASE_URL + NetworkConstant.PATH_MENU

            val jsonData = JSONObject()
            jsonData.put(NetworkConstant.ID_SHOP, "1")

            val request = JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonData,
                    {response ->
                        loader.hide(this)
                        bindind.swipeLayout.isRefreshing = false
                        cacheResult(response.toString())
                        parseResult(response.toString(),selectedItem)

                    },
                    { error ->
                        loader.hide(this)
                        bindind.swipeLayout.isRefreshing = false
                        error.message?.let{
                            Log.d("request", it)
                        } ?: run {
                            Log.d("request", error.toString())
                        }
                    }
            )
            queue.add(request)
        }
    }

    private fun cacheResult(response: String){
        val sharedPreferences = getSharedPreferences(USER_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(REQUEST_CACHE,response)
        editor.apply()
    }

    private fun resetCache() {
        val sharedPreferences = getSharedPreferences(USER_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(REQUEST_CACHE)
        editor.apply()
    }

    private fun resultFromCache(): String? {
        val sharedPreferences = getSharedPreferences(USER_PREFERENCE_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(REQUEST_CACHE, null)
    }

    private fun parseResult(response: String, selectedItem: ItemType?){
        val menu = GsonBuilder().create().fromJson(response.toString(), MenuResult::class.java)
        val items = menu.data.firstOrNull { it.name == ItemType.categoryTitle(selectedItem) }
        loadList(items?.items)
    }

    private fun loadList(dishes: List<Dish>?){
        dishes?.let {
            val adapter = CategoryAdapter(it) { dish ->
                val intent = Intent(this, DetailActivity:: class.java)
                intent.putExtra(DetailActivity.DISH_EXTRA, dish)
                startActivity(intent)
            }
            bindind.recycleView.layoutManager = LinearLayoutManager(this)
            bindind.recycleView.adapter = adapter
        }
    }

    private fun getCategoryTitle(item: ItemType?): String {
        return when(item) {
            ItemType.ENTREES -> getString(R.string.entrees)
            ItemType.PLATS -> getString(R.string.plats)
            ItemType.DESSERTS -> getString(R.string.desserts)
            else -> ""
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d( "lifecycle", "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d( "lifecycle", "onRestart")
    }

    override fun onDestroy() {
        Log.d( "lifecycle", "onDestroy")
        super.onDestroy()
    }
    companion object{
        const val USER_PREFERENCE_NAME = "USER_PREFERENCE_NAME"
        const val REQUEST_CACHE = "REQUEST_CACHE"
    }
}