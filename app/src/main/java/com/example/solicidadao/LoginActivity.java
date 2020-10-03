package com.example.solicidadao;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText editName, editPassword;
    Button btnSignIn, btnRegister;
    CheckBox boxRemember;
    JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        boxRemember = findViewById(R.id.boxRemember);
            editName = findViewById(R.id.editName);
            editPassword = findViewById(R.id.editPassword);
            btnSignIn = findViewById(R.id.btnSignIn);
            btnRegister = findViewById(R.id.btnRegister);

            btnSignIn.setOnClickListener(this);
            btnRegister.setOnClickListener(this);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        boolean check = preferences.getBoolean("check", false);
        if(check){
            String token = preferences.getString("token", null);
            if(token != null){
                startActivity(new Intent(LoginActivity.this, descricaoActivity.class));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignIn:
                LogButtonClick();
                break;
            case R.id.btnRegister:
                RegButtonClick();
                break;
        }
    }


    public void LogButtonClick(){
        if (!IsEmptyEditTextLogin()){
                login();

        }
    }

    public void RegButtonClick() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, 1);
    }

    public void login(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://1tech.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<ProfileUser> call = jsonPlaceHolderApi.login(editName.getText().toString(), editPassword.getText().toString());
        call.enqueue(new Callback<ProfileUser>() {
            @Override
            public void onResponse(Call<ProfileUser> call, Response<ProfileUser> response) {
                    if (response.body() != null) {
                        String token = response.body().getToken();
                        SharedPreferences preferences = getApplicationContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor prefLoginEdit = preferences.edit();
                        if (boxRemember.isChecked()){
                            prefLoginEdit.putBoolean("check", true);
                        }
                        prefLoginEdit.putBoolean("loggedin", true);
                        prefLoginEdit.putString("token", token);
                        prefLoginEdit.commit();
                        Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, descricaoActivity.class));
                    }else {
                        Toast.makeText(getApplicationContext(), "Usuário ou Senha Incorretos.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ProfileUser> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "error :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Boolean IsEmptyEditTextLogin(){



        if(editPassword.getText().toString().isEmpty() || editName.getText().toString().isEmpty()){

            Toast toast = Toast.makeText(getApplicationContext(),"Empty EditText", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();


            return true;
        }else{
            return false;
        }

    }
}
