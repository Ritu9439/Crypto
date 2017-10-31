package stock.cryptodocmarket;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


/**
 * Created by sai on 29/8/17.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.MyViewHolder> {

    ArrayList<Comments> arrayList;

    Context context;

    public CommentsAdapter(ArrayList<Comments> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //we call inflator over here...
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cmtlayout, parent, false);

        return new MyViewHolder(v, context,arrayList);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

       /* if (position == 0) {
            holder.itemView.setBackgroundColor(Color.GRAY);
        }
        else if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.DKGRAY);
        }
        else if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.GRAY);
        }*/
        Comments comments=arrayList.get(position);
        holder.name.setText(comments.getEmail());
        holder.date.setText(comments.getDate());
        holder.message.setText(comments.getMessage());

        Glide.with(context).load(comments.getProfilepic()).placeholder(R.mipmap.ic_profile).error(R.mipmap.ic_profile).into(holder.profilepic);

        Log.d("datadd",comments.getProfilepic());





    }

    @Override
    public int getItemCount() {

        //return the size of arraylist
return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name,email,message,date;
        ImageView profilepic;

        Context ctx;
        ArrayList<Comments> al;


        public MyViewHolder(View itemView, Context context,ArrayList<Comments> arrayList) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = (TextView) itemView.findViewById(R.id.name);
            profilepic = (ImageView) itemView.findViewById(R.id.profile_image);
            message = (TextView) itemView.findViewById(R.id.message);
            date = (TextView) itemView.findViewById(R.id.postdate);




al=arrayList;

            ctx = context;
        }

        @Override
        public void onClick(View view) {



        }
    }
}