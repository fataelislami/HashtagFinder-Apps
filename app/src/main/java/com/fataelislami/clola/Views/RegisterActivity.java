package com.fataelislami.clola.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.fataelislami.clola.MainActivity;
import com.fataelislami.clola.Models.RegisterModels;
import com.fataelislami.clola.Presenter.ClientAPI;
import com.fataelislami.clola.Presenter.RestAPI;
import com.fataelislami.clola.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.edtNameRegister)
    EditText edtNameRegister;
    @BindView(R.id.edtEmailRegister) EditText edtEmailRegister;
    @BindView(R.id.edtPasswordRegister) EditText edtPasswordRegister;
    public static final String ROOT_URL = "https://kostlab.id/project/clolandroid/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btnRegister)
    public void klik(){
        String name=edtNameRegister.getText().toString();
        String email=edtEmailRegister.getText().toString();
        String password=edtPasswordRegister.getText().toString();
        register(name,email,password);
//        String emailAdmin="admin@gmail.com";
//        String passwordAdmin="1234";
//        if(email.equals(emailAdmin) && password.equals(passwordAdmin)){
//            Toast.makeText(this,"Berhasil login",Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(this,"Gagal",Toast.LENGTH_LONG).show();
//        }
    }

    private void register(String name, String email, String password) {
        RestAPI service= ClientAPI.get(ROOT_URL).create(RestAPI.class);
        Call<RegisterModels> call=service.register(name,email,password);
        call.enqueue(new Callback<RegisterModels>() {
            @Override
            public void onResponse(Call<RegisterModels> call, Response<RegisterModels> response) {
                if(name.equals("") || email.equals("") || password.equals("")){
                    Toast.makeText(getApplicationContext(),"Harap Isi Form Dengan lengkap",Toast.LENGTH_LONG).show();
                }else{
                    if (response.body().getTotal_result()!=0){
                        Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Pendaftaran Berhasil,Silakan Login",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                    }
                }

            }

            @Override
            public void onFailure(Call<RegisterModels> call, Throwable t) {

            }
        });
    }
}
