package org.tensorflow.lite.examples.objectdetection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class Home : AppCompatActivity() {
    private lateinit var act1: Button
    private lateinit var act2: Button
    private lateinit var act3: Button
    private lateinit var logout: Button
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mAuth = FirebaseAuth.getInstance()
        act1 = findViewById(R.id.Act1)
        act2 = findViewById(R.id.act2)
        act3 = findViewById(R.id.act3)
        logout = findViewById(R.id.logout)
        act1.setOnClickListener {
            val intent = Intent(this@Home, MainActivity::class.java)
            startActivity(intent)
        }
        act2.setOnClickListener {
            val intent = Intent(this@Home, Cameralist::class.java)
            startActivity(intent)
        }
        act3.setOnClickListener {
            val intent = Intent(this@Home, Setname::class.java)
            startActivity(intent)
        }
        logout.setOnClickListener{
            mAuth.signOut()
            startActivity(Intent(this@Home, LoginActivity::class.java))
        }
    }
}

