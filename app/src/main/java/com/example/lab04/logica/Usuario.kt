package com.example.lab04.logica

import java.io.Serializable

data class Usuario(
    var idUsuario:String,
    var nombre:String,
    var primerApellido:String,
    var segundoApellido:String,
    var contrasenia:String
):Serializable{

    private var vuelosComprados:ArrayList<Vuelo> = ArrayList()

    fun obtenerVuelosComprados():ArrayList<Vuelo>{
        return vuelosComprados
    }

    fun establecerVuelosComprados(vuelos:ArrayList<Vuelo>){
        vuelosComprados.clear()
        vuelosComprados.addAll(vuelos)
    }

    fun agregarVuelo(vuelo:Vuelo){
        vuelosComprados.add(vuelo)
    }

    fun eliminarVuelo(idVuelo:String):Boolean{
        vuelosComprados.forEach { it ->
            if (it.identificador.equals(idVuelo)){
                vuelosComprados.remove(it)
                return true
            }
        }
        return false
    }

    fun consultarVuelo(idVuelo:String):Vuelo?{
        vuelosComprados.forEach { it ->
            if (it.identificador.equals(idVuelo)) return it
        }
        return null
    }

}
