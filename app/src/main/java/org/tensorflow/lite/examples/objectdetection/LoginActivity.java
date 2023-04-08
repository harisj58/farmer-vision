package org.tensorflow.lite.examples.objectdetection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    Button btnlogin;
    TextView btnsignup;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);
        btnlogin = findViewById(R.id.btnlogin);
        btnsignup = findViewById(R.id.btnsignup);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailId = edtEmail.getText().toString();
                String pwd = edtPassword.getText().toString();
                if (emailId.isEmpty()) {
                    edtEmail.setError("Please enter email ID");
                    edtEmail.requestFocus();
                } else if (pwd.isEmpty())
                    Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                else if (emailId.isEmpty() && pwd.isEmpty())
                    Toast.makeText(LoginActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                else if (!(emailId.isEmpty() && pwd.isEmpty())) {
                    mAuth.signInWithEmailAndPassword(emailId, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful())
                                Toast.makeText(LoginActivity.this, "Login error, please login again", Toast.LENGTH_SHORT).show();
                            else {
                                Intent intToHome = new Intent(LoginActivity.this, Home.class);
                                startActivity(intToHome);
                            }
                        }
                    });
                } else
                    Toast.makeText(LoginActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();

            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, signup.class));
            }
        });

        FirebaseUser mFireBaseUser = mAuth.getCurrentUser();
        if (mFireBaseUser != null) {
            Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(LoginActivity.this, Home.class);
            startActivity(i);
        } else
            Toast.makeText(LoginActivity.this, "Please login", Toast.LENGTH_SHORT).show();

    }
}