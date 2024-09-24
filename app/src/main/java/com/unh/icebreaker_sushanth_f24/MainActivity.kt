package com.unh.icebreaker_sushanth_f24

import android.nfc.Tag
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.unh.icebreaker_sushanth_f24.databinding.ActivityMainBinding
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSetRandomQuestion.setOnClickListener{
            binding.txtQuestion.text = "Hello"
        }
        binding.btnSubmit.setOnClickListener{
            binding.txtQuestion.text = ""
            writeStudentToFirebase()
        }
    }

    private fun writeStudentToFirebase(){
        val firstName = binding.txtFirstName.text
        val lastName = binding.txtLastName.text
        val prefName = binding.txtPrefName.text
        val answer = binding.txtAnswer.text

        Log.d("IcebreakerF24", "Variables: $firstName $lastName $prefName $answer")

    }
}