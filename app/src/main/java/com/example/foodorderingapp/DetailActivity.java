package com.example.foodorderingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.foodorderingapp.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {


    ActivityDetailBinding binding;
    int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Binding is used instead of findVIewById
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        // Custom Toolbar
        Toolbar customToolbar = binding.detailCustomToolbar.customToolbar;
        // OR
        // Toolbar customToolbar = findViewById(R.id.mainCustomToolbar);
        setSupportActionBar(customToolbar);
        // OR
        // setSupportActionBar(binding.mainCustomToolbar.customToolbar);



        /*    Code for "Back Button (<-)"    */
        // Setting back button to move to previous activity
        // Here back button is used to move to main activity
        customToolbar.setNavigationIcon(R.drawable.back_icon);

        // This function shows what will happen when we click on back button
        // Here we are just moving to the previous activity that is main activity whenever back button is pressed
        customToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        // Following code is for inserting data in the database
        // Creating database helper object
        final DBHelper helper = new DBHelper(this);



        // Code for order processing page
        if((getIntent().getIntExtra("type", 0)) == 1){

            // Setting title of custom toolbar
            getSupportActionBar().setTitle("Order Processing Page");


            // Here we are getting the value through intent that is passed from main adapter
            final int detailImage = getIntent().getIntExtra("mainImage", 0);
            final String detailName = getIntent().getStringExtra("mainName");
            final int detailPrice = Integer.parseInt(getIntent().getStringExtra("mainPrice"));
            final String detailDescription = getIntent().getStringExtra("mainDescription");


            // Setting the above data on the views of the detail activity
            binding.detailImage.setImageResource(detailImage);
            binding.detailName.setText(detailName);
            binding.detailPrice.setText(String.format("%d", detailPrice));
            binding.detailDescription.setText(detailDescription);



            /*  Code for incrementing and decrementing the quantity  */
            // Initializing value of the count
            count = 1;


            // Code for incrementing the quantity
            binding.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count++;
                    binding.quantity.setText(""+count);

                    // Code for increasing the price when quantity is increased
                    int newPrice = Integer.parseInt(binding.detailPrice.getText().toString());
                    binding.detailPrice.setText(""+(newPrice+detailPrice));
                }
            });

            // Code for decrementing the quantity
            binding.subtract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count>1){
                        count--;
                        binding.quantity.setText(""+count);

                        // Code for decreasing the price when quantity is decreased
                        int newPrice = Integer.parseInt(binding.detailPrice.getText().toString());
                        binding.detailPrice.setText(""+(newPrice-detailPrice));
                    }
                }
            });



            /*
             id = 0
             image = 1
             foodName = 2
             quantity = 3
             description = 4
             name = 5
             phone = 6
             price = 7
             originalPrice = 8
            */


            // Following code shows what happens when we click on "Order Now" button
            binding.insertBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // First Username and mobile number will be validated
                    if(!validateUsername() | !validateMobileNumber()) {
                        return;
                    }

                    // After validation below code to buy the item will run
                    else {

                        // Alert Dialog Box
                        // It will be shown when we press "Order Now" button in our mobile
                        new AlertDialog.Builder(DetailActivity.this)
                                .setTitle("Buy Item")
                                .setIcon(R.drawable.warning)
                                .setMessage("Are you sure you want to buy this item?")
                                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        boolean isInserted = helper.insertOrder(
                                                detailImage,
                                                detailName,
                                                Integer.parseInt(binding.quantity.getText().toString()),
                                                detailDescription,
                                                binding.personName.getText().toString(),
                                                binding.mobileNumber.getText().toString(),
                                                Integer.parseInt(binding.detailPrice.getText().toString()),
                                                detailPrice
                                        );

                                        if (isInserted)
                                            Toast.makeText(DetailActivity.this, "Order is placed successfully", Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(DetailActivity.this, "          Order isn't placed \n Please place the order again", Toast.LENGTH_SHORT).show();

                                    }
                                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();

                    }

                }
            });

        }

        // Code for order updating page
        else{

            // Setting title of custom toolbar
            getSupportActionBar().setTitle("Order Updating Page");


            // Here we are getting the value through intent that is passed from order adapter
            final int id = Integer.parseInt(getIntent().getStringExtra("orderNumber"));


            // Cursor is basically used to access one row at a time of any table from the database
            // Here cursor object is used to get order by it's id and it is used below to set data of all views of detail activity
            Cursor cursor = helper.getOrderById(id);
            final int image = cursor.getInt(1);


            /*
             id = 0
             image = 1
             foodName = 2
             quantity = 3
             description = 4
             name = 5
             phone = 6
             price = 7
             originalPrice = 8
            */


            // Here above cursor object is used to set the data on the views of the detail activity
            binding.detailImage.setImageResource(cursor.getInt(1));
            binding.detailName.setText(cursor.getString(2));
            binding.quantity.setText(String.format("%d", cursor.getInt(3)));
            binding.detailDescription.setText(cursor.getString(4));
            binding.personName.setText(cursor.getString(5));
            binding.mobileNumber.setText(cursor.getString(6));
            binding.detailPrice.setText(String.format("%d", cursor.getInt(7)));



            // Here we are getting original price of the item from the database
            // It is used when quantity is incremented or decremented at that time price is also increased or decreased
            int originalPrice = cursor.getInt(8);



            /*  Code for incrementing and decrementing the quantity  */
            // Initializing value of the count
            count = Integer.parseInt(binding.quantity.getText().toString());


            // Code for incrementing the quantity
            binding.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count++;
                    binding.quantity.setText(""+count);

                    // Code for increasing the price when quantity is increased
                    int newPrice = Integer.parseInt(binding.detailPrice.getText().toString());
                    binding.detailPrice.setText(""+(newPrice+originalPrice));
                }
            });

            // Code for decrementing the quantity
            binding.subtract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count>1){
                        count--;
                        binding.quantity.setText(""+count);

                        // Code for decreasing the price when quantity is decreased
                        int newPrice = Integer.parseInt(binding.detailPrice.getText().toString());
                        binding.detailPrice.setText(""+(newPrice-originalPrice));
                    }
                }
            });



            // Changing text of "Order Now" button to "Update Order"
            binding.insertBtn.setText("Update Order");


            // Following code shows what happens when we click on "Update Order" button
            binding.insertBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // First Username and mobile number will be validated
                    if(!validateUsername() | !validateMobileNumber()) {
                        return;
                    }

                    // After validation below code to update the item will run
                    else {

                        // Alert Dialog Box
                        // It will be shown when we press "Update Order" button in our mobile
                        new AlertDialog.Builder(DetailActivity.this)
                                .setTitle("Update Item")
                                .setIcon(R.drawable.warning)
                                .setMessage("Are you sure you want to update this item?")
                                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        boolean isUpdated = helper.updateOrder(
                                                id,
                                                image,
                                                binding.detailName.getText().toString(),
                                                Integer.parseInt(binding.quantity.getText().toString()),
                                                binding.detailDescription.getText().toString(),
                                                binding.personName.getText().toString(),
                                                binding.mobileNumber.getText().toString(),
                                                Integer.parseInt(binding.detailPrice.getText().toString())
                                        );

                                        if (isUpdated)
                                            Toast.makeText(DetailActivity.this, "Order is updated successfully", Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(DetailActivity.this, "          Order isn't updated \n Please update the order again", Toast.LENGTH_SHORT).show();

                                    }
                                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();

                    }

                }
            });

        }

    }



    /*  ********  Validation Methods  ********  */



    // Username Validation
    private boolean validateUsername(){

        String Username = binding.personName.getText().toString();
        //        ^           Beginning of the string
        //       [A-Za-z]     First character is not whitespace but it can be either capitals or small letter
        //       [A-Z a-z]    Other characters can be either whitespaces or capitals or small letters
        //        +           String contains at least one alphabetical char
        //        $           End of the string
        String checkUsername = "^[A-Za-z][A-Z a-z]+$";

        if(Username.isEmpty()){
            binding.personName.setError("Field cannot be empty");
            return false;
        }
        else if(Username.length()>20){
            binding.personName.setError("Name is too large");
            return false;
        }
        else if(Username.length()<=5){
            binding.personName.setError("Name is too short");
            return false;
        }
        else if(!Username.matches(checkUsername)){
            binding.personName.setError("Name must not contain numeric or special characters or first whitespace character");
            return false;
        }
        else {
            binding.personName.setError(null);
            return true;
        }

    }


    // Mobile Number Validation
    private boolean validateMobileNumber(){

        String mobileNumber = binding.mobileNumber.getText().toString();
        //        [1-9]          It matches first digit and checks if number lies between 1 to 9
        //        [0-9]          It matches other digits and checks if number lies between 0 to 9
        //        {9}            It specifies remaining
        String checkMobileNumber = "[1-9][0-9]{9}";

        if(mobileNumber.isEmpty()){
            binding.mobileNumber.setError("Field cannot be empty");
            return false;
        }
        else if(mobileNumber.length()>10){
            binding.mobileNumber.setError("Mobile number must be of 10 characters");
            return false;
        }
        else if(mobileNumber.length()<10){
            binding.mobileNumber.setError("Mobile number must be of 10 characters");
            return false;
        }
        else if(!mobileNumber.matches(checkMobileNumber)){
            binding.mobileNumber.setError("Invalid mobile number");
            return false;
        }
        else {
            binding.mobileNumber.setError(null);
            return true;
        }

    }

}
