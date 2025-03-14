package mx.edu.itesca.practica8_barraza

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class detalle_pelicula : AppCompatActivity() {
    private lateinit var seatLeftText: TextView
    private var occupiedSeats: ArrayList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_pelicula)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val iv_pelicula_image: ImageView = findViewById(R.id.iv_pelicula_imagen)
        val tv_nombre_pelicula: TextView = findViewById(R.id.tv_nombre_pelicula)
        val tv_pelicula_desc: TextView = findViewById(R.id.tv_pelicula_desc)
        val buyTickets: Button = findViewById(R.id.buyTickets)
        seatLeftText = findViewById(R.id.seatLeft)

        val bundle = intent.extras
        if (bundle != null) {
            iv_pelicula_image.setImageResource(bundle.getInt("header"))
            tv_nombre_pelicula.text = bundle.getString("titulo")
            tv_pelicula_desc.text = bundle.getString("sinopsis")
            val seatsAvailable = bundle.getInt("numberSeats", 20)
            occupiedSeats = bundle.getIntegerArrayList("occupiedSeats") ?: ArrayList()
            seatLeftText.text = "Seats Available: $seatsAvailable"
        }

        buyTickets.setOnClickListener {
            val intent = Intent(this, SeatSelection::class.java)
            intent.putExtra("name", tv_nombre_pelicula.text.toString())
            intent.putExtra("id", bundle?.getInt("id", -1))
            intent.putExtra("occupiedSeats", occupiedSeats)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val seatsRemaining = data?.getIntExtra("seatsRemaining", 20) ?: 20
            occupiedSeats = data?.getIntegerArrayListExtra("occupiedSeats") ?: occupiedSeats
            seatLeftText.text = "Seats available: $seatsRemaining"
        }
    }
}