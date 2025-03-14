package mx.edu.itesca.practica8_barraza

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SeatSelection : AppCompatActivity() {
    private var selectedSeat: Int = -1
    private lateinit var occupiedSeats: ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_seat_selection)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val title: TextView = findViewById(R.id.titleSeats)
        var posMovie = -1

        val bundle = intent.extras
        if (bundle != null) {
            title.text = bundle.getString("name")
            posMovie = bundle.getInt("id")
            occupiedSeats = bundle.getIntegerArrayList("occupiedSeats") ?: ArrayList()
        }

        val confirm: Button = findViewById(R.id.confirm)
        val row1: RadioGroup = findViewById(R.id.row1)
        val row2: RadioGroup = findViewById(R.id.row2)
        val row3: RadioGroup = findViewById(R.id.row3)
        val row4: RadioGroup = findViewById(R.id.row4)

        // Deshabilitar asientos ocupados
        disableOccupiedSeats(row1, 1, 5)
        disableOccupiedSeats(row2, 6, 10)
        disableOccupiedSeats(row3, 11, 15)
        disableOccupiedSeats(row4, 16, 20)

        val seatListener = RadioGroup.OnCheckedChangeListener { group, checkedId ->
            if (checkedId > -1) {
                selectedSeat = when (group.id) {
                    R.id.row1 -> group.indexOfChild(group.findViewById(checkedId)) + 1
                    R.id.row2 -> group.indexOfChild(group.findViewById(checkedId)) + 6
                    R.id.row3 -> group.indexOfChild(group.findViewById(checkedId)) + 11
                    R.id.row4 -> group.indexOfChild(group.findViewById(checkedId)) + 16
                    else -> -1
                }
                when (group.id) {
                    R.id.row1 -> { row2.clearCheck(); row3.clearCheck(); row4.clearCheck() }
                    R.id.row2 -> { row1.clearCheck(); row3.clearCheck(); row4.clearCheck() }
                    R.id.row3 -> { row1.clearCheck(); row2.clearCheck(); row4.clearCheck() }
                    R.id.row4 -> { row1.clearCheck(); row2.clearCheck(); row3.clearCheck() }
                }
            }
        }

        row1.setOnCheckedChangeListener(seatListener)
        row2.setOnCheckedChangeListener(seatListener)
        row3.setOnCheckedChangeListener(seatListener)
        row4.setOnCheckedChangeListener(seatListener)

        confirm.setOnClickListener {
            if (selectedSeat != -1) {
                occupiedSeats.add(selectedSeat)
                val returnIntent = Intent()
                val seatsRemaining = 20 - occupiedSeats.size
                returnIntent.putExtra("seatsRemaining", seatsRemaining)
                returnIntent.putExtra("occupiedSeats", occupiedSeats)
                setResult(RESULT_OK, returnIntent)
                finish()
            }
        }
    }

    private fun disableOccupiedSeats(radioGroup: RadioGroup, start: Int, end: Int) {
        for (i in 0 until radioGroup.childCount) {
            val seatNumber = start + i
            val radioButton = radioGroup.getChildAt(i) as RadioButton
            if (occupiedSeats.contains(seatNumber)) {
                radioButton.isEnabled = false
                radioButton.setBackgroundResource(R.drawable.radio_disabled) // Usar el drawable de "not available"
            }
        }
    }
}