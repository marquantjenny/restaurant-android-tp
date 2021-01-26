package fr.isen.MARQUANT.androidrestaurant.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.MARQUANT.androidrestaurant.CategoryAdapter
import fr.isen.MARQUANT.androidrestaurant.HomeActivity
import fr.isen.MARQUANT.androidrestaurant.R
import fr.isen.MARQUANT.androidrestaurant.databinding.ActivityCategoryBinding
import fr.isen.MARQUANT.androidrestaurant.network.MenuResult
import fr.isen.MARQUANT.androidrestaurant.network.NetworkConstant
import org.json.JSONObject

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
        binding.categoryTitle.text = getCategoryTitle(selectedItem)
        //loadList()

        makeRequest()

        Log.d( "lifecycle", "onCreate")
    }

    private fun loadList(){
        val entries= listOf<String>("title1","title2","title3")
        val adapter = CategoryAdapter(entries)
        binding.recycleView.layoutManager = LinearLayoutManager(this)
        binding.recycleView.adapter = adapter
    }

    private fun makeRequest(){
        val queue = Volley.newRequestQueue(this)
        val url = NetworkConstant.BASE_URL + NetworkConstant.PATH_MENU

        val jsonData = JSONObject()
        jsonData.put(NetworkConstant.ID_SHOP, 1)

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonData,
            {response ->
                val menu = GsonBuilder().create().fromJson(response.toString(), MenuResult::class.java)
                menu.data.forEach {
                    Log.d("request", it.name)
                }
            },
            { error ->
                error.message?.let{
                    Log.d("request", it)
                } ?: run {
                    Log.d("request", error.toString())
                }
            }
        )



        /*
        val request = StringRequest(Request.Method.GET,
            url,
            Response.Listener { response ->
                Log.d("Request", response)
            },
            Response.ErrorListener { error ->
                Log.d("Request", error.localizedMessage)
            }
        )
        */
        queue.add(request)
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
}