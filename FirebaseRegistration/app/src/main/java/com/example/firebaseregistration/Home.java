package com.example.firebaseregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {
    Button logoutButton;
    TextView userDetail;
    FirebaseAuth auth;
    FirebaseUser loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        findViewById(); //references to ui elements

        if(loggedInUser == null){
            loginActivity(); //if logged out
            finish();
        }
        else{
            userDetail.setText(loggedInUser.getEmail()); //display username
        }
        //onclick listeners
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut(); //logout user
                loginActivity(); //redirect
                finish();
            }
        });
    }

    private void findViewById(){
        logoutButton = findViewById(R.id.logoutButton);
        userDetail = findViewById(R.id.textViewUserDetails);
    }

    private void loginActivity() {
        Intent intent = new Intent(getApplication(),Login.class);
        startActivity(intent);
    }
}