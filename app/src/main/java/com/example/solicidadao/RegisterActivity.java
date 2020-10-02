package com.example.solicidadao;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText reg_username;
    EditText reg_password;
    EditText reg_email;
    Button btnSignIn;
    Button btnRegister;
    JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnRegister = findViewById(R.id.btnRegister);

        reg_username = findViewById(R.id.reg_username);
        reg_password = findViewById(R.id.reg_password);
        reg_email = findViewById(R.id.reg_email);

        btnSignIn.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                RegButtonClick();
                break;
            case R.id.btnSignIn:
                LogButtonClick();
                break;
        }
    }

    public void RegButtonClick(){

        String strUsername = reg_username.getText().toString();
        String strPassword = reg_password.getText().toString();
        String strEmail = reg_email.getText().toString();

        ProfileUser user = new ProfileUser(
                strUsername,
                strUsername,
                strPassword,
                strPassword,
                strEmail
        );

        if (!IsEmptyEditTextLogin()){
                RegisterInServer(user);
        }

    }
    public void LogButtonClick() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void RegisterInServer(ProfileUser user){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://1tech.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<ProfileUser> call = jsonPlaceHolderApi.signup(reg_username.getText().toString(), reg_username.getText().toString(),
                reg_email.getText().toString(), reg_password.getText().toString(), reg_password.getText().toString());

        call.enqueue(new Callback<ProfileUser>() {
            @Override
            public void onResponse(Call<ProfileUser> call, Response<ProfileUser> response) {
                    if (response.body() != null) {
                        SharedPreferences preferences = getApplicationContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor prefLoginEdit = preferences.edit();
                        prefLoginEdit.putBoolean("registration", true);
                        prefLoginEdit.commit();
                        Toast.makeText(getApplicationContext(), "Usuário Cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }

                else {
                    Toast.makeText(getApplicationContext(), "Dados Inválidos.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ProfileUser> call, Throwable t) {
                Log.d("fail", "fail");
            }
        });
    }



    private Boolean IsEmptyEditTextLogin(){


        if(reg_password.getText().toString().isEmpty() || reg_username.getText().toString().isEmpty()|| reg_email.getText().toString().isEmpty()){

            Toast toast = Toast.makeText(getApplicationContext(),"Empty EditText", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();


            return true;
        }else{
            return false;
        }

    }












}
