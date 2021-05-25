package com.example.lab04.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import com.example.lab04.R
import com.example.lab04.logica.Modelo
import com.example.lab04.logica.Usuario

class LoginActivity : AppCompatActivity() {

    private var modelo:Modelo = Modelo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var btnIngresar = findViewById<Button>(R.id.btn_ingresar)
        btnIngresar.setOnClickListener {
            //Login del usuario
            login()
        }

        var registrar = findViewById<TextView>(R.id.tv_registrar)
        registrar.setOnClickListener {
            //Intent registrar usuario
            var intent:Intent = Intent(this,RegistroActivity::class.java)
            startActivity(intent)
        }

        var cambiarContrasenia = findViewById<TextView>(R.id.tv_cambiar)
        cambiarContrasenia.setOnClickListener {
            var intent:Intent = Intent(this,ContraseniaActivity::class.java)
            startActivity(intent)
        }
    }

    fun login(){
        var txtUsuario = findViewById<AppCompatEditText>(R.id.et_usuario).text.toString()
        var txtContrasenia = findViewById<AppCompatEditText>(R.id.et_contrasenia).text.toString()

        if(txtUsuario.isNotEmpty() and txtContrasenia.isNotEmpty()){//Ambos campos con texto
            var usuarioIntent:Usuario? = modelo.obtenerUsuario(txtUsuario)
            if(usuarioIntent != null){
                if(usuarioIntent.contrasenia.equals(txtContrasenia)){
                    //Intent a actividad
                    var intent:Intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, "Contraseña no válida",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Usuario no encontrado",Toast.LENGTH_SHORT).show()
            }
        }else if(txtUsuario.isEmpty()){//Campo usuario vacio
            Toast.makeText(this, "Ingrese un usuario",Toast.LENGTH_SHORT).show()
        }else{//Campo contrasenia vacio
            Toast.makeText(this, "Ingrese la contraseña",Toast.LENGTH_SHORT).show()
        }
    }
}