package com.example.solicidadao;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class SolicitacaoEducacaoAdapter extends  RecyclerView.Adapter{
    List<SolicitacaoEducacao> solicitacaoEducacaos;
    AppCompatActivity activity;

    public SolicitacaoEducacaoAdapter(List<SolicitacaoEducacao> solicitacaoEducacaos, AppCompatActivity activity){
        this.solicitacaoEducacaos = solicitacaoEducacaos;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        SolicitacaoEducacaoViewHolder viewHolder = new SolicitacaoEducacaoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position){
        SolicitacaoEducacaoViewHolder viewHolder = (SolicitacaoEducacaoViewHolder) holder;
        viewHolder.ed_cpf.setText("CPF: " + solicitacaoEducacaos.get(position).getCpf());
        viewHolder.ed_rg.setText("RG: " + solicitacaoEducacaos.get(position).getRg());
        viewHolder.ed_data.setText("Data: " + solicitacaoEducacaos.get(position).getData_criacao());
        viewHolder.ed_comentario.setText("Descrição: " + solicitacaoEducacaos.get(position).getComentario());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(activity.getBaseContext(), InserirSolicitacaoEducacaoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("solicitacaoEducacao", solicitacaoEducacaos.get(holder.getAdapterPosition()));
                bundle.putInt("position", holder.getAdapterPosition());
                bundle.putInt("requestCode", MainActivity.REQUEST_EDITAR_SOLICITACAOEDUCACAO);
                intent.putExtras(bundle);
                activity.startActivityForResult(intent, MainActivity.REQUEST_EDITAR_SOLICITACAOEDUCACAO);
            }
        });
    }

    @Override
    public int getItemCount() {
        return solicitacaoEducacaos.size();
    }

    public void insertSolicitacaoEducacao(SolicitacaoEducacao solicitacaoEducacao){
        solicitacaoEducacaos.add(solicitacaoEducacao);
        notifyItemInserted(getItemCount());
    }

    public void update(SolicitacaoEducacao solicitacaoEducacao, int position){
        solicitacaoEducacaos.get(position).setId(solicitacaoEducacao.getId());
        solicitacaoEducacaos.get(position).setNome(solicitacaoEducacao.getNome());
        solicitacaoEducacaos.get(position).setCpf(solicitacaoEducacao.getCpf());
        solicitacaoEducacaos.get(position).setRg(solicitacaoEducacao.getRg());
        notifyItemChanged(position);
    }

    public void remover(int position){
        solicitacaoEducacaos.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, this.getItemCount());
    }


    public static class SolicitacaoEducacaoViewHolder extends RecyclerView.ViewHolder{
        TextView ed_cpf;
        TextView ed_rg;
        TextView ed_data;
        TextView ed_comentario;



        public SolicitacaoEducacaoViewHolder(@NonNull View itemView){
            super(itemView);
            itemView.setTag(this);
            ed_cpf = itemView.findViewById(R.id.ed_cpf);
            ed_rg = itemView.findViewById(R.id.ed_rg);
            ed_data = itemView.findViewById(R.id.ed_data);
            ed_comentario = itemView.findViewById(R.id.ed_comentario);

        }

    }
}
