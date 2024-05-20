package com.example.mybaseprojectwithhilt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mybaseprojectwithhilt.R
import com.example.mybaseprojectwithhilt.data.model.Country
import com.example.mybaseprojectwithhilt.util.getProgressDrawable
import com.example.mybaseprojectwithhilt.util.loadImage

class CountryListAdapter (var countries:MutableList<Country>): RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    fun updateCountries(newCouuntries:List<Country>){
        countries.clear()
        countries.addAll(newCouuntries)
        notifyDataSetChanged()
    }

    class CountryViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val countryName=view.findViewById<TextView>(R.id.name)
        private val countryCapital=view.findViewById<TextView>(R.id.capital)
        private val imageView=view.findViewById<ImageView>(R.id.imageView)
        private val progressDrawable= getProgressDrawable(view.context)
        fun bind(country: Country){
            countryCapital.text=country.capital
            countryName.text=country.countryName
            imageView.loadImage(country.flagUrl,progressDrawable)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_country,parent,false))
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount()=countries.size

}