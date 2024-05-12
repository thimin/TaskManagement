package com.example.mynote

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView //RecyclerView.Adapter<ReservationsAdapter.ReservationViewHolder>{

class ReservationsAdapter (private var reservations: List<Reservation>, context: Context) : RecyclerView.Adapter<ReservationsAdapter.ReservationViewHolder>(){

    private val db: ReservationsDatabaseHelper = ReservationsDatabaseHelper(context)
    class ReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reservation_item, parent, false)
        return ReservationViewHolder(view)
    }

    override fun getItemCount(): Int = reservations.size


    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val reservation = reservations[position]
        holder.titleTextView.text = reservation.title
        holder.contentTextView.text = reservation.content
        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateReservationActivity::class.java).apply{
                putExtra("reservation_id", reservation.id)
            }
            holder.itemView.context.startActivity(intent)
        }
        holder.deleteButton.setOnClickListener{
            db.deleteReservation(reservation.id)
            refreshData(db.getAllReservations())
            Toast.makeText(holder.itemView.context, "Reservation Deleted",Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newReservations: List<Reservation>){
        reservations = newReservations
        notifyDataSetChanged()
    }

}