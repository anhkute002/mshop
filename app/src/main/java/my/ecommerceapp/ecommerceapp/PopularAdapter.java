package my.ecommerceapp.ecommerceapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import my.ecommerceapp.ecommerceapp.activities.DetailedActivity;
import my.ecommerceapp.ecommerceapp.models.PopularProductsModel;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    private Context context;
    private List<PopularProductsModel> popularProductsModelList;

    public PopularAdapter(Context context, List<PopularProductsModel> popularProductsModelList) {
        this.context = context;
        this.popularProductsModelList = popularProductsModelList;
    }

    @NonNull
    @Override
    public PopularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(popularProductsModelList.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(popularProductsModelList.get(position).getName());
        holder.price.setText(String.valueOf(popularProductsModelList.get(position).getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detailed",popularProductsModelList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularProductsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name,price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.all_img);
            name = itemView.findViewById(R.id.all_product_name);
            price = itemView.findViewById(R.id.all_price);
        }
    }
}
