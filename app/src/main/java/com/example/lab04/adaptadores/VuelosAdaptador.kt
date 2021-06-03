package com.example.lab04.adaptadores

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lab04.R
import com.example.lab04.activities.ListVuelosActivity
import com.example.lab04.activities.ReservarAsientoActivity
import com.example.lab04.logica.Modelo
import com.example.lab04.logica.Usuario
import com.example.lab04.logica.Vuelo
import kotlinx.android.synthetic.main.item_vuelo.view.*


open class VuelosAdaptador (
    private val context: Context,
    private var listaVuelos:ArrayList<Vuelo>
    ):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    /**
     *Infla las vistas de elementos que están diseñadas en el archivo de diseño xml.
     *crea un nuevo {@link ViewHolder} e inicializa algunos campos privados por se usados en el RecyclerView.
    **/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_vuelo,
                parent,
                false
            )
        )
    }

    /**
     *Vincula cada elemento de ArrayList a una vista
     *Llamado cuando el RecyclerView necesita un nuevo {@link ViewHolder} del tipo especificado
     *para representar un elemento.
     *Este nuevo ViewHolder debe construirse con una nueva Vista que pueda representar los
     *elementos del tipo dado. Puede crear una nueva vista manualmente o inflarla desde un
     *archivo de diseño XML.
    **/

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vuelo = listaVuelos[position]

        if(holder is MyViewHolder){
            holder.itemView.tvOrigen.text = vuelo.origen
            holder.itemView.tvDestino.text = vuelo.destino
            holder.itemView.tvSalida.text = vuelo.salida
            holder.itemView.tvRegreso.text = vuelo.regreso

            holder.itemView.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onClick(position, vuelo)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listaVuelos.size
    }

    /**
     *Función para vincular el onClickListener
     **/
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: Vuelo)
    }

    /**
     * Una función para comprar el vuelo y pasar el detalle existente a través de un intent
     */
    fun notifyBuyItem(activity: Activity, position: Int, requestCode: Int, usuario: Usuario) {
        val intent = Intent(context, ReservarAsientoActivity::class.java)
        var vuelo:Vuelo = listaVuelos[position]

        intent.putExtra(ReservarAsientoActivity.COMPRAR_VUELO, Modelo.obtenerVuelo(vuelo.identificador))
        intent.putExtra(ReservarAsientoActivity.DETALLES_AVION, Modelo.obtenerAvion(vuelo.idAvion!!))
        intent.putExtra(ReservarAsientoActivity.USUARIO_LOGUEADO, usuario)

        /*activity.startActivityForResult(
            intent,
            requestCode
        )*/

        activity.startActivity(intent)
        notifyItemChanged(position)
    }

    fun removeAt(position: Int) {
        listaVuelos.removeAt(position)
        notifyItemRemoved(position)
    }
    /**
    * Un ViewHolder describe un item view y metadata sobre su lugar en el RecyclerView
    * */
    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

}