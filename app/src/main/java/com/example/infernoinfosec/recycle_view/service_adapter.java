package com.example.infernoinfosec.recycle_view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infernoinfosec.R;
import com.example.infernoinfosec.fragments.HomeFragment;

import java.util.ArrayList;

public class service_adapter extends RecyclerView.Adapter<service_adapter.myviewholder> {
    Context context;
    ArrayList<service_model> datalist;

    public service_adapter(ArrayList<service_model> datalist,Context context) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.serv_display,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        final service_model temp=datalist.get(position);

        holder.Headline.setText(datalist.get(position).getHead());
        holder.subtext.setText(datalist.get(position).getBody());

        holder.cyDef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(context,DataDisplayActivity.class);
                 intent.putExtra("imgname",temp.getImgname());
                intent.putExtra("Name",temp.getName());
                intent.putExtra("Email",temp.getEmail());
                intent.putExtra("Phone",temp.getPhone());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
               // getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment() ).commit();


               // context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView Headline,subtext;
        CardView cyDef;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            cyDef=(CardView) itemView.findViewById(R.id.cyDefanseCard);
            img=(ImageView) itemView.findViewById(R.id.bg_img);
            Headline=(TextView)itemView.findViewById(R.id.Headline);
            subtext=(TextView)itemView.findViewById(R.id.subtext);


        }
    }
}
