package com.example.lab04.logica

import java.io.Serializable

data class Avion(
    var identificador:String,
    var marca:String,
    var modelo:String,
    var filas:Int,
    var columnas:Int
):Serializable