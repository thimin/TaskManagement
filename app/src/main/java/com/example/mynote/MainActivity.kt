package com.example.mynote

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynote.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db:ReservationsDatabaseHelper
    private lateinit var reservationsAdapter: ReservationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        db = ReservationsDatabaseHelper(this)
        reservationsAdapter = ReservationsAdapter(db.getAllReservations(), this)

        binding.reservationsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.reservationsRecyclerView.adapter = reservationsAdapter



        binding.addButton.setOnClickListener{
            val intent = Intent(this, AddReservationsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        reservationsAdapter.refreshData(db.getAllReservations())
    }
}