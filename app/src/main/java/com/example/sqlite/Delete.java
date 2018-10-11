package com.example.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


public class Delete extends AppCompatActivity{

    DatabaseHelper myDb;
    Button button;
    EditText edit1;
    TextView text1;
    Button showPopupBtn, closePopupBtn;
    PopupWindow popupWindow;
    LinearLayout linearLayout1;
    Cursor res;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete);
        linearLayout1 = (LinearLayout) findViewById(R.id.layout_delete);
        button=(Button)findViewById(R.id.delete);

        myDb = new DatabaseHelper(this);

        edit1=(EditText)findViewById(R.id.edit1);
        text1=(TextView)findViewById(R.id.text1);

        edit1.setHint("Enter id which you want to Delete");

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        res= myDb.getData(edit1.getText().toString());
                        if(res.getCount()>0) {

                            LayoutInflater layoutInflater = (LayoutInflater) Delete.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View customView = layoutInflater.inflate(R.layout.popup, null);
                            closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);

                            //instantiate popup window
                            popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            //display the popup window
                            popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);




                            //close the popup window on button click
                            closePopupBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Integer deletedRows = myDb.deleteData(edit1.getText().toString());
                                    if (deletedRows > 0) {
                                        text1.setText("Data Deleted");
                                        //Toast.makeText(getApplicationContext(), "Data Deleted", Toast.LENGTH_LONG).show();
                                        //text1.setText("Data Deleted");

                                    }
                                    popupWindow.dismiss();
                                }
                            });
                        }

                        else
                            text1.setText("Data Not Found");



                        /*Integer deletedRows = myDb.deleteData(edit1.getText().toString());
                        if (deletedRows > 0) {
                            //Toast.makeText(getApplicationContext(), "Data Deleted", Toast.LENGTH_LONG).show();
                            text1.setText("Data Deleted");

                        }
                        else
                            text1.setText("No Data Found");*/
                            //Toast.makeText(getApplicationContext() ,"No Data Found", Toast.LENGTH_LONG).show();



                        // Show all data
                        // showMessage("Data",buffer.toString());
                    }
                }
        );
    }

}
