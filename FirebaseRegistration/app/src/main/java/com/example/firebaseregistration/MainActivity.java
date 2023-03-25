package com.example.firebaseregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String TAG = "FIREBASE";
    Button signUp;
    EditText fullName, email, birthdate, mobile, password;
    RadioGroup radioGroup;
    RadioButton female, male;
    String gender; //store selected gender
    FirebaseFirestore database;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseFirestore.getInstance(); //initialize cloud firestore
        mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth
        findViewById(); //references to ui elements
        //radio buttons
        female = findViewById(R.id.radio_female);
        male = findViewById(R.id.radio_male);
        radioGroup = findViewById(R.id.radio_group_register_gender);

        //click signup
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //variable to hold user inputs
                String fullNameInput = fullName.getText().toString();
                String emailInput = email.getText().toString();
                String birthdateInput = birthdate.getText().toString();
                String mobileInput = mobile.getText().toString();
                String passwordInput = password.getText().toString();

                if (male.isChecked()) {
                    String maleChecked = male.getText().toString();
                }else{
                    String femaleChecked = female.getText().toString();
                }

                //validations (edit text)
                if(!fullNameInput.isEmpty() && !emailInput.isEmpty() && !birthdateInput.isEmpty() && !mobileInput.isEmpty() && !passwordInput.isEmpty()){
                    createUser(emailInput, passwordInput); //create account using auth
                    registerUser(fullNameInput, emailInput, birthdateInput, mobileInput, passwordInput, gender); //method call
                }else{
                    validations(fullNameInput,emailInput,birthdateInput,mobileInput,passwordInput);
                }

                //validations (radio button)
                if(radioGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getApplicationContext(),"Select a Gender", Toast.LENGTH_SHORT).show();
                }else if(radioGroup.getCheckedRadioButtonId() == female.getId()){

                }
                else if(radioGroup.getCheckedRadioButtonId() == male.getId()){

                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == -1){
                        //no gender selected
                }else{
                    if(checkedId == female.getId()){
                        //gender = "female";
                    }else if(checkedId == male.getId()){
                        //gender = "male";
                    }
                }
            }
        });
    }
    //for sign in ,authentication
    private void createUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Successfully Created!",
                            Toast.LENGTH_SHORT).show();
                    //loginActivity(); //proceed to login
                    finish(); //close registration activity
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUser(String fullname, String email, String birthdate, String mobile, String password, String gender){
        // Create a new user
        Map<String, Object> user = new HashMap<>();
        user.put("fullname", fullname);
        user.put("email", email);
        user.put("birthdate", birthdate);
        user.put("mobile", mobile);
        user.put("password", password);
        user.put("gender", gender);

        // Add a new document with a generated ID
        database.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    private void validations(String fullNameInput, String emailInput, String birthdateInput, String mobileInput, String passwordInput){
        //edit text validations
        if(fullNameInput.isEmpty()){
            fullName.requestFocus(); //cursor here
            fullName.setError("Full Name required!");
        } else if(emailInput.isEmpty()){
            email.requestFocus(); //cursor here
            email.setError("Email Required!");
        } else if(birthdateInput.isEmpty()){
            birthdate.requestFocus(); //cursor here
            birthdate.setError("Birthdate Required!");
        } else if(mobileInput.isEmpty()){
            mobile.requestFocus(); //cursor here
            mobile.setError("Mobile no. required");
        } else if(passwordInput.isEmpty()){
            password.requestFocus(); //cursor here
            password.setError("Password required!");
        }
    }

    private void findViewById(){
        signUp = findViewById(R.id.buttonSignup);
        fullName = findViewById(R.id.fullNameTxt);
        email = findViewById(R.id.emailTxt);
        birthdate = findViewById(R.id.birthdateTxt);
        mobile = findViewById(R.id.mobileTxt);
        password = findViewById(R.id.passwordTxt);

    }


    @Override
    protected void onStart(){
        super.onStart();
        Log.i(TAG, "onStart()");

    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }
}

