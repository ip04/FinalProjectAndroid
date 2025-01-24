package com.example.gaminghubapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gaminghubapp.R;
import com.example.gaminghubapp.domain.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginRegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText userEdt, passEdt;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
    }

    public void login(View view){
        String email = ((EditText) findViewById(R.id.et_login_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.et_login_password)).getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginRegisterActivity.this, "Login OK", Toast.LENGTH_LONG).show();
                            System.out.println("Login OK");
                            Intent intent = new Intent(LoginRegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginRegisterActivity.this, "Login Failed", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }


    public void register(View view){
        String email = ((EditText) findViewById(R.id.et_register_email)).getText().toString();
        String username = ((EditText) findViewById(R.id.et_register_username)).getText().toString();
        String password = ((EditText) findViewById(R.id.et_register_password)).getText().toString();
        String phone = ((EditText) findViewById(R.id.et_register_phone)).getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){
                        writeData(email, username, phone);
                        Toast.makeText(LoginRegisterActivity.this, "Register OK", Toast.LENGTH_LONG).show();
                        System.out.println("Register OK");
                        Intent intent = new Intent(LoginRegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginRegisterActivity.this, "Register Failed", Toast.LENGTH_LONG).show();
                        System.out.println("Register Failed");
                    }
                });
    }

    private void writeData(String email, String username, String phone) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://secondlesson-abf92-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("users").child(currentUser.getUid());
        User user = new User(email, username, phone);
        myRef.setValue(user);
    }
}