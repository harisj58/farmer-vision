package org.gdscbbditm.farmervision;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.gdscbbditm.farmervision.R;

public class HomeActivity extends AppCompatActivity {

    private Button camMode, camList;
    private FirebaseAuth mAuth;
    private Toolbar toolbar;
    private TextView greeting;
    private DatabaseReference mDbRef;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        camMode = findViewById(R.id.act1);
        camList = findViewById(R.id.act2);
        greeting = findViewById(R.id.textView3);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);


        destroyCamFromDatabase();

        camMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }
        });

        camList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CameraList.class));
            }
        });

        mDbRef = FirebaseDatabase.getInstance().getReference();

        String uid = mAuth.getCurrentUser().getUid();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("name");
            String email = extras.getString("email");
            addUserToDatabase(name, email, uid);
        }

        DatabaseReference userRef = mDbRef.child("user").child(uid).child("name");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.
                String name = snapshot.getValue(String.class);

                // after getting the value we are setting
                // our value to our text view in below line.
                greeting.setText("Welcome, "+name+"!");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // no warnings since functionality is not disrupted
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            mAuth.signOut();
            gsc.signOut();
            Toast.makeText(HomeActivity.this, "Signed out successfully.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void addUserToDatabase(String name, String email, String uid) {
        mDbRef.child("user").child(uid).setValue(new User(name, email, uid));
    }

    @Override
    protected void onResume() {
        destroyCamFromDatabase();
        super.onResume();
    }

    private void destroyCamFromDatabase() {
        String accountUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String deviceId = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("user").child(accountUID)
                .child("cameras");

        dbRef.child(deviceId).removeValue();
    }
}