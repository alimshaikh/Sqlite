package com.example.sqlite;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    static int i = 0;
    int j=0;
    EditText Title, Desc, Id;
    Button btnAddData;
    Button btnviewAll;
    Button btnDelete;
    Button btnviewUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        Title = (EditText) findViewById(R.id.title);
        Desc = (EditText) findViewById(R.id.desc);
        Id = (EditText) findViewById(R.id.id);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnviewAll = (Button) findViewById(R.id.button_viewAll);
        btnviewUpdate = (Button) findViewById(R.id.button_update);
        btnDelete = (Button) findViewById(R.id.button_delete);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }

    public void Required() {
        if (Title.getText().toString().length() == 0) {
            i++;
            Title.setError("Title is required!");

        }

        if (Desc.getText().toString().length() == 0) {
            i++;
            Desc.setError("Description is required!");
        }

        if (Id.getText().toString().length() == 0) {
            i++;
            Id.setError("Unique Id is required!");
        }


    }

    //TO Delete Data..
    public void DeleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent j = new Intent(MainActivity.this,Delete.class);
                        startActivity(j);
                       /* Integer deletedRows = myDb.deleteData(Id.getText().toString());
                        if (deletedRows > 0)
                            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();*/
                    }
                }
        );
    }

    //TO Update Data..
    public void UpdateData() {
          btnviewUpdate.setOnClickListener(
                  new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {

                         Intent i = new Intent(MainActivity.this,Update.class);
                         startActivity(i);
                          /*boolean isUpdate = myDb.updateData(Id.getText().toString(),
                                Title.getText().toString(),
                                Desc.getText().toString());
                        if(isUpdate == true)
                            Toast.makeText(MainActivity.this,"Data Update",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();*/
                    }
                }
        );
    }

    //To Add Data..
    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Required();

                        if (i == 0) {
                            boolean isInserted = myDb.insertData(Id.getText().toString(),
                                    Title.getText().toString(),
                                    Desc.getText().toString());

                            if (isInserted == true) {
                                Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                               // Log.d("Data Added: ", "ID: " + Id.getText());
                            }

                            else
                                Toast.makeText(MainActivity.this, "Duplicate Id:  "+myDb.ID +"\nPlease Enter Another Id", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(MainActivity.this, "Please insert input", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(MainActivity.this, "i= " + i, Toast.LENGTH_LONG).show();
                            i = 0;
                        }
                    }
                }
        );
    }

    //To Show All Data..
    public void viewAll() {
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0) {
                            // show message
                            showMessage("Error", "No Data Found");
                            //return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append(" Id :" + res.getString(0) + "\n");
                            buffer.append("Title :" + res.getString(1) + "\n");
                            buffer.append("Description :" + res.getString(2) + "\n");
                            buffer.append("Create Date :" + res.getString(3) + "\n");
                            buffer.append("Update Date :" + res.getString(4) + "\n\n");
                        }

                        // Show all data
                        showMessage(
                                "Data", buffer.toString());
                    }
                }
        );
    }

    //To  Show Data in Dialog..
    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}


