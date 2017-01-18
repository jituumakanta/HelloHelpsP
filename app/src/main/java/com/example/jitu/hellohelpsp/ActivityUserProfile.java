package com.example.jitu.hellohelpsp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static com.example.jitu.hellohelpsp.R.id.textView;

public class ActivityUserProfile extends AppCompatActivity {
    private TextView textViewUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        textViewUsername = (TextView) findViewById(R.id.textView_Username);

        Intent intent = getIntent();

        textViewUsername.setText("Welcome User " + intent.getStringExtra(ProviderLogin.KEY_USERNAME));
    }
}
