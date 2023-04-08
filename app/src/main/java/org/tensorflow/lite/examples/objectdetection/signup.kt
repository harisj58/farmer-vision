package org.tensorflow.lite.examples.objectdetection

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signup : AppCompatActivity() {
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtName: EditText
    private lateinit var btnsignup: Button
    private lateinit var btnlogin: TextView
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    private var authStateListener: AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        edtName = findViewById(R.id.name)
        edtEmail = findViewById(R.id.email)
        edtPassword = findViewById(R.id.password)
        btnsignup = findViewById(R.id.btnsignup)
        btnlogin = findViewById(R.id.gotologin)
        btnsignup.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val name = edtName.text.toString()
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(this, "Enter name!", Toast.LENGTH_SHORT).show()
                edtName.requestFocus()
            } else if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Enter email!", Toast.LENGTH_SHORT).show()
                edtEmail.requestFocus()
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Enter password!", Toast.LENGTH_SHORT).show()
                edtPassword.requestFocus()
            } else if (password.length < 6) {
                Toast.makeText(
                    this,
                    "Password too short! Enter minimum 6 characters!",
                    Toast.LENGTH_SHORT
                ).show()
                edtPassword.requestFocus()
            } else Signup(email, password, name);
        }
        btnlogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        if (mAuth.currentUser != null) {
            startActivity(Intent(this@signup, Home::class.java))
        }

        authStateListener = AuthStateListener {
            val mFireBaseUser: FirebaseUser? = mAuth.currentUser
            if (mFireBaseUser != null) {
                Toast.makeText(this@signup, "You are logged in", Toast.LENGTH_SHORT).show()
                val i = Intent(this@signup, MainActivity::class.java)
                startActivity(i)
            } else Toast.makeText(this@signup, "Please login", Toast.LENGTH_SHORT).show()
        }
    }

    private fun Signup(email: String, password: String, name: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@signup, Home::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@signup, "Some error occurred", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name, email, uid))
    }
}