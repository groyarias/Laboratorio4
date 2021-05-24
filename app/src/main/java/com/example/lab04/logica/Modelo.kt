package com.example.lab04.logica

/*class Modelo private constructor(){

    companion object{
        private var instance:Modelo? = null

        //Llamado al modelo de la forma Modelo()/Modelo.invoke()
        operator fun invoke() = synchronized(this){//Thread safe: de lo contrario podría causar problemas si se manejara con hilos (solución)
            if (instance == null)
                instance = Modelo()
            instance
        }
    }*/
object Modelo{
    private var usuarios:ArrayList<Usuario> = ArrayList<Usuario>()

    init {
        //Usuarios
        agregarUsuario(Usuario("admin","admin","Administrador","NA","NA"))
        agregarUsuario(Usuario("jorge","admin","Jorge","Canales","Espinoza"))
        agregarUsuario(Usuario("roy","admin","Roy","Arias","Sandoval"))

    }


    fun obtenerUsuarios():ArrayList<Usuario>{
        return usuarios
    }

    fun agregarUsuario(usuario:Usuario){
        usuarios.add(usuario)
    }

    fun obtenerUsuario(idUsuario:String):Usuario?{
        usuarios.forEach { it ->
            if(it.idUsuario.equals(idUsuario))
                return it
        }
        return null
    }

    fun modificarUsuario(usuario:Usuario):Boolean{
        usuarios.forEach { it ->
            if(it.idUsuario.equals(usuario.idUsuario)){
                it.contrasenia = usuario.contrasenia
                it.nombre = usuario.nombre
                it.primerApellido = usuario.primerApellido
                it.segundoApellido = usuario.segundoApellido
                return true
            }
        }
        return false
    }

    fun modificarContraseniaUsuario(idUsuario: String, nuevaContrasenia:String, confirmacion:String):Boolean{
        usuarios.forEach { it ->
            if(it.idUsuario.equals(idUsuario)){
                if (nuevaContrasenia.equals(confirmacion)){
                    it.contrasenia = nuevaContrasenia
                    return true
                }
            }
        }
        return false
    }

}

/*fun main(){
    var modelo = Modelo
    println(modelo)
    var modelo1 = Modelo
    println(modelo1)
}*/