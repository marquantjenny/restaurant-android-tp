package fr.isen.MARQUANT.androidrestaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.MARQUANT.androidrestaurant.databinding.DishCellBinding
import fr.isen.MARQUANT.androidrestaurant.network.Dish

class CategoryAdapter (private val titles: List<Dish>,
                       private val entryClickListener: (Dish) -> Unit)
    :RecyclerView.Adapter<CategoryAdapter.DishViewHolder>(){
    class DishViewHolder (dishesBinding: DishCellBinding): RecyclerView.ViewHolder(dishesBinding.root){
        val titleView: TextView = dishesBinding.dishesTitle
        val priceView: TextView = dishesBinding.dishPrice
        val imageView: ImageView = dishesBinding.dishImageView
        val layout = dishesBinding.root

        fun bind(dish: Dish){
            titleView.text = dish.name
            priceView.text = "${dish.prices.first().price} €"
            Picasso.get()
                .load(dish.getThumbnaiUrl())
                .placeholder(R.drawable.zer)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        return DishViewHolder(DishCellBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return titles.count()
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val dish = titles[position]
        holder.layout.setOnClickListener {
            entryClickListener.invoke(dish)
        }
        holder.bind(dish)
    }
}