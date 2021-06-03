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
    private var vuelos:ArrayList<Vuelo> = ArrayList<Vuelo>()
    private var aviones:ArrayList<Avion> = ArrayList<Avion>()

    private var usuariologueado:Usuario? = null

    init {
        //Usuarios
        agregarUsuario(Usuario("admin","Administrador","NA","NA","admin"))
        agregarUsuario(Usuario("jorge","Jorge","Canales","Espinoza","admin"))
        agregarUsuario(Usuario("roy","Roy","Arias","Sandoval","admin"))

        agregarVuelo(Vuelo("v1","Costa Rica,San Jose","Colombia,Cali","05/06/2021","10/06/2021"))
        agregarVuelo(Vuelo("v2","Costa Rica,San Jose","USA,Chicago","07/06/2021","21/06/2021"))
        agregarVuelo(Vuelo("v3","Costa Rica,San Jose","Perú,Lima","04/06/2021","17/06/2021"))

        agregarAvion(Avion("a1","Boeing","747",10,5))
        agregarAvion(Avion("a2","Airbus","A340",8,3))

        vuelos[0].idAvion = aviones[0].identificador
        vuelos[0].boolArrayAsientos = crearMatrizBooleanaAsientos(aviones[0].filas,aviones[0].columnas)
        //Reservacion de asientos
        vuelos[0].boolArrayAsientos!![0][0] = true
        vuelos[0].boolArrayAsientos!![3][3] = true


        vuelos[1].idAvion = aviones[1].identificador
        vuelos[1].boolArrayAsientos = crearMatrizBooleanaAsientos(aviones[1].filas,aviones[1].columnas)

        vuelos[2].idAvion = aviones[1].identificador
        vuelos[2].boolArrayAsientos = crearMatrizBooleanaAsientos(aviones[1].filas,aviones[1].columnas)


        var r_user = usuarios[2]
        r_user.agregarVuelo(vuelos[0])
        r_user.agregarVuelo(vuelos[2])

    }

    fun establecerUsuarioLogueado(usuario: Usuario){
        usuariologueado = usuario
    }

    fun obtenerUsuarioLogueado():Usuario{
        return usuariologueado!!
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

    fun modificarContraseniaUsuario(idUsuario: String, contrasenia:String ,nuevaContrasenia:String, confirmacion:String):Boolean{
        usuarios.forEach { it ->
            if(it.idUsuario.equals(idUsuario)){
                if(it.contrasenia != contrasenia){
                   return false
                }
                if (nuevaContrasenia.equals(confirmacion)){
                    it.contrasenia = nuevaContrasenia
                    return true
                }
            }
        }
        return false
    }

    fun obtenerVuelos():ArrayList<Vuelo>{
        return vuelos
    }

    fun agregarVuelo(vuelo: Vuelo){
        vuelos.add(vuelo)
    }

    fun eliminarVueloAUsuario(usuario: Usuario, vuelo: Vuelo){
        if(vuelos.contains(vuelo)){
            usuarios.find { it.idUsuario.equals(usuario.idUsuario) }?.eliminarVuelo(vuelo.identificador)
        }
    }

    fun obtenerVuelo(idVuelo:String):Vuelo?{
        vuelos.forEach { it ->
            if(it.identificador.equals(idVuelo))
                return it
        }
        return null
    }

    fun agregarAvion(avion: Avion){
        aviones.add(avion)
    }

    fun obtenerAviones():ArrayList<Avion>{
        return aviones
    }

    fun obtenerAvion(idAvion:String):Avion?{
        aviones.forEach { it ->
            if(it.identificador.equals(idAvion))
                return it
        }
        return null
    }

    fun crearMatrizBooleanaAsientos(filas:Int, columnas:Int): Array<BooleanArray> {
        val matrizBool= Array(filas){BooleanArray(columnas)}
        return matrizBool
    }

}

/*fun main(){
    var modelo = Modelo
    println(modelo)
    var modelo1 = Modelo
    println(modelo1)

    println(modelo.obtenerVuelos().toString())
}*/