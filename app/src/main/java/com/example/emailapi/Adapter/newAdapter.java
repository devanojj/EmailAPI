package com.example.emailapi.Adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.emailapi.Entity.Animal;
import com.example.emailapi.Main.SubmissionPage;
import com.example.emailapi.R;


import java.util.ArrayList;

public class newAdapter extends RecyclerView.Adapter<newAdapter.CategoryViewHolder> {

    public Context c;
    public ArrayList<Animal> arrayList;

    public newAdapter(Context c, ArrayList<Animal> arrayList) {
        this.c=c;
        this.arrayList=arrayList;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.animal_rcv_layout,parent,false);
        return new CategoryViewHolder(v, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        Animal tlist1 = arrayList.get(position);
        holder.t1.setText(tlist1.getDOB());
        holder.t2.setText(tlist1.getBreed());
        holder.t3.setText(tlist1.getEnergyLevel());
        holder.t4.setText(tlist1.getName());
        holder.t5.setText(tlist1.getFrom());
        holder.id.setText(tlist1.getId());
        holder.idfromUser.setText(tlist1.getUsid());
        Glide.with(holder.itemView.getContext())
                .load(tlist1.getmImageUrl())
                .fitCenter()
                .centerCrop()
                .into(holder.i1);


    }



    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        private Context context; // add final maybe?
        public TextView t1;
        public TextView t2;
        public TextView t3;
        public TextView t4;
        public TextView t5;
        public TextView id;
        public TextView idfromUser;
        public ImageView i1;

        public Button s;

        public CategoryViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;

            t1 = itemView.findViewById(R.id.categoryEditTV1);
            t2 = itemView.findViewById(R.id.descEditTV1);
            t3 = itemView.findViewById(R.id.deadlineEditTV1);
            t4 = itemView.findViewById(R.id.statusEditTV1);
            t5 = itemView.findViewById(R.id.fromEditTV1);
            id = itemView.findViewById(R.id.idAnimal);
            idfromUser = itemView.findViewById(R.id.idUser);
            i1 = itemView.findViewById(R.id.pictureOfDog);
            s = itemView.findViewById(R.id.selectAnimal);

            s.setOnClickListener(v -> {
                Toast.makeText(context, "Button clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(itemView.getContext(), SubmissionPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Bundle bundle = new Bundle();
                bundle.putString("Name", t4.getText().toString());
                bundle.putString("Age", t2.getText().toString());
                bundle.putString("Id", id.getText().toString());
                bundle.putString("idFromUser", idfromUser.getText().toString());

                intent.putExtras(bundle);

                context.startActivity(intent);

            });


        }
    }

}
