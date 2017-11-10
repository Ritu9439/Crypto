package stock.cryptodocmarket.Fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import stock.cryptodocmarket.R;


/**
 * Created by Administrator on 15-08-2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    ArrayList arrayList;
    Context context;


    public NewsAdapter(ArrayList arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //we call inflator over here...
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsitem, parent, false);

        return new MyViewHolder(v, context, arrayList);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        NewsData newsData = (NewsData) arrayList.get(position);
        holder.title.setText(newsData.getTitle());
        //holder.header.setText(newsData.getHeader());
        holder.pubDate.setText(newsData.getPubDate());
       //holder.description.setText(Html.fromHtml(newsData.getDescription()));

        Glide.with(context).load(newsData.getImage()).into(holder.image);
        //holder.image.setImageResource(newsData.getImage());
        Log.d("dataa",newsData.getDescription());


    }

    @Override
    public int getItemCount() {

        //return the size of arraylist
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title,pubDate,header,description,link;
        ImageView image;
        Context ctx;
        ArrayList<NewsData> arrayList = new ArrayList();

        public MyViewHolder(View itemView, Context context, ArrayList al) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.title);
            pubDate = (TextView) itemView.findViewById(R.id.pubDate);
            header = (TextView) itemView.findViewById(R.id.header);
            description = (TextView) itemView.findViewById(R.id.description);
            link = (TextView) itemView.findViewById(R.id.link);
            image = (ImageView) itemView.findViewById(R.id.image);
            arrayList = al;
            ctx = context;
            link.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

if (view.getId()== R.id.link){
    int position = getAdapterPosition();
    NewsData data = arrayList.get(position);
    Toast.makeText(ctx, "" + data.getLink(), Toast.LENGTH_SHORT).show();

    Intent intent=new Intent(ctx, NewsFullScreen.class);
    intent.putExtra("website",data.getLink());
    ctx.startActivity(intent);
}



        }

    }
}