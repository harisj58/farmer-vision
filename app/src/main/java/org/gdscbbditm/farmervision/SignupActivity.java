package org.gdscbbditm.farmervision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.gdscbbditm.farmervision.R;

public class SignupActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword, edtName;
    private Button btnsignup;
    private TextView btnlogin;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        edtName = findViewById(R.id.name);
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);
        btnsignup = findViewById(R.id.btnsignup);
        btnlogin = findViewById(R.id.gotologin);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                String name = edtName.getText().toString();
                String password = edtPassword.getText().toString();

                if(name.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Enter name!", Toast.LENGTH_SHORT).show();
                    edtName.requestFocus();
                } else if(email.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Enter email!", Toast.LENGTH_SHORT).show();
                    edtEmail.requestFocus();
                } else if(password.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Enter password!", Toast.LENGTH_SHORT).show();
                    edtPassword.requestFocus();
                } else if(password.length()<6) {
                    Toast.makeText(SignupActivity.this, "Password too short! Enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    edtPassword.requestFocus();
                } else {
                    SignUpUser(name, email, password);
                }
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToLogin = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(goToLogin);
            }
        });

        if(mAuth.getCurrentUser()!=null) {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

        authStateListener = firebaseAuth -> {
            FirebaseUser mFireBaseUser= mAuth.getCurrentUser();
            if(mFireBaseUser!=null){
                Toast.makeText(SignupActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(SignupActivity.this, HomeActivity.class);
                startActivity(i);
            }
            else
                Toast.makeText(SignupActivity.this, "Please login", Toast.LENGTH_SHORT).show();
        };
    }

    private void SignUpUser(String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(SignupActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                    Intent goToHome = new Intent(SignupActivity.this, HomeActivity.class);
                    goToHome.putExtra("name", name);
                    goToHome.putExtra("email", email);
                    startActivity(goToHome);
                }
                else
                {
                    Toast.makeText(SignupActivity.this, "Registration failed!"+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}