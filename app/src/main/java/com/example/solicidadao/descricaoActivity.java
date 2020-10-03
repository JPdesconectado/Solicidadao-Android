package com.example.solicidadao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class descricaoActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_header;
    TextView tv_desc;
    TextView tv_cpf;
    TextView tv_rg;
    TextView tv_doc;
    ImageView iv_cpf;
    ImageView iv_rg;
    Button btList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descricao);
        tv_header = findViewById(R.id.tv_header);
        tv_desc = findViewById(R.id.tv_desc);
        tv_cpf = findViewById(R.id.tv_cpf);
        tv_rg = findViewById(R.id.tv_rg);
        tv_doc = findViewById(R.id.tv_doc);
        iv_cpf = findViewById(R.id.iv_cpf);
        iv_rg = findViewById(R.id.iv_rg);
        btList = findViewById(R.id.btList);

        btList.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        startActivity(new Intent(descricaoActivity.this, MainActivity.class));
    }
}
