package stock.cryptodocmarket.InidanCompany;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import stock.cryptodocmarket.R;


/**
 * Created by sai on 29/8/17.
 */

public class IndianAdapter extends RecyclerView.Adapter<IndianAdapter.MyViewHolder> {

    ArrayList<IndianMarket> arrayList;


    Context context;

    public IndianAdapter(ArrayList<IndianMarket> arrayList, Context context) {
        this.arrayList = arrayList;

        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //we call inflator over here...
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.indianrow, parent, false);

        return new MyViewHolder(v, context,arrayList);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

       /* if (position == 0) {
            holder.itemView.setBackgroundColor(Color.GRAY);
        }
        else if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.DKGRAY);
        }
        else if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.GRAY);
        }*/
        IndianMarket indianMarket=arrayList.get(position);
        if (indianMarket.getMarket()!=null && !indianMarket.getMarket().isEmpty()) {
            holder.tvmarket.setText(indianMarket.getMarket());
        }
        if (indianMarket.getBuy()!=null && !indianMarket.getMarket().isEmpty()) {
            holder.tvbuy.setText(indianMarket.getBuy());

        }
        if (indianMarket.getSell()!=null && !indianMarket.getMarket().isEmpty()) {
            holder.tvsell.setText(indianMarket.getSell());

        }


        holder.tvbuy.setTextColor(Color.DKGRAY);
        holder.tvsell.setTextColor(Color.DKGRAY);
        new CountDownTimer(1000, 50) {

            @Override
            public void onTick(long arg0) {
                // TODO Auto-generated method stub

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFinish() {
                holder.tvbuy.setTextColor(context.getColor(R.color.colorPrimary));
                holder.tvsell.setTextColor(context.getColor(R.color.colorPrimary));

            }
        }.start();




    }

    @Override
    public int getItemCount() {

        //return the size of arraylist
return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvmarket,tvbuy,tvsell;


        Context ctx;
        ArrayList<IndianMarket> al;


        public MyViewHolder(View itemView, Context context,ArrayList<IndianMarket> arrayList) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvmarket = (TextView) itemView.findViewById(R.id.tvmarket);
            tvbuy = (TextView) itemView.findViewById(R.id.tvbuy);
            tvsell = (TextView) itemView.findViewById(R.id.tvsell);


al=arrayList;

            ctx = context;
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            IndianMarket indianMarket=al.get(position);
          //  Toast.makeText(ctx, ""+indianMarket.getMarket(), Toast.LENGTH_SHORT).show();


            Intent intent=new Intent(ctx, IndianGraph.class);
            intent.putExtra("market",indianMarket.getMarket());


            ctx.startActivity(intent);
          /* ForeignMarket foreignMarket=al.get(position);

            Intent intent=new Intent(ctx, GraphActivity.class);

            intent.putExtra("coin",foreignMarket.getLASTMARKET());
            intent.putExtra("coinprice",foreignMarket.getPRICE());
            intent.putExtra("market",foreignMarket.getMARKETNAME());
            ctx.startActivity(intent);
*/

        }
    }

}