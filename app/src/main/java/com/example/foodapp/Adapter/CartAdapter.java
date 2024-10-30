package com.example.foodapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.foodapp.Domain.Foods;
import com.example.foodapp.Helper.ChangeNumberItemsListener;
import com.example.foodapp.Helper.ManagmentCart;
import com.example.foodapp.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.viewholer> {

    ArrayList<Foods> list;
    private ManagmentCart managmentCart;
    ChangeNumberItemsListener changeNumberItemsListener;

    public CartAdapter(ArrayList<Foods> list, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.list = list;
        managmentCart = new ManagmentCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public CartAdapter.viewholer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholer_cart, parent, false);
        return new viewholer(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.viewholer holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.feeEachItem.setText("$" +(list.get(position).getNumberInCart()*  list.get(position).getPrice()));
        holder.totalEachItem.setText(list.get(position).getNumberInCart() + "*$" + (
                list.get(position).getPrice()
        ));
        holder.num.setText(list.get(position).getNumberInCart() + "");
        Glide.with(holder.itemView.getContext()).load(list.get(position).getImagePath()).transform(
                new CenterCrop(), new RoundedCorners(30)
        ).into(holder.pic1);

        holder.plusItem.setOnClickListener(view -> managmentCart.plusNumberItem(list, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.change();
        }));

        holder.minusItem.setOnClickListener(view -> managmentCart.minusNumberItem(list, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.change();
        }));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class viewholer extends RecyclerView.ViewHolder {
        TextView title, feeEachItem, plusItem, minusItem, totalEachItem, num;
        ImageView pic1;

        public viewholer(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleTxt);
            pic1 = itemView.findViewById(R.id.pic1);
            feeEachItem = itemView.findViewById(R.id.feeEachitem);
            plusItem = itemView.findViewById(R.id.plusCartBtn);
            minusItem = itemView.findViewById(R.id.minusCartBtn);
            totalEachItem = itemView.findViewById(R.id.totalEachitem);
            num = itemView.findViewById(R.id.numberItemTxt);
        }
    }
}
