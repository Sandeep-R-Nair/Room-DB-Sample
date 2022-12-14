package com.example.roomdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.roomdb.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Sandeep on 12-Dec-22.
 */

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var appDb: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize the db
        appDb = AppDatabase.getDatabase(this)

        binding.btnWriteData.setOnClickListener{
            writeData()
        }

        binding.btnReadData.setOnClickListener{
            readData()
        }

        binding.btnUpdateData.setOnClickListener{
            updateData()
        }

        binding.btnDeleteAll.setOnClickListener{
            GlobalScope.launch {
                appDb.studentDao().deleteAll()
            }
        }

    }

    private fun updateData() {
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val rollNo = binding.etRollNo.text.toString()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && rollNo.isNotEmpty()){
            //add to the model class

            //call the insert() method to insert data to the table
            GlobalScope.launch(Dispatchers.IO){
                appDb.studentDao().update(firstName, lastName, rollNo.toInt())
            }

            binding.etFirstName.text.clear()
            binding.etLastName.text.clear()
            binding.etRollNo.text.clear()

            Toast.makeText(this@MainActivity,"Success",Toast.LENGTH_SHORT).show()
        }else{
            binding.etFirstName.error = "Can't be null"
            binding.etLastName.error = "Can't be null"
            binding.etRollNo.error = "Can't be null"
            Toast.makeText(this@MainActivity,"Please fill all details",Toast.LENGTH_SHORT).show()
        }
    }

    private fun readData() {
        val rollNo = binding.etRollNoRead.text.toString()
        if (rollNo.isNotEmpty()){
            var student: Student
            //call the getAllStudents() method to read data from the table
            GlobalScope.launch(Dispatchers.IO){
                student= appDb.studentDao().findByRoll(rollNo.toInt())
                displayData(student)
            }
        }else{
            binding.etRollNoRead.error = "Can't be null"
            Toast.makeText(this@MainActivity,"Please enter the roll no.",Toast.LENGTH_SHORT).show()
        }
    }

    private fun writeData() {
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val rollNo = binding.etRollNo.text.toString()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && rollNo.isNotEmpty()){
            //add to the model class
            val student= Student(null, firstName, lastName, rollNo.toInt())

            //call the insert() method to insert data to the table
            GlobalScope.launch(Dispatchers.IO){
                appDb.studentDao().insert(student)
            }

            binding.etFirstName.text.clear()
            binding.etLastName.text.clear()
            binding.etRollNo.text.clear()

            Toast.makeText(this@MainActivity,"Success",Toast.LENGTH_SHORT).show()
        }else{
            binding.etFirstName.error = "Can't be null"
            binding.etLastName.error = "Can't be null"
            binding.etRollNo.error = "Can't be null"
            Toast.makeText(this@MainActivity,"Please fill all details",Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun displayData(student: Student) {
        withContext(Dispatchers.Main){
            binding.tvFirstName.text = student.firstName
            binding.tvLastName.text = student.lastName
            binding.tvRollNo.text = student.rollNo.toString()
        }
    }

}