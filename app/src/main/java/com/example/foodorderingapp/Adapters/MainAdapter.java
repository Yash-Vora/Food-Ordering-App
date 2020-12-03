package com.example.foodorderingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderingapp.DetailActivity;
import com.example.foodorderingapp.Models.MainModel;
import com.example.foodorderingapp.R;

import java.util.ArrayList;
import java.util.Collection;

// Here we are extending viewHolder class created inside this class
// Also implementing Filterable interface for filtering data in the search bar and it's methods are created inside this class
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.viewHolder> implements Filterable {

    ArrayList<MainModel> list;
    ArrayList<MainModel> listAll;
    Context context;

    // Constructor
    public MainAdapter(ArrayList<MainModel> list, Context context) {
        this.list = list;
        this.context = context;
        this.listAll = new ArrayList<>(list);
    }

    // This function is used to inflate sample layout file into "activity_main.xml" layout file
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_main_food, parent, false);
        return new viewHolder(view);
    }

    // This function is used to set data of all views by it's position in "activity_main.xml"
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        final MainModel model = list.get(position);
        holder.mainImage.setImageResource(model.getMainImage());
        holder.mainName.setText(model.getMainName());
        holder.mainPrice.setText(model.getMainPrice());
        holder.mainDescription.setText(model.getMainDescription());

        // Following is the code what happens when we click any of the item of main activity
        // Here when we click on any of the item it will move to detail activity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent moveToDetailActivity = new Intent(context, DetailActivity.class);
                moveToDetailActivity.putExtra("mainImage", model.getMainImage());
                moveToDetailActivity.putExtra("mainName", model.getMainName());
                moveToDetailActivity.putExtra("mainPrice", model.getMainPrice());
                moveToDetailActivity.putExtra("mainDescription", model.getMainDescription());
                moveToDetailActivity.putExtra("type", 1);
                context.startActivity(moveToDetailActivity);

            }
        });

    }

    // This function is used to set size of recycler view in "activity_main.xml"
    @Override
    public int getItemCount() {
        return list.size();
    }



    // This code is used in search bar
    // This will filter the data that we write in the search bar
    @Override
    public Filter getFilter() {
        return filter;
    }

    // This code will perform filtering and publish results in search bar
    Filter filter = new Filter() {

        // This method will perform filtering in background and return it's result in publishResults() method
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<MainModel> filteredList = new ArrayList<>();

            if(charSequence.toString().isEmpty())
            {
                filteredList.addAll(listAll);
            }
            else
            {
                for(MainModel mainList : listAll)
                {
                    if(mainList.getMainName().toLowerCase().contains(charSequence.toString().toLowerCase()))
                    {
                        filteredList.add(mainList);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        // This method will show filtered results in the user interface(UI)
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list.clear();
            list.addAll((Collection<? extends MainModel>) filterResults.values);
            notifyDataSetChanged();
        }

    };



    // This function is used to bind all the views that are used in layout file and get that views by it's id
    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView mainImage;
        TextView mainName, mainPrice, mainDescription;

        // Constructor
        public viewHolder(@NonNull View itemView) {

            super(itemView);

            mainImage = itemView.findViewById(R.id.mainImage);
            mainName = itemView.findViewById(R.id.mainName);
            mainPrice = itemView.findViewById(R.id.mainPrice);
            mainDescription = itemView.findViewById(R.id.mainDescription);

        }

    }

}
