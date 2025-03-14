package mx.edu.itesca.practica8_barraza

data class Pelicula(
    var titulo: String,
    var image: Int,
    var header: Int,
    var sinopsis: String,
    var seats: ArrayList<Cliente>,
    var occupiedSeats: ArrayList<Int> = ArrayList() // Lista de asientos ocupados
)