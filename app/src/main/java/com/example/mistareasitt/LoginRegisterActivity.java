package com.example.mistareasitt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
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

    //Método que registra el usuario
    public void registroUsuario(View view){

        controller = new ControladorDB(this);
        usuario = findViewById(R.id.cajaUsuarioRegistro);
        pass = findViewById(R.id.cajaPassRegistro);

        //System.out.println("Consulta: " + usuario.getText().toString() + ", " + pass.getText().toString());*/

        //Comprobador de campos vacíos y si existe usuario. Si no existe
        if (usuario.getText().toString().isEmpty()){
            this.usuario.setError("Usuario vacío");
        }else if (pass.getText().toString().isEmpty()){
            this.pass.setError("Password vacía");
        }else if (controller.existeUsuario(usuario.getText().toString())) {
            Toast toast = Toast.makeText(this, "El usuario existe", Toast.LENGTH_LONG);
            toast.show();
        }else {

            //Añade usuario a la BBDD
            controller.addUser(usuario.getText().toString(), pass.getText().toString());

            //Mostrar toast
            mostrarToastRegister(getBaseContext(), "Cuenta creada", 150);

            //Retorno de activity al login
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    //Método que retorna al Login
    public void volverLogin(View view){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    //Método que muestra el toast personalizado
    private void mostrarToastRegister(Context context, String mensaje, int alturaDesdeBottom) {
        LayoutInflater inflater = getLayoutInflater();
        View view2 = inflater.inflate(R.layout.toast_personalizado, null);
        TextView texto = view2.findViewById(R.id.txtMensaje);
        Toast toastPersonalizado = new Toast (context);
        //toastPersonalizado.setDuration(Toast.LENGTH_LONG);
        texto.setText(mensaje);
        toastPersonalizado.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM, 0, alturaDesdeBottom);
        toastPersonalizado.setView(view2);
        toastPersonalizado.show();
    }

}