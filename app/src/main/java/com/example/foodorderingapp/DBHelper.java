package com.example.foodorderingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.foodorderingapp.Models.OrderModel;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    final static String DBName = "foodOrderingDatabase.db";
    final static int DBVersion = 2;

    // Constructor
    // In this constructor their are four arguments passed in super function:
    // 1.  context    It shows from where database is called
    // 2.  DBName     Here we are passing name of the database
    // 3.  factory    Here we are passing value of factory
    // 4.  DBVersion  Here we are passing value database version
    //                DBVersion value will be changed whenever we change anything the database and below onUpgrade method will also be called
    public DBHelper(@Nullable Context context) {
        super(context, DBName, null, DBVersion);
    }

    // Creating the table in the above "foodOrderingDatabase.db" database
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table orders" +
                        "(id integer primary key autoincrement," +
                        "image int," +
                        "foodname text," +
                        "quantity int," +
                        "description text," +
                        "name text," +
                        "phone text," +
                        "price int," +
                        "originalprice int)"
        );

    }

    // This method is called when we change anything database
    // If we change anything in the database then first we have to change above DBVersion and then this method will be called
    // In this method it will delete the above table and new updated table will be created at that place
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists orders");
        onCreate(db);
    }




    /* ************    CRUD OPERATION     ************ */




    // Create Operation
    // This method is used to insert order in the database
    // This method is called when we are clicking on "Order Now" button and that order will be inserted in the database
    public boolean insertOrder(int image, String foodName, int quantity, String description, String name, String phone, int price, int originalPrice){

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

        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("image", image);
        values.put("foodname", foodName);
        values.put("quantity", quantity);
        values.put("description", description);
        values.put("name", name);
        values.put("phone", phone);
        values.put("price", price);
        values.put("originalprice", originalPrice);

        // Here when insert method is called it will return id of inserted row
        long value = db.insert("orders", null, values);
        db.close();

        // If 0 or <0 value is returned row isn't inserted
        if(value<=0)
            return false;
        // If >0 value is returned row is inserted
        else
            return true;

    }




    // Read Operation
    // This method is used to read order from the database
    // This method is called from order activity where we are getting all the orders from the database and show that orders in the order activity page
    public ArrayList<OrderModel> getOrders(){

        ArrayList<OrderModel> orders = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select id,image,foodName,price from orders", null);

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

        if(cursor.moveToFirst()){

            OrderModel orderModel = new OrderModel();
            orderModel.setOrderNumber(cursor.getInt(0) + "");
            orderModel.setOrderImage(cursor.getInt(1));
            orderModel.setOrderName(cursor.getString(2));
            orderModel.setOrderPrice(cursor.getInt(3) + "");
            orders.add(orderModel);
            while(cursor.moveToNext()){

                OrderModel model = new OrderModel();
                model.setOrderNumber(cursor.getInt(0) + "");
                model.setOrderImage(cursor.getInt(1));
                model.setOrderName(cursor.getString(2));
                model.setOrderPrice(cursor.getInt(3) + "");
                orders.add(model);

            }

        }

        cursor.close();
        db.close();
        return orders;

    }

    // This method is used to read order by it's id from the database
    // This method is called from detail activity where we are getting one order by it's id from the database and show that order in the detail activity page
    public Cursor getOrderById(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from orders where id=" + id, null);

        if(cursor != null)
            cursor.moveToFirst();

        return cursor;

    }




    // Update Operation
    // This method is used to update order in the database
    // This method is called when we are clicking on "Update Order" button and that order will be updated in the database
    public boolean updateOrder(int id, int image, String foodName, int quantity, String description, String name, String phone, int price){

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

        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("image", image);
        values.put("foodname", foodName);
        values.put("quantity", quantity);
        values.put("description", description);
        values.put("name", name);
        values.put("phone", phone);
        values.put("price", price);

        // Here when update method is called it will return id of updated row
        long value = db.update("orders", values, "id="+id, null);
        db.close();

        // If 0 or <0 value is returned row isn't updated
        if(value<=0)
            return false;
        // If >0 value is returned row is updated
        else
            return true;

    }




    // Delete Operation
    // This method is used to delete order from the database
    // This method is called when we long press on any item of order activity and it will delete that order from the database
    public int deleteOrder(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        int value = db.delete("orders", "id="+id, null);
        return value;

    }

}
