package com.example.solicidadao;

import java.math.BigInteger;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @FormUrlEncoded
    @POST("rest/login/")
    Call<ProfileUser> login(@Field("username") String username,
                            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("rest/signup/")
    Call<ProfileUser> signup(@Field("username") String username,
                             @Field("nome") String nome,
                             @Field("email") String email,
                             @Field("password") String password,
                             @Field("password2") String password2
    );

    @GET("rest/solicitacao_educacao/")
    Call<List<SolicitacaoEducacao>> getSolicitacaoEducacao();

    @FormUrlEncoded
    @POST("rest/solicitacao_educacao/")
    Call<SolicitacaoEducacao> insertSolicitacaoEducacao(@Field("nome") int nome,
                                                        @Field("cadastro_pf") String cpf,
                                                        @Field("rg") String rg
                                                        );

    @PUT("rest/solicitacao_educacao/{id}/")
    Call<SolicitacaoEducacao> putSolicitacaoEducacao(@Path("id") int id, @Body SolicitacaoEducacao solicitacaoEducacao);

    @PUT("rest/solicitacao_educacao/{id}/")
    Call<SolicitacaoEducacao> patchSolicitacaoEducacao(@Path("id") int id, @Body SolicitacaoEducacao solicitacaoEducacao);

    @DELETE("rest/solicitacao_educacao/{id}/")
    Call<Void> removeSolicitacaoEducacao(@Path("id") int id);

}
