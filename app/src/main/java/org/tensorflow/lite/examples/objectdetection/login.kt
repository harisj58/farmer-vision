package org.tensorflow.lite.examples.objectdetection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {
     private lateinit var  edtEmail: EditText
    private lateinit var  edtPassword: EditText
    private lateinit var  btnlogin: Button
    private lateinit var  btnsignup: TextView
private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
supportActionBar?.hide()
        mAuth= FirebaseAuth.getInstance()

         edtEmail=findViewById(R.id.email)
        edtPassword=findViewById(R.id.password)
        btnlogin=findViewById(R.id.btnlogin)
        btnsignup=findViewById(R.id.btnsignup)
        btnsignup.setOnClickListener {
            val intent =Intent(this,signup::class.java)
            startActivity(intent)
        }
        btnlogin.setOnClickListener{
            val email=edtEmail.text.toString()
            val password =edtPassword.text.toString()

            Login(email,password);
        }
    }
    private fun Login(email:String,password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent =  Intent(this@login,Home::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@login,"User dont exist", Toast.LENGTH_SHORT).show()

                }
            }
    }}
