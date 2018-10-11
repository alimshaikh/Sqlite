package com.example.sqlite;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Update extends AppCompatActivity{

    DatabaseHelper myDb;
    public static boolean isUpdate;
    Button button1;
    EditText edit1;
    TextView text1,text2,text3,text4,text5;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);
        myDb = new DatabaseHelper(this);



        button1=(Button)findViewById(R.id.button1);
        edit1=(EditText)findViewById(R.id.edit1);
        text1=(TextView)findViewById(R.id.text1);


        //When Delete Button Click..
        button1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getData(edit1.getText().toString());
                        if(res.getCount()>0) {
                            text1.setText(" Data Found");
                            loadFragment(new FirstFragment());

                        }

                       if (res.getCount()==0){
                            loadFragment(new SecondFragment());
                            text1.setText("No Data Found");
                        }

                    }
                }
        );
    }

    public void loadFragment( android.app.Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.linearlayout, fragment);
        fragmentTransaction.commit(); // save the changes
    }

}
