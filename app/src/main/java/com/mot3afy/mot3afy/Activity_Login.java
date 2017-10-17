package com.mot3afy.mot3afy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Activity_Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__login);
    }

    public void main (View v ){

        startActivity(new Intent(Activity_Login.this,Activity_Main.class));


    }
}
