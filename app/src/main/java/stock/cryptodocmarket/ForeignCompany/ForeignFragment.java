package stock.cryptodocmarket.ForeignCompany;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import stock.cryptodocmarket.MyInterface;
import stock.cryptodocmarket.R;
import stock.cryptodocmarket.RecyclerTouchListener;
import stock.cryptodocmarket.UserActivity;


public class ForeignFragment extends Fragment {
    StringBuilder stringBuilder = new StringBuilder();
    ArrayList al2=new ArrayList();
    public static String marketname[]={"Bittrex","Bitfinex","Bitstamp","Poloniex","Kraken","BTCE"};
    ForeignMarketListAdapter foreignMarketListAdapter;
    RecyclerView recyclerView;
    ProgressBar progress;
    public ForeignFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView= (RecyclerView) v.findViewById(R.id.marketlist);
        progress= (ProgressBar) v.findViewById(R.id.progress);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        getImageData();
        foreignMarketListAdapter=new ForeignMarketListAdapter(getActivity());
        recyclerView.setAdapter(foreignMarketListAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent=new Intent(getActivity(),DetailedActivity.class);
                intent.putExtra("marketname",""+ marketname[position]);
                intent.putExtra("data",al2);
                Log.d("mmmmmm",""+al2);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_user, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_status:
                Intent intent = new Intent(getActivity(), UserActivity.class);
                startActivity(intent);
                break;
        }

            return super.onOptionsItemSelected(item);
        }



    public  void getImageData(){

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://cryptodoc.in/").build();
        MyInterface myinterface = restAdapter.create(MyInterface.class);
        myinterface.getImage(new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                try {
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String output = null;

                    progress.setVisibility(View.GONE);
                    while ((output = buffer.readLine()) != null) {
                        stringBuilder.append(output);
                    }

                    JSONArray jsonArray=new JSONArray(""+stringBuilder);
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        al2.add(jsonArray.getString(i));

                    }




                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }
}
