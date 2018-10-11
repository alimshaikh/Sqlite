package com.example.sqlite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FirstFragment extends android.app.Fragment {

    View view;
    DatabaseHelper myDb;
    EditText text3,text4;
    TextView text2;
    Button button;
    String text5,text6,id;
    boolean isUpdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_first, container, false);
        text2=(TextView) view.findViewById(R.id.text2);
        text3=(EditText) view.findViewById(R.id.text3);
        text4=(EditText) view.findViewById(R.id.text4);
        button=(Button) view.findViewById(R.id.button2);

        id=(String)myDb.id1;
        text2.setText("Id: " +myDb.id1);
        text3.setText(myDb.title);
        text4.setText(myDb.desc);

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         text5=(text3.getText().toString());
                         text6= (text4.getText().toString());

                        DatabaseHelper  myDb1 = new DatabaseHelper(getActivity());
                        isUpdate = myDb1.updateData(id,text5,text6);


                        if(isUpdate == true)
                            Toast.makeText(getActivity(),"Data Update",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getActivity(),"Data not Updated",Toast.LENGTH_LONG).show();
                    }
                }
        );


        return view;
    }
}
