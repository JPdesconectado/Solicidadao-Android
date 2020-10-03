package com.example.solicidadao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import java.util.InputMismatchException;

public class InserirSolicitacaoEducacaoActivity extends AppCompatActivity {
    EditText ed_cpf;
    EditText ed_rg;
    Button deletar;
    SolicitacaoEducacao solicitacaoEducacao;

    int position;
    int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_solicitacao_educacao);

        ed_cpf = findViewById(R.id.ed_cpf);
        ed_rg = findViewById(R.id.ed_rg);
        deletar = findViewById(R.id.buttonDelete);

        Bundle bundle = getIntent().getExtras();
        requestCode = bundle.getInt("requestCode");

        if (requestCode==MainActivity.REQUEST_EDITAR_SOLICITACAOEDUCACAO){
            solicitacaoEducacao = (SolicitacaoEducacao) bundle.getSerializable("solicitacaoEducacao");
            position = bundle.getInt("position");
            ed_cpf.setText(solicitacaoEducacao.getCpf());
            ed_rg.setText(solicitacaoEducacao.getRg());

        }else{
            solicitacaoEducacao = new SolicitacaoEducacao();
            deletar.setVisibility(View.INVISIBLE);
            deletar.setClickable(false);
        }
    }

    public void concluir(View view){
        if (isCPF(ed_cpf.getText().toString())){
            Bundle bundle = new Bundle();
            if (requestCode==MainActivity.REQUEST_EDITAR_SOLICITACAOEDUCACAO)
                bundle.putInt("position", position);
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
            int id = preferences.getInt("id", 0);
            solicitacaoEducacao.setNome(id);
            solicitacaoEducacao.setCpf(ed_cpf.getText().toString());
            solicitacaoEducacao.setRg(ed_rg.getText().toString());
            bundle.putSerializable("solicitacaoEducacao", solicitacaoEducacao);
            bundle.putString("operacao", "inserir/editar");
            Intent returnIntent = new Intent();
            returnIntent.putExtras(bundle);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }else{
            ed_cpf.setError("CPF INVÁLIDO.");
        }

    }

    public void deletar(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("operacao","deletar");
        bundle.putSerializable("solicitacaoEducacao", solicitacaoEducacao);
        Intent returnIntent = new Intent();
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public static boolean isCPF(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }

}
