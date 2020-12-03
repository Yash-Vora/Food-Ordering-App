package com.example.foodorderingapp.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.DBHelper;
import com.example.foodorderingapp.DetailActivity;
import com.example.foodorderingapp.Models.OrderModel;
import com.example.foodorderingapp.R;

import java.util.ArrayList;

// Here we are extending viewHolder class created inside this class
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.viewHolder>{

    ArrayList<OrderModel> list;
    Context context;

    // Constructor
    public OrderAdapter(ArrayList<OrderModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    // This function is used to inflate sample layout file into "activity_order.xml" layout file
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_order_food, parent, false);
        return new viewHolder(view);
    }

    // This function is used to set data of all views by it's position in "activity_order.xml"
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        final OrderModel model = list.get(position);

        holder.orderImage.setImageResource(model.getOrderImage());
        holder.orderName.setText(model.getOrderName());
        holder.orderNumber.setText(model.getOrderNumber());
        holder.orderPrice.setText(model.getOrderPrice());

        // Following is the code what happens when we click any of the item of order activity
        // Here when we click on any of the item it will move to detail activity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent moveToDetailActivity = new Intent(context, DetailActivity.class);
                moveToDetailActivity.putExtra("orderNumber", model.getOrderNumber());
                moveToDetailActivity.putExtra("type", 2);
                context.startActivity(moveToDetailActivity);

            }
        });

        // Following is the code what happens when we click any of the item of order activity
        // Here when we long click on any of the item it will delete that item
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                // Alert Dialog Box
                // It will be shown when we press back button in our mobile
                new AlertDialog.Builder(context)
                        .setTitle("Delete Item")
                        .setIcon(R.drawable.warning)
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                DBHelper helper = new DBHelper(context);

                                if(helper.deleteOrder(Integer.parseInt(model.getOrderNumber())) > 0)
                                    Toast.makeText(context, "Order is deleted successfully", Toast.LENGTH_SHORT).show();

                                else
                                    Toast.makeText(context, "          Order isn't deleted \n Please delete the order again", Toast.LENGTH_SHORT).show();

                            }
                        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();

                return true;

            }
        });

    }

    // This function is used to set size of recycler view in "activity_order.xml"
    @Override
    public int getItemCount() {
        return list.size();
    }



    // This function is used to bind all the views that are used in layout file and get that views by it's id
    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView orderImage;
        TextView orderName, orderNumber, orderPrice;

        // Constructor
        public viewHolder(@NonNull View itemView) {

            super(itemView);

            orderImage = itemView.findViewById(R.id.orderImage);
            orderName = itemView.findViewById(R.id.orderName);
            orderNumber = itemView.findViewById(R.id.orderNumber);
            orderPrice = itemView.findViewById(R.id.orderPrice);

        }

    }

}
