package com.example.jitu.hellohelpsp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GoBusiness extends AppCompatActivity implements View.OnClickListener {
    Button buttonRegister;
    Button buttonLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_business);
        buttonRegister=(Button)findViewById(R.id.button_Register);
        buttonLogin=(Button)findViewById(R.id.button_Login);
        buttonRegister.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            Intent i1=new Intent(getApplicationContext(),ProviderRegister.class);
            startActivity(i1);
        }
        if(v == buttonLogin){
            Intent i2=new Intent(getApplicationContext(),ProviderLogin.class);
            startActivity(i2);
        }
    }
}
