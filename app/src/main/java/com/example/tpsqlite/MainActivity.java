package com.example.tpsqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabsaHelper dataBase;
    EditText adresse,designation,description, txtId;
    Button btnAjouter,btnAfficher, btnModifier, btnSupprimer,btnRechercher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBase=new DatabsaHelper(this);

        adresse=(EditText) findViewById(R.id.txtAdresse);
        designation= (EditText) findViewById(R.id.txtDesignation);
        description= (EditText) findViewById(R.id.txtDescription);
        txtId=(EditText) findViewById(R.id.txtId);
        btnAjouter=(Button) findViewById(R.id.btnAjouter);
        btnAfficher=(Button) findViewById(R.id.btnAfficher);
        btnModifier=(Button) findViewById(R.id.btnModifier);
        btnSupprimer=(Button) findViewById(R.id.btnSupprimer);
        btnRechercher=(Button) findViewById(R.id.btnRechercher);

        addData();

        viewAll();

        updateData();

        delete();

        viewById();
    }
    public void viewById()
    {
        btnRechercher.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res= dataBase.getById(txtId.getText().toString());
                        if (res.getCount()==0)
                        {
                            showMessage("Error", "No DATA found");
                            return;
                        }
                        StringBuffer buffer=new StringBuffer();
                        while (res.moveToNext())
                        {
                            buffer.append("\n\n"+"ID: "+res.getString(0)+"\n");
                            buffer.append("Designation: "+res.getString(1)+"\n");
                            buffer.append("Adresse: "+res.getString(2)+"\n");
                            buffer.append("Description: "+res.getString(3)+"\n");
                        }
                        //show data
                        showMessage("DATA",buffer.toString());

                    }
                }
        );
    }
    public void addData()
    {
        btnAjouter.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      boolean isInserted=  dataBase.insertData(
                                designation.getText().toString(),
                                adresse.getText().toString(),
                                description.getText().toString());
                      if (isInserted==true)
                          Toast.makeText(MainActivity.this,
                                  "Data inserted succefully",Toast.LENGTH_LONG).show();
                      else
                          Toast.makeText(MainActivity.this,
                                  "Data not inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void viewAll()
    {
        btnAfficher.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      Cursor res= dataBase.getALlData();
                      if (res.getCount()==0)
                      {
                          showMessage("Error", "Nothing DATA found");
                          return;
                      }
                      StringBuffer buffer=new StringBuffer();
                      while (res.moveToNext())
                      {
                          buffer.append("\n\n"+"ID: "+res.getString(0)+"\n");
                          buffer.append("Designation: "+res.getString(1)+"\n");
                          buffer.append("Adresse: "+res.getString(2)+"\n");
                          buffer.append("Description: "+res.getString(3)+"\n");
                      }
                      //show data
                        showMessage("DATA",buffer.toString());
                    }
                }
        );
    }


    public void updateData()
    {
        btnModifier.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdated= dataBase.updateData(txtId.getText().toString(),
                                designation.getText().toString(),
                                adresse.getText().toString(),
                                description.getText().toString());
                        if (isUpdated==true)
                            Toast.makeText(MainActivity.this,
                                    "Data updated succefully",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,
                                    "Data not updated",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void delete()
    {
        btnSupprimer.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deletedRows= dataBase.deleteData(txtId.getText().toString());
                        if(deletedRows>0)
                            Toast.makeText(MainActivity.this,
                                    "Deleted succefully",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,
                                    "Not deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }



}