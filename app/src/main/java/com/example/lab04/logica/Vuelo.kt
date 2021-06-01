package com.example.lab04.logica

import java.io.Serializable
import java.util.*

data class Vuelo(
    var identificador:String,
    var origen:String,
    var destino:String,
    var salida:String,
    var regreso: String
):Serializable
