package com.example.mistareasitt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.mistareasitt.db.ControladorDB;
import com.google.android.material.textfield.TextInputEditText;

public class LoginRegisterActivity extends AppCompatActivity {

    private ControladorDB controller;
    private TextInputEditText usuario, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        getSupportActionBar().hide();
    }

    public void registroUsuario(View view){

        controller = new ControladorDB(this);
        usuario = findViewById(R.id.cajaUsuarioRegistro);
        pass = findViewById(R.id.cajaPassRegistro);

        //System.out.println("Consulta: " + usuario.getText().toString() + ", " + pass.getText().toString());*/
        if (usuario.getText().toString().isEmpty()){
            this.usuario.setError("Usuario vacío");
        }else if (pass.getText().toString().isEmpty()){
            this.pass.setError("Password vacía");
        }else if (controller.siExisteUsuario(usuario.getText().toString())) {
            System.out.println("PASA EXISTE USUARIO ");
            Toast toast = Toast.makeText(this, "El usuario existe", Toast.LENGTH_LONG);
            toast.show();
        }else {
            LayoutInflater inflater = getLayoutInflater();
            View view1 = inflater.inflate(R.layout.toast_login_cuentanueva, null);
            Toast toastNuevoUsuario = new Toast(this);
            toastNuevoUsuario.setDuration(Toast.LENGTH_LONG);
            toastNuevoUsuario.setView(view1);
            toastNuevoUsuario.show();
            //Añade usuario a la BBDD
            controller.addUser(usuario.getText().toString(), pass.getText().toString());

            //Retorno de activity al login
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        System.out.println("pasa");


    }

    public void volverLogin(View view){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}