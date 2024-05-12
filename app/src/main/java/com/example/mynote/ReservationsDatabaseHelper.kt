package com.example.mynote

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ReservationsDatabaseHelper(context: Context) :SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME  = "reservationapp.db"
        private const val DATABASE_VERSION  = 1
        private const val TABLE_NAME  = "allreservations"
        private const val COLUMN_ID  = "id"
        private const val COLUMN_TITLE  = "title"
        private const val COLUMN_CONTENT  = "content"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery =  "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertReservation(reservation: Reservation){
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_TITLE, reservation.title)
            put(COLUMN_CONTENT, reservation.content)

        }
        db.insert(TABLE_NAME,null, values)
        db.close()
    }

    fun getAllReservations(): List<Reservation> {
        val reservationslist = mutableListOf<Reservation>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while(cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            val reservation = Reservation(id, title, content)
            reservationslist.add(reservation)
        }
        cursor.close()
        db.close()
        return reservationslist
    }

    fun updateReservation(reservation: Reservation){
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_TITLE, reservation.title)
            put(COLUMN_CONTENT, reservation.content)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(reservation.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getReservationByID(reservationId: Int): Reservation{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $reservationId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title= cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return Reservation(id, title, content)

    }

    fun deleteReservation(reservationId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(reservationId.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()
    }

}