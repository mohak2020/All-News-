package com.example.allnews;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class WelcomeActivity extends AppCompatActivity {

    private static final String TAG = "WelcomeActivity";

    public static final int RC_SIGN_IN = 1;


    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 //      setContentView(R.layout.activity_welcome);
//
//        Button button = (Button)findViewById(R.id.launch_app);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
//                startActivity(intent);
//            }
//        });
//
       mFirebaseAuth = FirebaseAuth.getInstance();


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {



            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                Log.d(TAG, "onAuthStateChanged: ");

                FirebaseUser user = firebaseAuth.getCurrentUser();
//               Log.d(TAG, "onAuthStateChanged: "+user.getEmail());

                if (user != null) {
                    //Log.d(TAG, "onAuthStateChanged: "+user.getEmail());
                    setContentView(R.layout.activity_welcome);

                    Button launchButton = (Button)findViewById(R.id.launch_app);


                    launchButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    });

                    Button signoutButton =(Button)findViewById(R.id.sign_out);

                    signoutButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AuthUI.getInstance().signOut(getApplicationContext());
                        }
                    });

                    mFirebaseAuth = FirebaseAuth.getInstance();

                    Toast.makeText(getApplicationContext(), "You're now signed in. Welcome to News App.", Toast.LENGTH_SHORT).show();

                } else {


                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(

                                            new AuthUI.IdpConfig.EmailBuilder().build()
                                    ))
                                    .build(),
                            RC_SIGN_IN);

                }

                // RC_SIGN_IN

            }
        };




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Sign-in succeeded, set up the UI
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // Sign in was canceled by the user, finish the activity
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }


}
