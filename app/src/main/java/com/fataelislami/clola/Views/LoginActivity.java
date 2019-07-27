package com.fataelislami.clola.Views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.fataelislami.clola.MainActivity;
import com.fataelislami.clola.Models.LoginModels;
import com.fataelislami.clola.Presenter.ClientAPI;
import com.fataelislami.clola.Presenter.RestAPI;
import com.fataelislami.clola.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.edtEmailLogin)
    EditText edtEmailLogin;
    @BindView(R.id.edtPasswordLogin) EditText edtPasswordLogin;
    public static final String ROOT_URL = "https://kostlab.id/project/clolandroid/";
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("AuthLogin", 0);
        editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    @OnClick(R.id.btnLogin)
    public void klik(){
        String email=edtEmailLogin.getText().toString();
        String password=edtPasswordLogin.getText().toString();
        ceklogin(email,password);
//        String emailAdmin="admin@gmail.com";
//        String passwordAdmin="1234";
//        if(email.equals(emailAdmin) && password.equals(passwordAdmin)){
//            Toast.makeText(this,"Berhasil login",Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(this,"Gagal",Toast.LENGTH_LONG).show();
//        }
    }

    @OnClick(R.id.txtLoginSkip)
    public void skip(){
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.txtLoginRegister)
    public void register(){
        Intent i=new Intent(this,RegisterActivity.class);
        startActivity(i);
    }

    public void ceklogin(String email,String password){
        RestAPI service= ClientAPI.get(ROOT_URL).create(RestAPI.class);
        Call<LoginModels> call=service.login(email,password);
        call.enqueue(new Callback<LoginModels>() {
            @Override
            public void onResponse(Call<LoginModels> call, Response<LoginModels> response) {
                if (response.body().getTotal_result()!=0){
                    editor.putString("id",response.body().getResults().getId());
                    editor.putString("name",response.body().getResults().getName());
                    editor.putString("email",response.body().getResults().getEmail());
                    editor.putString("status","login");
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"Berhasil Masuk",Toast.LENGTH_LONG).show();
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"Password dan Email salah",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModels> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),"Response : "+t.getMessage(),Toast.LENGTH_LONG).show();
                Log.d("Response",t.getMessage().toString());
            }
        });
    }
}
