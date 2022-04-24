package com.example.infernoinfosec.recycle_view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.infernoinfosec.DashboardActivity;
import com.example.infernoinfosec.DataDisplayActivity;
import com.example.infernoinfosec.R;
import com.example.infernoinfosec.fragments.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadepter extends RecyclerView.Adapter<myadepter.myviewholder>  {


    Context context;
    List<model> ListAll;
   // private CollectionReference root = FirebaseFirestore.getInstance().collection("News");
    FirebaseAuth fAuth;





    public myadepter(ArrayList<model> datalist, Context context)
    {
        this.datalist = datalist;
        this.context = context;
        this.ListAll= new ArrayList<>(datalist);
    }




    ArrayList<model> datalist;

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false); //use to add grid layout--> R.layout.grid_card_view
        return new myviewholder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {


        final model temp=datalist.get(position);
        String head=datalist.get(position).getHeadline();
        //String postId=temp.PostId;

        holder.Headline.setText(head.substring(0, Math.min(head.length(), 95))+(head.length()>=95?"...":""));
       // holder.subtext.setText(datalist.get(position).getSubtext());
       // holder.phone.setText(datalist.get(position).getPhone());
        Uri uri= Uri.parse(datalist.get(position).getPurl());
        Glide.with(holder.img.getContext()).load(uri).into(holder.img);

       holder.readmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DataDisplayActivity.class);
                // intent.putExtra("imgname",temp.getImgname());
                intent.putExtra("Headline",temp.getHeadline());
                intent.putExtra("ImageUri",temp.getPurl());
                intent.putExtra("Description",temp.getDescription());
                intent.putExtra("postedDate",temp.getPostedOn());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });

        fAuth=(FirebaseAuth) FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null) {
            holder.del.setVisibility(View.VISIBLE);
            holder.del.isClickable();
        }

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.img.getContext());
                builder.setTitle("Delete News");
                builder.setMessage("Do you want to delete this news ?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseFirestore.getInstance().collection("News")
                                .document(temp.getKey())
                                .delete();
                        myadepter.this.notifyDataSetChanged();
                        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();;
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });





    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }



    class myviewholder extends RecyclerView.ViewHolder
    {
        public View delete;
        ImageView img,del;
        TextView Headline;
        Button readmore;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

           img=(ImageView) itemView.findViewById(R.id.bg_img);
            Headline=(TextView)itemView.findViewById(R.id.Headline);
            readmore=(Button)itemView.findViewById(R.id.readMoreBtn);
            del=(ImageView)itemView.findViewById(R.id.deleteNews);



        }
    }


}
