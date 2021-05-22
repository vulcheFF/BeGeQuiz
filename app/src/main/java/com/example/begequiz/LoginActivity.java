package com.example.begequiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.begequiz.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        auth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Влизане...");

        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,pass;

                        email = binding.emailBox.getText().toString();
                        pass = binding.passwordBox.getText().toString();
                        if(TextUtils.isEmpty(email)){
                            binding.emailBox.setError("Нужен е имейл");
                            return;
                        }
                        if(TextUtils.isEmpty(pass)){
                            binding.passwordBox.setError("Нужна е парола");
                            return;
                        }


                dialog.show();

                        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                dialog.dismiss();
                                if(task.isSuccessful()){

                                   startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                   finish();
                                }
                                else
                                {

                                    Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                                }

                            }
                        });

            }
        });
        binding.createNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
               // finish();
            }
        });
    }
}