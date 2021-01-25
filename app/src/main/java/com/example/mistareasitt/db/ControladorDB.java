package com.example.mistareasitt.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;

public class ControladorDB extends SQLiteOpenHelper {

    public ControladorDB(@Nullable Context context) {
        super(context, "com.example.mistareasitt.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USUARIOS (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NOMUSUARIO TEXT NOT NULL, " +
                "PASS TEXT NOT NULL)");

        db.execSQL("CREATE TABLE TAREAS (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NOMBRE TEXT NOT NULL, " +
                "IDUSUARIO INTEGER, " +
                "FOREIGN KEY(IDUSUARIO) REFERENCES USUARIOS(ID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addUser(String usuario, String pass) {
        ContentValues registro = new ContentValues();
        registro.put("NOMUSUARIO", usuario);
        registro.put("PASS", pass);

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert("USUARIOS", null, registro);
        db.close();
    }

    public boolean siExisteUsuario (String idUsuario) {

        SQLiteDatabase db = this.getReadableDatabase();

        String[] args = new String[] {idUsuario};

        System.out.println("Args: " + args[0]);

        Cursor cursor = db.rawQuery("SELECT * FROM USUARIOS " +
                "WHERE NOMUSUARIO=?", args);

        if (cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    public String loginUsuario(String usuario, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] args = new String[] {usuario, pass};

        Cursor cursor = db.rawQuery("SELECT ID FROM USUARIOS " +
                "WHERE NOMUSUARIO=? AND PASS=?", args);

        cursor.moveToFirst();

        return cursor.getString(0);
    }

    public void addTarea(String tarea, String idUsuario){

        ContentValues registro = new ContentValues();
        registro.put("NOMBRE", tarea);
        registro.put("IDUSUARIO", idUsuario);

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert("TAREAS", null, registro);
        //db.execSQL("INSERT INTO TAREAS VALUES (null, ' + tarea + ');");
        db.close();
    }

    public String[] obtenerTareas(String idUsuario) {
        System.out.println("Pasa controlador obtenerTareas");

        SQLiteDatabase db = this.getReadableDatabase();

        String [] args = new String[]{String.valueOf(idUsuario)};

        Cursor cursor = db.rawQuery("SELECT * FROM TAREAS WHERE IDUSUARIO=?", args);
        //db.execSQL("INSERT INTO TAREAS VALUES (null, ' + tarea + ');");
        int regs = cursor.getCount();

        System.out.println("registros: " + regs);

        if (regs == 0) {
            db.close();
            return null;
        } else {
            String[] tareas = new String[regs];
            cursor.moveToFirst();
            for (int i = 0; i < regs; i++) {
                tareas[i] = cursor.getString(1);
                cursor.moveToNext();
            }
            db.close();
            return tareas;
        }
    }

    public int numeroRegistros(){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM TAREAS", null);
            return cursor.getCount();
    }

    public void borrarTarea(String tarea, String idUsuario){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("TAREAS", "NOMBRE=? AND IDUSUARIO=?", new String [] {tarea, idUsuario});
        db.close();
    }

    public boolean existeTarea (String task, String userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] args = new String[] {task, userId};

        Cursor cursor = db.rawQuery("SELECT * FROM TAREAS " + "WHERE NOMBRE=? AND IDUSUARIO=?", args);

        if (cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }
}
