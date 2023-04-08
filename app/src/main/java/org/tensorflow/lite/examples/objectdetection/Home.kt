package org.tensorflow.lite.examples.objectdetection

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth

class Home : AppCompatActivity() {
    private lateinit var act1: Button
    private lateinit var act2: Button
    private lateinit var act3: Button
    private lateinit var logout: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        toolbar = findViewById(R.id.home_toolbar)
        setSupportActionBar(toolbar)
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
        logout.setOnClickListener {
            mAuth.signOut()
            startActivity(Intent(this@Home, LoginActivity::class.java))
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menu_logout) {
            mAuth.signOut()
            startActivity(Intent(this@Home, signup::class.java))
            finish()
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
}

