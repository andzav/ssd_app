package com.example.a1.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a1.myapplication.apihandlers.App;
import com.example.a1.myapplication.models.loginModel;
import com.example.a1.myapplication.models.userModel;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);
        SharedPreferences sp1=this.getSharedPreferences("Login", MODE_PRIVATE);

        String SID = sp1.getString("SID", null);
        String email = sp1.getString("email", null);
        String location = sp1.getString("location", null);
        if(SID!=null&&email!=null&&location!=null){
            Intent intent = new Intent(MainActivity.this, OrderActivity.class);
            intent.putExtra("SID", SID);
            intent.putExtra("email", email);
            intent.putExtra("location", location);
            startActivity(intent);
        }
    }

    public void login(View view) {
        final Button btn = findViewById(R.id.button);
        btn.setEnabled(false);
        final EditText emailField = findViewById(R.id.emailField);
        final EditText passField   = findViewById(R.id.passField);
        loginModel req = new loginModel();
        req.setEmail(emailField.getText().toString());
        req.setPassword(passField.getText().toString());

        App.getApi().loginUser(req).enqueue(new Callback<userModel>() {
            @Override
            public void onResponse(@NonNull Call<userModel> call, @NonNull Response<userModel> response) {
                if(response.code()==200){
                    if(response.body()!=null&&response.body().getSID()!=null&& !Objects.equals(response.body().getPermission(), "default")) {

                        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
                        SharedPreferences.Editor Ed = sp.edit();
                        Ed.putString("SID", response.body().getSID());
                        Ed.putString("email", emailField.getText().toString());
                        Ed.putString("location", response.body().getLocation());
                        Ed.apply();

                        Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                        intent.putExtra("SID", response.body().getSID());
                        intent.putExtra("email", emailField.getText().toString());
                        intent.putExtra("location", response.body().getLocation());
                        startActivity(intent);
                        btn.setEnabled(true);
                    }else{
                        Toast.makeText(getApplicationContext(), "Don't have permission for this tool", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Authorization failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<userModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
