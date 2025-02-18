package mx.edu.itesca.practica8_barraza

import android.content.Intent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.botonP)

        button.setOnClickListener {
            val intent: Intent = Intent(this, catalogo::class.java)
            startActivity(intent)
        }
    }
}