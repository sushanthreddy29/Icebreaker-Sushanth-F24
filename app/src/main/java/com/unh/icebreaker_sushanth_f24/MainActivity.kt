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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val className = "android-fall24"
    private val db = Firebase.firestore
    private var TAG = "IcebreakerF24"
    private var questionBank: MutableList<Questions>? = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getQuestionsFromFirebase()

        binding.btnSetRandomQuestion.setOnClickListener{
            binding.txtQuestion.text = questionBank!!.random().text
        }
        binding.btnSubmit.setOnClickListener{
            writeStudentToFirebase()
            binding.txtQuestion.text = ""
        }
    }

    private fun getQuestionsFromFirebase(){
        db.collection("Questions")
            .get()
            .addOnSuccessListener { result ->
                questionBank = mutableListOf()
                for(document in result){
                    val question = document.toObject(Questions::class.java)
                    questionBank!!.add(question)
                    Log.d(TAG,"$question")
                }
            }
            .addOnFailureListener{ e ->
                Log.w(TAG,"error", e)
            }
    }


    private fun writeStudentToFirebase(){
        val firstName = binding.txtFirstName
        val lastName = binding.txtLastName
        val prefName = binding.txtPrefName
        val answer = binding.txtAnswer

        Log.d(TAG, "Variables: $firstName $lastName $prefName $answer ")

        val student = hashMapOf(
            "firstName" to firstName.text.toString(),
            "lastName" to lastName.text.toString(),
            "prefName" to prefName.text.toString(),
            "answer" to answer.text.toString(),
            "class" to className,
            "question" to binding.txtQuestion.text.toString()
        )

        db.collection("students")
            .add(student)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG,"DocumentSnapshot written successfully with ID: ${documentReference.id}")
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error adding document", exception)
            }

        firstName.setText("")
        lastName.setText("")
        prefName.setText("")
        answer.setText("")


    }
}