package fr.isen.MARQUANT.androidrestaurant.utils

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import fr.isen.MARQUANT.androidrestaurant.databinding.ViewLoaderBinding

class Loader {

    lateinit var binding: ViewLoaderBinding

    fun show(context: Context, caption: String) {
        val inflater = LayoutInflater.from(context)
        binding = ViewLoaderBinding.inflate(inflater)
        binding.loaderCaption.text = caption

        val activity = context as? Activity
        val view = activity?.window?.peekDecorView() as? ViewGroup

        activity?.runOnUiThread {
            view?.addView(binding.root)
        }
    }

    fun hide(context: Context) {
        val activity = context as? Activity
        val view = activity?.window?.peekDecorView() as? ViewGroup
        activity?.runOnUiThread {
            view?.removeView(binding.root)
        }
    }
}