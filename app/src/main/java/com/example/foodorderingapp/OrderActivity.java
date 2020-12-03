package com.example.foodorderingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.foodorderingapp.Adapters.OrderAdapter;
import com.example.foodorderingapp.Models.OrderModel;
import com.example.foodorderingapp.databinding.ActivityOrderBinding;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {


    ActivityOrderBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Binding is used instead of findVIewById
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        // Custom Toolbar
        Toolbar customToolbar = binding.orderCustomToolbar.customToolbar;
        // OR
        // Toolbar customToolbar = findViewById(R.id.mainCustomToolbar);
        setSupportActionBar(customToolbar);
        // OR
        // setSupportActionBar(binding.mainCustomToolbar.customToolbar);


        // Setting title of custom toolbar
        getSupportActionBar().setTitle("My Orders");



        /*    Code for "Back Button (<-)"    */
        // Setting back button to move to previous activity
        // Here back button is used to move to order activity
        customToolbar.setNavigationIcon(R.drawable.back_icon);

        // This function shows what will happen when we click on back button
        // Here we are just moving to the previous activity that is order activity whenever back button is pressed
        customToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        // Following code is for getting data from the database
        // Creating database helper object
        DBHelper helper = new DBHelper(this);



        // Following code shows how items are added in recycler view of order activity
        ArrayList<OrderModel> list = helper.getOrders();


        // Here we are adding items in the array list through the database


        // Setting adapter on the recycler view
        OrderAdapter adapter = new OrderAdapter(list, this);
        binding.orderRecyclerView.setAdapter(adapter);


        // Vertical scrolling using linear layout manager
        // Setting linear layout manager on the recycler view
        // Here linear layout manager is used to vertically scroll all the items of the recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.orderRecyclerView.setLayoutManager(layoutManager);

    }

}
