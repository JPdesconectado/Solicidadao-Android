package com.example.solicidadao;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_EDITAR_SOLICITACAOEDUCACAO = 2;
    private static final int REQUEST_INSERIR_SOLICITACAOEDUCACAO = 1;
    RecyclerView recyclerView;
    SolicitacaoEducacaoAdapter adapter;
    JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new SolicitacaoEducacaoAdapter(new ArrayList<SolicitacaoEducacao>(), this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://1tech.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        getSolicitacaoEducacao();
    }

    public void getSolicitacaoEducacao(){
        Call<List<SolicitacaoEducacao>> call = jsonPlaceHolderApi.getSolicitacaoEducacao();
        call.enqueue(new Callback<List<SolicitacaoEducacao>> () {
            @Override
            public void onResponse(Call<List<SolicitacaoEducacao>>  call, Response<List<SolicitacaoEducacao>>  response) {
                if (response.body() != null){
                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor prefLoginEdit = preferences.edit();
                    List<SolicitacaoEducacao> solicitacaoEducacaos = response.body();
                    int id = response.body().get(0).getNome();
                    prefLoginEdit.putInt("id", id);
                    prefLoginEdit.commit();
                    for (SolicitacaoEducacao solicitacaoEducacao : solicitacaoEducacaos)
                        adapter.insertSolicitacaoEducacao(solicitacaoEducacao);

                }
            }

            @Override
            public void onFailure(Call<List<SolicitacaoEducacao>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void insertSolicitacaoEducacao(SolicitacaoEducacao solicitacaoEducacao){
        Call<SolicitacaoEducacao> call = jsonPlaceHolderApi.insertSolicitacaoEducacao(
                solicitacaoEducacao.getNome(),
                solicitacaoEducacao.getCpf(),
                solicitacaoEducacao.getRg()
                );
        call.enqueue(new Callback<SolicitacaoEducacao>(){
            @Override
            public void onResponse(Call<SolicitacaoEducacao> call, Response<SolicitacaoEducacao> response) {
                if(response.body() == null){
                    Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),response.code()+"",Toast.LENGTH_LONG).show();
                    SolicitacaoEducacao eResponse = response.body();
                    adapter.insertSolicitacaoEducacao(eResponse);
                }
            }

            @Override
            public void onFailure(Call<SolicitacaoEducacao> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateSolicitacaoEducacao(final SolicitacaoEducacao solicitacaoEducacao, final int position){
        Call<SolicitacaoEducacao> call = jsonPlaceHolderApi.putSolicitacaoEducacao(solicitacaoEducacao.getId(), solicitacaoEducacao);
        call.enqueue(new Callback<SolicitacaoEducacao>() {
            @Override
            public void onResponse(Call<SolicitacaoEducacao> call, Response<SolicitacaoEducacao> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),response.code()+"",Toast.LENGTH_LONG).show();
                    SolicitacaoEducacao tResponse = response.body();
                    adapter.update(solicitacaoEducacao, position);
                }
            }

            @Override
            public void onFailure(Call<SolicitacaoEducacao> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void removeSolicitacaoEducacao(SolicitacaoEducacao solicitacaoEducacao, final int position){
        Call<Void> call = jsonPlaceHolderApi.removeSolicitacaoEducacao(solicitacaoEducacao.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),response.code(),Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),response.code()+"",Toast.LENGTH_LONG).show();
                    adapter.remover(position);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.inserirMenu){
            Bundle bundle = new Bundle();
            bundle.putInt("requestCode",REQUEST_INSERIR_SOLICITACAOEDUCACAO);
            Intent intent = new Intent(this, InserirSolicitacaoEducacaoActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_INSERIR_SOLICITACAOEDUCACAO);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_INSERIR_SOLICITACAOEDUCACAO){
            if (resultCode == Activity.RESULT_OK){
                Bundle bundle = data.getExtras();
                SolicitacaoEducacao solicitacaoEducacao = (SolicitacaoEducacao) bundle.getSerializable("solicitacaoEducacao");
                insertSolicitacaoEducacao(solicitacaoEducacao);
            }
        }
        if (requestCode == REQUEST_EDITAR_SOLICITACAOEDUCACAO){
            if (resultCode == Activity.RESULT_OK){
                Bundle bundle = data.getExtras();
                int position = bundle.getInt("position");
                SolicitacaoEducacao solicitacaoEducacao = (SolicitacaoEducacao) bundle.getSerializable("solicitacaoEducacao");
                String op = bundle.getString("operacao");
                if(op.equals("deletar")){
                    removeSolicitacaoEducacao(solicitacaoEducacao, position);
                }else {
                    solicitacaoEducacao = (SolicitacaoEducacao) bundle.getSerializable("solicitacaoEducacao");
                    updateSolicitacaoEducacao(solicitacaoEducacao, position);
                }
            }
        }

}
}
