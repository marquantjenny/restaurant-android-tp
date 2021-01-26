package fr.isen.MARQUANT.androidrestaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.MARQUANT.androidrestaurant.databinding.DishCellBinding

class CategoryAdapter (private val titles: List<String>):RecyclerView.Adapter<CategoryAdapter.DishViewHolder>(){
    class DishViewHolder (binding: DishCellBinding): RecyclerView.ViewHolder(binding.root){
        val titleView: TextView= binding.dishTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        return DishViewHolder(DishCellBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return titles.count()
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        holder.titleView.text = titles[position]
    }
}