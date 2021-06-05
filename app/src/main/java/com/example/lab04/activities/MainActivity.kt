package com.example.lab04.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.lab04.R
import com.example.lab04.databinding.ActivityMainBinding
import com.example.lab04.logica.Modelo
import com.example.lab04.logica.Usuario

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private var usuarioLogeado : Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home
            ), drawerLayout
        )

        //Aquí se obtiene el objeto del usuario logueado
        //var intent = intent
        //usuarioLogeado = intent.getSerializableExtra("loged_user")  as Usuario
        usuarioLogeado = Modelo.obtenerUsuarioLogueado()


        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.menu.findItem(R.id.nav_perfil_usuario).setCheckable(true)
        navView.menu.findItem(R.id.nav_perfil_usuario).setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.nav_perfil_usuario -> {
                    drawerLayout.close()
                    val intent = Intent(this, SecondActivity::class.java)
                    intent.putExtra(SecondActivity.USUARIO_LOGUEADO, Modelo.obtenerUsuarioLogueado())
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }


        navView.menu.findItem(R.id.nav_listado_vuelos_activity).setCheckable(true)
        navView.menu.findItem(R.id.nav_listado_vuelos_activity).setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.nav_listado_vuelos_activity -> {
                    drawerLayout.close()
                    val intent = Intent(this, ListVuelosActivity::class.java)
                    intent.putExtra(ListVuelosActivity.USUARIO_LOGUEADO, usuarioLogeado)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        navView.menu.findItem(R.id.nav_listado_compras_activity).setCheckable(true)
        navView.menu.findItem(R.id.nav_listado_compras_activity).setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.nav_listado_compras_activity -> {
                    drawerLayout.close()

                    val intent = Intent(this, ListComprasActivity::class.java)
                    intent.putExtra(ListComprasActivity.COMPRAS_USUARIO, Modelo.obtenerUsuario(usuarioLogeado!!.idUsuario)?.obtenerVuelosComprados())
                    intent.putExtra(ListComprasActivity.USUARIO_LOGUEADO, usuarioLogeado)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        navView.menu.findItem(R.id.nav_logout).setCheckable(true)
        navView.menu.findItem(R.id.nav_logout).setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.nav_logout -> {
                    logout(this)
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun logout(activity: Activity) {
        //Initialize alert dialog
        var builder = AlertDialog.Builder(activity)
        //Set title
        builder.setTitle("Cerrar Sesión")
        //Set message
        builder.setMessage("Está seguro que desea cerrar sesión?")
        //Positive yes button
        builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->

            var intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            //Finish activity
            activity.finishAffinity()
        })
        //Negative no button
        builder.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
            //Dismiss dialog
            dialog.dismiss()
        })
        //Show dialog
        builder.show()
    }

}