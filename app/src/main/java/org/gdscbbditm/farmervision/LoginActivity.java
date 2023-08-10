package org.gdscbbditm.farmervision;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.gdscbbditm.farmervision.R;

import java.util.function.Consumer;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    Button btnlogin;
    SignInButton gSignIn;
    TextView btnsignup;
    FirebaseAuth mAuth;

    SharedPreferences sharedPreferences;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);
        btnlogin = findViewById(R.id.btnlogin);
        btnsignup = findViewById(R.id.btnsignup);
        gSignIn = findViewById(R.id.google_sign_in);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        gSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailId = edtEmail.getText().toString();
                String pwd = edtPassword.getText().toString();
                if (emailId.isEmpty()) {
                    edtEmail.setError("Please enter email ID");
                    edtEmail.requestFocus();
                } else if (pwd.isEmpty()) {
                    edtEmail.setError("Please enter email ID");
                    edtPassword.requestFocus();
                }
                else if (emailId.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                    edtEmail.requestFocus();
                }
                else if (!(emailId.isEmpty() && pwd.isEmpty())) {
                    mAuth.signInWithEmailAndPassword(emailId, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful())
                                Toast.makeText(LoginActivity.this, "Login error, please login again", Toast.LENGTH_SHORT).show();
                            else {
                                Intent intToHome = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intToHome);
                            }
                        }
                    });
                } else
                    Toast.makeText(LoginActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();

            }
        });

        authStateListener = firebaseAuth -> {
            FirebaseUser mFireBaseUser = mAuth.getCurrentUser();
            if (mFireBaseUser != null) {
                Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(i);
            } else
                Toast.makeText(LoginActivity.this, "Please login", Toast.LENGTH_SHORT).show();
        };

        sharedPreferences = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean("firstTime", true);
        if (isFirstTime) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime", false);
            editor.commit();
            Intent intent = new Intent(LoginActivity.this, OnboardingActivity.class);
            startActivity(intent);
            finish();
        }

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        if(mAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }


    }

    void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken(), account.getDisplayName(), account.getEmail());
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void firebaseAuth(String idToken, String name, String email) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    navigateToSecondActivity(name, email);
                } else {
                    Toast.makeText(LoginActivity.this, "Error! Google sign in failed.", Toast.LENGTH_SHORT).show();
                }
            } 
        });
    }
    void navigateToSecondActivity(String name, String email){
        Intent intToHome = new Intent(LoginActivity.this, HomeActivity.class);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        intToHome.putExtra("name", name);
        intToHome.putExtra("email", email);
        startActivity(intToHome);
    }
}