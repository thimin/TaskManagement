package com.example.mynote


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mynote.databinding.ActivityAddReservationsBinding

class AddReservationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddReservationsBinding
    private lateinit var db: ReservationsDatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddReservationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ReservationsDatabaseHelper(this)

        binding.saveButton.setOnClickListener{

            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val reservation = Reservation(0,title,content)
            db.insertReservation(reservation)
            finish()
            Toast.makeText(this, "Reservation Saved", Toast.LENGTH_SHORT).show()

        }


    }
}