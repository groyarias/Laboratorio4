package com.example.lab04.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab04.R
import com.example.lab04.adaptadores.VuelosAdaptador
import com.example.lab04.logica.Modelo
import com.example.lab04.logica.Vuelo
import com.example.swiperecyclerview.utils.SwipeToBuyCallback
import kotlinx.android.synthetic.main.activity_list_vuelos.*
import java.util.*

class ListVuelosActivity : AppCompatActivity() {

    private var listaVuelos: ArrayList<Vuelo> = ArrayList()
    private var listaVuelosMostrar:ArrayList<Vuelo> = ArrayList()

    private var vuelosAdaptador:VuelosAdaptador? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_vuelos)

        supportActionBar!!.setTitle("Listado de vuelos")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        listaVuelos = Modelo.obtenerVuelos()
        listaVuelosMostrar.addAll(listaVuelos)

        if (listaVuelos.size > 0) {
            rv_vuelos_list.visibility = View.VISIBLE
            tv_sin_vuelos_disponibles.visibility = View.GONE
            configurarVuelosRecyclerView(listaVuelos)
        } else {
            rv_vuelos_list.visibility = View.GONE
            tv_sin_vuelos_disponibles.visibility = View.VISIBLE
        }
    }


    private fun configurarVuelosRecyclerView(listaVuelos: ArrayList<Vuelo>) {
        rv_vuelos_list.layoutManager = LinearLayoutManager(this)
        rv_vuelos_list.setHasFixedSize(true)

        vuelosAdaptador = VuelosAdaptador(this, listaVuelos)
        rv_vuelos_list.adapter = vuelosAdaptador

        vuelosAdaptador!!.setOnClickListener(object : VuelosAdaptador.OnClickListener {
            override fun onClick(position: Int, vuelo: Vuelo) {

                Toast.makeText(this@ListVuelosActivity, "${vuelo.identificador}",Toast.LENGTH_SHORT).show()

            }
        })

        val buySwipeHandler = object : SwipeToBuyCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = rv_vuelos_list.adapter as VuelosAdaptador
                adapter.notifyBuyItem(
                    this@ListVuelosActivity,
                    viewHolder.adapterPosition,
                    BUY_FLIGHT_ACTIVITY_REQUEST_CODE
                )
            }
        }
        val buyItemTouchHelper = ItemTouchHelper(buySwipeHandler)
        buyItemTouchHelper.attachToRecyclerView(rv_vuelos_list)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val menuItem = menu!!.findItem(R.id.search)

        if(menuItem != null){
            val searchView = menuItem.actionView as SearchView
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText!!.isNotEmpty()){

                        listaVuelosMostrar.clear()
                        val search = newText.lowercase(Locale.getDefault())
                        listaVuelos.forEach {
                            var esOrigen:Boolean = it.origen.lowercase(Locale.getDefault()).contains(search)
                            var esDestino:Boolean = it.destino.lowercase(Locale.getDefault()).contains(search)
                            if (esOrigen || esDestino){
                                listaVuelosMostrar.add(it)
                            }
                        }
                        configurarVuelosRecyclerView(listaVuelosMostrar!!)
                        vuelosAdaptador!!.notifyDataSetChanged()

                    }else{
                        listaVuelosMostrar.clear()
                        listaVuelosMostrar.addAll(listaVuelos)
                        configurarVuelosRecyclerView(listaVuelosMostrar)
                        vuelosAdaptador!!.notifyDataSetChanged()
                    }
                    return true
                }

            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object{
        private const val BUY_FLIGHT_ACTIVITY_REQUEST_CODE = 1
        internal const val COMPRAR_VUELO = "comprar_vuelo"
    }

}