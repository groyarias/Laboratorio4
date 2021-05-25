package com.example.lab04.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import com.example.lab04.R
import com.example.lab04.logica.Modelo
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ContraseniaActivity : AppCompatActivity() {

    private val modelo:Modelo = Modelo

    private var idUsuario:String? = null
    private var contrasenia:String? = null
    private var nuevaContrasenia:String? = null
    private var confirmacion:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contrasenia)

        var fabEditarContrasenia = findViewById<FloatingActionButton>(R.id.btn_editUser)
        fabEditarContrasenia.setOnClickListener {
            idUsuario = findViewById<EditText>(R.id.et_usuario).text.toString()
            contrasenia = findViewById<AppCompatEditText>(R.id.et_contrasenia_actual).text.toString()
            nuevaContrasenia = findViewById<AppCompatEditText>(R.id.et_nueva_contrasenia).text.toString()
            confirmacion = findViewById<AppCompatEditText>(R.id.et_confirmacion_contrasenia).text.toString()
            if(validarCambioContrasenia(idUsuario!!,contrasenia!!,nuevaContrasenia!!,confirmacion!!)){
                if(modelo.modificarContraseniaUsuario(idUsuario!!,contrasenia!!,nuevaContrasenia!!,confirmacion!!)){
                    var intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this,"Verificar los datos ingresados",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Ingrese todos los datos en los campos",Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun validarCambioContrasenia(idUsuario:String,contrasenia:String,nuevaContrasenia:String,confirmacion:String):Boolean{
        return (idUsuario.isNotEmpty() and
                contrasenia.isNotEmpty() and
                nuevaContrasenia.isNotEmpty() and
                confirmacion.isNotEmpty())
    }
}