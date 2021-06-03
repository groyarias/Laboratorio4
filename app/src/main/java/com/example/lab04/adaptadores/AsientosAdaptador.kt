package com.example.lab04.adaptadores

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.lab04.R

class AsientosAdaptador(var context: Context, var matrizBooleana:Array<BooleanArray>, var asientos:ArrayList<String>, var columnas:Int): BaseAdapter(){

    override fun getItem(position: Int): Any {
        return asientos[position]
    }

    override fun getCount(): Int {
        return asientos.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View = View.inflate(context, R.layout.item_asiento,null)
        var tvAsiento = view.findViewById<TextView>(R.id.tv_asiento)

        var posicion = position

        var asiento:String = asientos[posicion]
        tvAsiento.text = asiento

        var fila = posicion/columnas
        var columna = posicion%columnas

        if(matrizBooleana[fila][columna]){
            val card = view?.findViewById<CardView>(R.id.cv_asiento)
            card.setBackgroundColor(Color.RED)
        }
        return view
    }

}