package com.example.mistareasitt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
    }

    public void crearCuenta(View view){
        Toast toast = Toast.makeText(this,"Cuenta Creada", Toast.LENGTH_LONG);
        toast.show();
    }

    public void login(View view){
        TextInputEditText usuario = (TextInputEditText) findViewById(R.id.cajaUser);
        TextInputEditText pass = (TextInputEditText) findViewById(R.id.cajaPass);

        if(usuario.getText().toString().equalsIgnoreCase("itt") && pass.getText().toString().equalsIgnoreCase("123")){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }else{
            Toast toast = Toast.makeText(this, "Credenciales inválidas", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}