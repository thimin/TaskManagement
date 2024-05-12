package com.example.mynote

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mynote.databinding.ActivityUpdateReservationBinding

class UpdateReservationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateReservationBinding
    private lateinit var db: ReservationsDatabaseHelper
    private var reservationId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ReservationsDatabaseHelper(this)

        reservationId = intent.getIntExtra("reservation_id", -1)
        if(reservationId == -1){
            finish()
            return
        }

        val note = db.getReservationByID(reservationId)
        binding.updateTitleEditText.setText(note.title)
        binding.updateContentEditText.setText(note.content)

        binding.updateSaveButton.setOnClickListener{
            val newTitle = binding.updateTitleEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()
            val updatedReservation =Reservation(reservationId, newTitle, newContent)
            db.updateReservation(updatedReservation)
            finish()
            Toast.makeText(this, "changes Saved", Toast.LENGTH_SHORT).show()
        }

        }
    }
