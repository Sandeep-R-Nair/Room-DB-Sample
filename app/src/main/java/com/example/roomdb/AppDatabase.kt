package com.example.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Sandeep on 12-Dec-22.
 */

@Database(entities = [Student::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        //create a reference to the 'StudentDatabase'
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            val tempInstance = INSTANCE

            //check whether the current instance is null. If not return current instance
            if (tempInstance != null){
                return tempInstance
            }

            //else create a new instance of the database and return it
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "student_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}