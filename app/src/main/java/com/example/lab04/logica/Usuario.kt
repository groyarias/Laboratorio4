package com.example.lab04.logica

import java.io.Serializable

data class Usuario(
    var idUsuario:String,
    var nombre:String,
    var primerApellido:String,
    var segundoApellido:String,
    var contrasenia:String
):Serializable
