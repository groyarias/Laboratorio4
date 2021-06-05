package com.example.lab04.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.lab04.R
import com.example.lab04.logica.Usuario
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    var usuario: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        supportActionBar!!.setTitle("Perfil usuario")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        var intent = intent
        usuario = intent.getSerializableExtra(USUARIO_LOGUEADO) as Usuario
        mostrarPerfilUsuario()

    }

    fun mostrarPerfilUsuario(){
        txtNameProfile.text = usuario?.nombre
        tvIdPerfil.text = usuario?.idUsuario
        tvNombrePerfil.text = usuario?.nombre
        tvApellidoPerfil.text = usuario?.primerApellido
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
        internal const val USUARIO_LOGUEADO = "usuario_logueado"
    }
}