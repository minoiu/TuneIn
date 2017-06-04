package com.qmul.nminoiu.tunein;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signup_button;
    private EditText first_name;
    private EditText last_name;
    private AutoCompleteTextView email_register;
    private EditText password;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        signup_button = (Button) findViewById(R.id.signup_button);
        first_name = (EditText) findViewById(R.id.first_name);
        last_name = (EditText) findViewById(R.id.last_name);
        email_register = (AutoCompleteTextView) findViewById(R.id.email_register);
        password = (EditText) findViewById(R.id.password);

        signup_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == signup_button) {
            registerUser();
        }
    }


    private void registerUser() {
        String email = email_register.getText().toString().trim();
        String passwor = password.getText().toString().trim();
        String firstname = first_name.getText().toString().trim();
        String lastname = last_name.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            //stopping function
            return;
        }

        if(TextUtils.isEmpty(passwor)) {
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            //stopping function
            return;
        }

        if(TextUtils.isEmpty(firstname)) {
            //firstname is empty
            Toast.makeText(this, "Please enter your first name", Toast.LENGTH_SHORT).show();
            //stopping function
            return;
        }

        if(TextUtils.isEmpty(lastname)) {
            //surname is empty
            Toast.makeText(this, "Please enter your last name", Toast.LENGTH_SHORT).show();
            //stopping function
            return;
        }

        //if validations are ok a progress bar is shown
        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,passwor).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                  if(task.isSuccessful()){
                      //user is registered
                      Toast.makeText(RegisterActivity.this, "Registration successfull", Toast.LENGTH_SHORT).show();
                      //progressDialog.hide();
                  }
                  else{
                      Toast.makeText(RegisterActivity.this, "Could not register, please try again", Toast.LENGTH_SHORT).show();

                  }
            }
        });


    }

}

