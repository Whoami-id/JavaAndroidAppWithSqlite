package com.example.sqldemo;

import androidx.appcompat.app.AppCompatActivity;



import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.shape.CutCornerTreatment;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_add, btn_viewAll;
    EditText et_name, et_age;
    Switch sw_active;
    ListView lv_customerList;
    ArrayAdapter customerArrayAdapter;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.btn_add);
        btn_viewAll = findViewById(R.id.btn_viewAll);
        et_age = findViewById(R.id.et_age);
        et_name = findViewById(R.id.et_name);
        sw_active = findViewById(R.id.sw_active);
        lv_customerList = findViewById(R.id.lv_customerList);

        databaseHelper = new DatabaseHelper(MainActivity.this);
        showCustomerOneListView(databaseHelper);

        btn_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                CustomerModel customerModel;
                try {
                    customerModel = new CustomerModel(-1, et_name.getText().toString(), Integer.parseInt(et_age.getText().toString()), sw_active.isChecked());
                    Toast.makeText(MainActivity.this, customerModel.toString(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Error creating customer", Toast.LENGTH_SHORT).show();
                    customerModel = new CustomerModel(-1, "error", 0, false);
                }

                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                boolean success = databaseHelper.addOne(customerModel);
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                showCustomerOneListView(databaseHelper);
            }
        });

        btn_viewAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                showCustomerOneListView(databaseHelper);
            }
        });

        lv_customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerModel customerModel = (CustomerModel) parent.getItemAtPosition(position);
                databaseHelper.deleteOn(customerModel);
                showCustomerOneListView(databaseHelper);
            }
        });

    }

    private void showCustomerOneListView(DatabaseHelper databaseHelper2) {
        customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1, databaseHelper2.getEveryOne());
        lv_customerList.setAdapter(customerArrayAdapter);
    }




}