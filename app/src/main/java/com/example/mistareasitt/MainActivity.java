package com.example.mistareasitt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mistareasitt.db.ControladorDB;

public class MainActivity extends AppCompatActivity {

    private ControladorDB controladorDB;
    private ArrayAdapter<String> miAdapter;
    private String idUsuario;
    private Bundle datos; //Para recopilar todos los datos de otras activities
    private ListView listViewTareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controladorDB = new ControladorDB(this);
        listViewTareas = (ListView) findViewById(R.id.listaTareas);
        //Recopilamos todos los putExtra con getExtras
        datos = this.getIntent().getExtras();
        //Recogemos el id de usuario con la key del putExtra
        idUsuario = datos.getString("idUsuario");
        //Chequear que llega el idUsuario
        System.out.println("idUsuario: " + idUsuario);
        actualizarUI();
    }
    //Método para crear el mení
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Método para crear tarea en un ítem del menú
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        final EditText cajaTexto = new EditText(this);
        //Diálogo con usuario para insertar tarea
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Nueva tarea")
                .setMessage("Describe tu tarea")
                .setView(cajaTexto)
                .setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tarea = cajaTexto.getText().toString();
                        //Comprueba si no existe la tarea, si no existe la crea y si no te avisa
                        if (!controladorDB.existeTarea(tarea, idUsuario)) {
                            controladorDB.addTarea(tarea, idUsuario);
                            actualizarUI();
                            //Toast crear tarea
                            mostrarToast(getBaseContext(), "Tarea creada", 150);
                        } else {
                            //toast de tarea duplicada
                            Toast.makeText(getBaseContext(), "Tarea duplicada", Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .setNegativeButton("Cancelar",null)
                .create();
        dialog.show();

        return super.onOptionsItemSelected(item);
    }

    //Método que actualiza la interfaz de usuario
    private void actualizarUI(){
        if(controladorDB.obtenerTareas(idUsuario) == null){
            listViewTareas.setAdapter(null);
        }else {
            miAdapter = new ArrayAdapter<>(this, R.layout.item_tarea, R.id.task_title, controladorDB.obtenerTareas(idUsuario));
            listViewTareas.setAdapter(miAdapter);
        }
    }

    public void actualizarTarea(View view) {
        View parentButton = (View) view.getParent();
        //Creamos edittext para poder recoger el texto a editar
        final EditText tareaEdit = new EditText(this);
        //Recogemos tarea antigua en formato String
        String tareaAntigua = ((TextView) parentButton.findViewById(R.id.task_title)).getText().toString();
        //System.out.println(tareaAntigua);
        tareaEdit.setText(tareaAntigua);

        //Diálogo para la función de actualizar tarea
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Editar tarea")
                .setMessage("Describe tu nueva tarea")
                .setView(tareaEdit)
                .setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Pasamos tarea antigua, la nueva y el id hacia el update del controladorDB
                        controladorDB.actualizarTarea(tareaAntigua, tareaEdit.getText().toString(), idUsuario);
                        actualizarUI();
                        //Mensaje de éxito de edición
                        mostrarToast(getBaseContext(), "Tarea editada", 150);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();
        dialog.show();
    }

    //Método que borra las tareas
    public void borrarTarea(View view){
        View parent = (View) view.getParent();
        TextView tareaTextView = (TextView) parent.findViewById(R.id.task_title);
        String tarea = tareaTextView.getText().toString();
        controladorDB.borrarTarea(tarea, idUsuario);
        actualizarUI();

        //toast para acabar tarea
        mostrarToast(getBaseContext(), "Tarea completada", 150);
    }

    //Método para volver a LoginActivity
    public void volverLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    //Método para mostrar toast personalizado en cada función
    private void mostrarToast(Context context, String mensaje, int alturaDesdeBottom) {
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