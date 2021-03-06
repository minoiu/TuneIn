package com.qmul.nminoiu.tunein;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nicoleta on 29/09/2017
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signup_button;
    private EditText first_name;
    private EditText last_name;
    private AutoCompleteTextView email_register;
    private EditText password;
    private TextView textViewSignin;
    public static String fullname;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        signup_button = (Button) findViewById(R.id.signup_button);
        first_name = (EditText) findViewById(R.id.first_name);
        last_name = (EditText) findViewById(R.id.last_name);
        email_register = (AutoCompleteTextView) findViewById(R.id.email_register);
        password = (EditText) findViewById(R.id.password);
        signup_button.setOnClickListener(this);
        final TextView textViewSignin = (TextView) this.findViewById(R.id.textViewSignin);
        textViewSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    //handle click on register button
    @Override
    public void onClick(View view) {
        if (view == signup_button) {
            registerUser();
        }
    }

    /**
     * Signin.
     *
     * @param view the view
     */
    public void signin(View view) {
        Intent nextActivity = new Intent(this, LoginActivity.class);
        startActivity(nextActivity);
    }

    //registe new user
    private void registerUser() {
        final String email = email_register.getText().toString().trim();
        String passwor = password.getText().toString().trim();
        final String firstname = first_name.getText().toString().trim();
        final String lastname = last_name.getText().toString().trim();
        fullname = firstname + " " + lastname;

        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            //stopping function
            return;
        }

        if (TextUtils.isEmpty(passwor)) {
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            //stopping function
            return;
        }

        if (TextUtils.isEmpty(firstname)) {
            //firstname is empty
            Toast.makeText(this, "Please enter your first name", Toast.LENGTH_SHORT).show();
            //stopping function
            return;
        }

        if (TextUtils.isEmpty(lastname)) {
            //surname is empty
            Toast.makeText(this, "Please enter your last name", Toast.LENGTH_SHORT).show();
            //stopping function
            return;
        }

        //if validations are ok a progress bar is shown
        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, passwor).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    //user is registered
                    Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                    Firebase userRef = ref.child("Users");
                    Map<String,Object> uinfo = new HashMap<String, Object>();
                    uinfo.put(user.getUid(),fullname);

                    Firebase ref1 = new Firebase("https://tunein-633e5.firebaseio.com/");
                    Firebase userRef1 = ref1.child("PublicProfiles");
                    Map<String,Object> uinfo1 = new HashMap<String, Object>();
                    uinfo1.put(user.getUid(),fullname);
                    userRef.updateChildren(uinfo);
                    userRef1.updateChildren(uinfo1);
                    String email = user.getEmail();
                    Firebase ref2 = new Firebase("https://tunein-633e5.firebaseio.com/").child("Emails").child(fullname);
                    Map<String,Object> uinfo2 = new HashMap<String, Object>();
                    uinfo2.put("Email",email);
                    ref2.updateChildren(uinfo2);

                    Firebase ref3 = new Firebase("https://tunein-633e5.firebaseio.com/").child("Fullname").child(user.getUid());
                    Map<String,Object> uinfo3 = new HashMap<String, Object>();
                    uinfo3.put("Name",fullname);
                    ref3.updateChildren(uinfo3);

                    Firebase ref4 = new Firebase("https://tunein-633e5.firebaseio.com/").child("ID").child(fullname);
                    Map<String,Object> uinfo4 = new HashMap<String, Object>();
                    uinfo4.put("Id",user.getUid());
                    ref4.updateChildren(uinfo4);

                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);
                } else {
                    progressDialog.hide();
                    FirebaseAuthException e = (FirebaseAuthException )task.getException();
                    Toast.makeText(RegisterActivity.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegisterActivity.this, "Could not register, please try again", Toast.LENGTH_SHORT).show();
                    Log.e("LoginActivity", "Failed Registration", e);
                    return;
                }
            }
        });
    }
}




