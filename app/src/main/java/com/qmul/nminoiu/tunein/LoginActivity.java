package com.qmul.nminoiu.tunein;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.onesignal.OneSignal;




import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * created by nicoleta on 14/09/2017
 */
public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_READ_CONTACTS = 0;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private FirebaseAuth firebaseAuth;
    private Button forgotPassword;
    private FirebaseUser user;
    public static String loggedEmail;
    private DatabaseReference mDatabase;
    private String ID;
    private Handler mHandler = new Handler();
    public static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.reset();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Emails");
        firebaseAuth = FirebaseAuth.getInstance();
        mPasswordView = (EditText) findViewById(R.id.password);
        mEmailView.setText("mar@zar.com");
        mPasswordView.setText("123456");

        //initialise One Signal library for notifications
        OneSignal.startInit(this).init();
    }

    /**
     * Forgot password.
     *
     * @param v the v
     */
    public void forgotPassword(View v) {
        firebaseAuth = FirebaseAuth.getInstance();
        forgotPassword = (Button) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailView.getText().toString().trim();
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Please check your email to reset password", Toast.LENGTH_LONG).show();
                            Intent loginIntent = new Intent(LoginActivity.this, LoginActivity.class);
                            startActivity(loginIntent);
                        }
                    }
                });
            }
        });
    }

    /**
     * Signin.
     *
     * @param v the v
     */
    public void signin(View v) {
        final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Please wait...", "Proccessing...", true);
        (firebaseAuth.signInWithEmailAndPassword(mEmailView.getText().toString(), mPasswordView.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(LoginActivity.this, RealTimeActivity.class);
                    i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
                    startActivity(i);
                } else {
                    Log.e("ERROR", task.getException().toString());
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    /**
     * Register.
     *
     * @param view the view
     */
    public void register(View view) {
        Intent nextActivity = new Intent(this, RegisterActivity.class);
        startActivity(nextActivity);
    }
}






