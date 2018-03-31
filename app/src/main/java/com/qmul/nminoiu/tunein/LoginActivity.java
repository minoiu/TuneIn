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
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    //makeprivate static final  R = ;

    // DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    //DatabaseReference mConditionRef = mRootRef.child("condition");


    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
//    makeprivate static final String[] DUMMY_CREDENTIALS = new String[]{
//            "foo@example.com:hello", "bar@example.com:world"
//    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //makeprivate UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private FirebaseAuth firebaseAuth;
    private Button forgotPassword;
    private FirebaseUser user;
    /**
     * The constant loggedEmail.
     */
//TextView mConditionTextView;
    public static String loggedEmail;
    private DatabaseReference mDatabase;
    private String ID;
    private Handler mHandler = new Handler();
    /**
     * The constant mediaPlayer.
     */
    public static MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);

        OneSignal.startInit(this).init();
        //String loggedEmail = firebaseAuth.getCurrentUser().getEmail();

        //mConditionTextView = (TextView)findViewById(R.id.textViewCondition);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mEmailView.setText("mar@zar.com");

        mediaPlayer = new MediaPlayer();
        mediaPlayer.reset();


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Emails");


        //setting notification tags for current user
        firebaseAuth = FirebaseAuth.getInstance();


        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setText("123456");
//        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == R.id.login || id == EditorInfo.IME_NULL) {
//                    attemptLogin();
//                    return true;
//                }
//                return false;
//            }
//        });

//        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
//        mEmailSignInButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                attemptLogin();
//            }
//        });

//        mLoginFormView = findViewById(R.id.login_form);
//        mProgressView = findViewById(R.id.login_progress);
//    }

//    @Override
//    public void onStart(){
//        super.onStart();
//
////        mConditionRef.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot dataSnapshot) {
////                String text = dataSnapshot.getValue(String.class);
////                mConditionTextView.setText(text);
////            }
//
//            @Override
//           // public void onCancelled(DatabaseError databaseError) {
//
//
//        }}
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

                    String email = mEmailView.getText().toString().trim();
                    String username = mEmailView.getText().toString().trim();
                    int index = username.indexOf("@");
                    username = username.substring(0,index);

                    Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                    //Firebase userRef = ref.child("ActiveUsers").child(email);
                    //userRef.setValue(ref.child("ActiveUsers").child(email).toString());


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






