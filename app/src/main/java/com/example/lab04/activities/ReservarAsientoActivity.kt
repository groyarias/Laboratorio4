package com.example.lab04.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.lab04.R
import com.example.lab04.adaptadores.AsientosAdaptador
import com.example.lab04.logica.Avion
import com.example.lab04.logica.Modelo
import com.example.lab04.logica.Usuario
import com.example.lab04.logica.Vuelo
import kotlinx.android.synthetic.main.activity_reservar_asiento.*

class ReservarAsientoActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private var matrizBooleana:Array<BooleanArray>? = null
    private var arrayList:ArrayList<String> = ArrayList()
    private var gridView: GridView? = null
    private var asientosAdaptador:AsientosAdaptador? = null

    private var lastCard: CardView? = null
    private var filas:Int = 0
    private var columnas:Int = 0
    private var posicionSeleccionada:Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservar_asiento)

        supportActionBar!!.setTitle("Reservación de asiento")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        var intent = intent
        var vuelo:Vuelo = intent.getSerializableExtra(COMPRAR_VUELO) as Vuelo
        var avion: Avion = intent.getSerializableExtra(DETALLES_AVION) as Avion
        var usuario: Usuario = intent.getSerializableExtra(USUARIO_LOGUEADO) as Usuario

        filas = avion.filas
        columnas = avion.columnas

        gridView = gv_asientos
        gridView?.numColumns = columnas
        matrizBooleana = vuelo.boolArrayAsientos
        arrayList = crearListaAsientos(filas,columnas)
        asientosAdaptador = AsientosAdaptador(applicationContext, matrizBooleana!!, arrayList, columnas)
        gridView?.adapter = asientosAdaptador
        gridView?.onItemClickListener = this

        btnComprar.setOnClickListener {
            if (posicionSeleccionada >= 0){
                var fila = posicionSeleccionada/columnas
                var columna = posicionSeleccionada%columnas

                Modelo.obtenerVuelo(vuelo.identificador)?.boolArrayAsientos!![fila][columna] = true
                Modelo.obtenerUsuario(usuario.idUsuario)?.agregarVuelo(vuelo)

                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Debe seleccionar un asiento", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun crearListaAsientos(filas:Int, columnas:Int):ArrayList<String>{
        val letras = arrayOf("A","B","C","D","E","F","G","H","I","J","K","L","M")
        var arrayList:ArrayList<String> = ArrayList()

        for (i in 0..filas-1){
            for (j in 0..columnas-1){
                arrayList.add("${letras[i]}${j+1}")
            }
        }
        return arrayList
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var posicion = position
        var item:String = arrayList.get(posicion)


        var fila = posicion/columnas
        var columna = posicion%columnas

        if(!matrizBooleana!![fila][columna]){
            val card = view?.findViewById<CardView>(R.id.cv_asiento)
            //Cambio de color de los CardView, siendo el último seleccionado de color verde
            if (lastCard != null){
                posicionSeleccionada = posicion
                lastCard?.setBackgroundColor(Color.WHITE)
                lastCard = card
                lastCard?.setBackgroundColor(Color.GREEN)
            }else{
                posicionSeleccionada = posicion
                lastCard = card
                lastCard?.setBackgroundColor(Color.GREEN)
            }
        }
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
        internal const val COMPRAR_VUELO = "comprar_vuelo"
        internal const val DETALLES_AVION = "detalles_avion"
        internal const val USUARIO_LOGUEADO = "usuario_logueado"
    }
}