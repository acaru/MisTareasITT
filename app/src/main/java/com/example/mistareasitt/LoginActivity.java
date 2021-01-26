package com.example.mistareasitt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mistareasitt.db.ControladorDB;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private ControladorDB controller;
    private TextInputEditText usuario, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
    }

    //Método que registra usuarios cambiando a la activity LoginRegisterActivity
    public void registroUsuario(View view){
        startActivity(new Intent(this, LoginRegisterActivity.class));
    }

    //Método que que realiza el login recogiendo usuario y contraseña de las cajas de texto
    public void login(View view){
        controller = new ControladorDB(this);
        usuario = findViewById(R.id.cajaUser);
        pass = findViewById(R.id.cajaPass);

        //Comprobador de si faltan usuario y pass, si no  faltan y averigua que no es null el texto
        //Si es correcto te envía a la MainActivity enviando con putExtra la info del usuario
        if(usuario.getText().toString().isEmpty()){
            usuario.setError("Falta usuario");
        }else if (pass.getText().toString().isEmpty()){
            pass.setError("Falta contraseña");
        }else if (controller.loginUsuario(usuario.getText().toString(), pass.getText().toString()) != null){
            String idUsuario = controller.loginUsuario(usuario.getText().toString(), pass.getText().toString());
            startActivity(new Intent(this, MainActivity.class)
                    .putExtra("idUsuario", idUsuario));
            finish();
        }else{
            Toast toast =  Toast.makeText(this, "Usuario o contraseña incorrecta", Toast.LENGTH_LONG);
        }

        /*Esto sería en caso de solo tener unos pocos usuarios predefinidos en la app
        if(usuario.getText().toString().equalsIgnoreCase("itt") && pass.getText().toString().equalsIgnoreCase("123")){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }else{
            Toast toast = Toast.makeText(this, "Credenciales inválidas", Toast.LENGTH_LONG);
            toast.show();
        }*/
    }
}