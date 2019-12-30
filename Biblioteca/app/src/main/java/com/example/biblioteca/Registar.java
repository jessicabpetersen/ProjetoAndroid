package com.example.biblioteca;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.Menu;

public class Registar extends AppCompatActivity {

    private Button bt_registar;
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private EditText regNome,regEmail,regPassword,regConfirmarPassword;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu3, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuThree:
                startActivity(new Intent(Registar.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registar);

        openHelper = new Database(this);

        bt_registar = findViewById(R.id.bt_registar);
        regNome = findViewById(R.id.nome);
        regEmail = findViewById(R.id.email);
        regPassword = findViewById(R.id.pass);
        regConfirmarPassword = findViewById(R.id.confirm_pass);

        bt_registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = openHelper.getWritableDatabase();
                String fNome = regNome.getText().toString().trim();
                String fEmail = regEmail.getText().toString().trim();
                String fPassword = regPassword.getText().toString().trim();
                String fConfirmarPassword = regConfirmarPassword.getText().toString().trim();
                if (fNome.isEmpty() || fEmail.isEmpty() || fPassword.isEmpty() || fConfirmarPassword.isEmpty()) {
                    Toast.makeText(Registar.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    insertData(fNome,fEmail,fPassword,fConfirmarPassword);
                    Toast.makeText(Registar.this, "Registo efetuado com sucesso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Registar.this, MainActivity.class));
                }
            }
        });

    }
    public void insertData(String fNome,String fEmail,String fPassword,String fConfirmarPassword){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.COL_NOME,fNome);
        contentValues.put(Database.COL_EMAIL,fEmail);
        contentValues.put(Database.COL_PASSWORD,fPassword);
        contentValues.put(Database.COL_CPASSWORD,fConfirmarPassword);

        db.insert(Database.TABLE_REGISTO,null,contentValues);
    }


}
