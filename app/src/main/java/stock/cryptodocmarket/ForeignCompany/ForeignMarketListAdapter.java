package stock.cryptodocmarket.ForeignCompany;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import stock.cryptodocmarket.R;

/**
 * Created by Administrator on 24-10-2017.
 */

public class ForeignMarketListAdapter extends RecyclerView.Adapter<ForeignMarketListAdapter.MyViewHolder>{
    String name[]= {"Bittrex","Bitfinex","Bitstamp ","Poloniex","Kraken","BTCE"};
    Context context;

    public ForeignMarketListAdapter(Context context) {
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //we call inflator over here...
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.foreignlist, parent, false);

        return new MyViewHolder(v, context);

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
        holder.name.setText(name[position]);

        //holder.nooftask.setText(getSet.getNooftask());


    }

    @Override
    public int getItemCount() {

        //return the size of arraylist
        return name.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        ImageView imageView;
        Context ctx;


        public MyViewHolder(View itemView, Context context) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = (TextView) itemView.findViewById(R.id.mname);


            ctx = context;
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            //  Toast.makeText(ctx, ""+data.getName(), Toast.LENGTH_SHORT).show();

            // openBottomSheet(view);


        }
    }
}
