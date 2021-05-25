package com.example.lab04.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.lab04.R
import com.example.lab04.logica.Modelo
import com.example.lab04.logica.Usuario
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RegistroActivity : AppCompatActivity() {

    private val modelo:Modelo = Modelo

    private var idUsuario:String? = null
    private var nombre:String? = null
    private var primerApellido:String? = null
    private var segundoApellido:String? = null
    private var contrasenia:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        var fabAgregarUsuario = findViewById<FloatingActionButton>(R.id.btnAgregarUsuario)
        fabAgregarUsuario.setOnClickListener {

            idUsuario = findViewById<EditText>(R.id.et_usuario).text.toString()
            nombre = findViewById<EditText>(R.id.et_nombre).text.toString()
            primerApellido = findViewById<EditText>(R.id.et_primerApellido).text.toString()
            segundoApellido = findViewById<EditText>(R.id.et_segundoApellido).text.toString()
            contrasenia = findViewById<EditText>(R.id.et_contrasenia).text.toString()

            Toast.makeText(this,"${idUsuario} ${contrasenia}", Toast.LENGTH_SHORT).show()

            if (validarRegistro(idUsuario!!, nombre!!, primerApellido!!,segundoApellido!!, contrasenia!!)){
                var usuario : Usuario = Usuario(idUsuario!!, nombre!!, primerApellido!!,segundoApellido!!, contrasenia!!)
                modelo.agregarUsuario(usuario)
                var intent:Intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"Se deben ingresar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }



    }

    fun validarRegistro(idUsuario:String,nombre:String, primerApellido:String, segundoApellido:String, contrasenia:String):Boolean{
        return (idUsuario.isNotEmpty() and
                nombre.isNotEmpty() and
                primerApellido.isNotEmpty() and
                segundoApellido.isNotEmpty() and
                contrasenia.isNotEmpty())
    }
}