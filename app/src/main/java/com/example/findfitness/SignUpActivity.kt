package com.example.findfitness

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.findfitness.databinding.SignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    // Pass information through
    private lateinit var binding:SignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dtaRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

// If already have an account you'll be directed to the signactivity
        binding.tvAlreadyIn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
// Logic Type in registeration information to obtain an account
        binding.btnRegister.setOnClickListener{
            val username = binding.etNewUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etNewPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()
// Logic of email and password correction
            if(email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()){
                if(password.equals(confirmPassword)){
                   firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                       if(it.isSuccessful){
                           addUserToDatabase(username,email,firebaseAuth.uid!!)
                        val intent = Intent(this, SignInActivity::class.java)
                           finish()
                           startActivity(intent)
                       }else{
                           Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                       }
                   }

                }
                else{
                    Toast.makeText(this, "Password is Not a Match", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(this, "Please type in the empty fields!", Toast.LENGTH_SHORT).show()
            }

        }
    }
    // Logic of firebase data of user input
    private fun addUserToDatabase(username:String, email: String, uid: String){
        dtaRef = FirebaseDatabase.getInstance().getReference()

        dtaRef.child("user").child(uid).setValue(User(username,email,uid))
    }
}