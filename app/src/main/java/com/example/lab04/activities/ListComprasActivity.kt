package com.example.lab04.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.example.lab04.logica.Usuario
import com.example.lab04.logica.Vuelo
import com.example.swiperecyclerview.utils.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.activity_list_compras.*
import java.util.*

class ListComprasActivity : AppCompatActivity() {

    private var usuarioLogueado:Usuario? = null

    private var listaVuelosComprados: ArrayList<Vuelo> = ArrayList()
    private var listaVuelosMostrar:ArrayList<Vuelo> = ArrayList()

    private var vuelosAdaptador:VuelosAdaptador? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_compras)

        supportActionBar!!.setTitle("Vuelos comprados")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        var intent = intent
        listaVuelosComprados = intent.getSerializableExtra(COMPRAS_USUARIO)  as ArrayList<Vuelo>
        usuarioLogueado = intent.getSerializableExtra(USUARIO_LOGUEADO) as Usuario


        //Toast.makeText(this,"Vuelos comprados: ${listaVuelosComprados.size}",Toast.LENGTH_SHORT).show()

        if (listaVuelosComprados.size > 0) {
            rv_compras_list.visibility = View.VISIBLE
            tv_sin_vuelos_comprados.visibility = View.GONE
            configurarVuelosCompradosRecyclerView(listaVuelosComprados)
        } else {
            rv_compras_list.visibility = View.GONE
            tv_sin_vuelos_comprados.visibility = View.VISIBLE
        }
    }

    private fun configurarVuelosCompradosRecyclerView(listaVuelos: ArrayList<Vuelo>) {
        rv_compras_list.layoutManager = LinearLayoutManager(this)
        rv_compras_list.setHasFixedSize(true)

        vuelosAdaptador = VuelosAdaptador(this, listaVuelos)
        rv_compras_list.adapter = vuelosAdaptador

        vuelosAdaptador!!.setOnClickListener(object : VuelosAdaptador.OnClickListener {
            override fun onClick(position: Int, vuelo: Vuelo) {

                Toast.makeText(this@ListComprasActivity, "${vuelo.identificador}",Toast.LENGTH_SHORT).show()

            }
        })

        val deleteSwipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = rv_compras_list.adapter as VuelosAdaptador

                var position = viewHolder.adapterPosition
                var vuelo = listaVuelos[position]
                Modelo.eliminarVueloAUsuario(usuarioLogueado!!,vuelo)
                adapter.removeAt(position)

            }
        }
        val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
        deleteItemTouchHelper.attachToRecyclerView(rv_compras_list)

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
                        listaVuelosComprados.forEach {
                            var esOrigen:Boolean = it.origen.lowercase(Locale.getDefault()).contains(search)
                            var esDestino:Boolean = it.destino.lowercase(Locale.getDefault()).contains(search)
                            if (esOrigen || esDestino){
                                listaVuelosMostrar.add(it)
                            }
                        }
                        configurarVuelosCompradosRecyclerView(listaVuelosMostrar!!)
                        vuelosAdaptador!!.notifyDataSetChanged()

                    }else{
                        listaVuelosMostrar.clear()
                        listaVuelosMostrar.addAll(listaVuelosComprados)
                        configurarVuelosCompradosRecyclerView(listaVuelosMostrar)
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
        internal const val COMPRAS_USUARIO = "compras_usuario"
        internal const val USUARIO_LOGUEADO = "usuario_logueado"
    }
}