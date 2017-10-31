package stock.cryptodocmarket.InidanCompany;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import stock.cryptodocmarket.MyService;
import stock.cryptodocmarket.R;
import stock.cryptodocmarket.SessionData.SessionIndianListData;
import stock.cryptodocmarket.UserActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class IndianFragment extends Fragment {
RecyclerView marketlist;
ProgressBar progress;
    ArrayList<IndianMarket> arrayList;
    private static final String TAG = "BroadcastTest";
    private Intent intent;
    private SessionIndianListData sharedPreference;
    private Gson gson;
    IndianAdapter indianAdapter;
    public IndianFragment() {
        // Required empty public constructor
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
        View v=inflater.inflate(R.layout.fragment_calls, container, false);
        intent = new Intent(getActivity(), BroadcastReceiver.class);
        marketlist= v.findViewById(R.id.marketlist);
        progress= v.findViewById(R.id.progress);

        gson = new Gson();
        sharedPreference = new SessionIndianListData(getActivity());
        getHighScoreListFromSharedPreference();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        marketlist.setLayoutManager(layoutManager);
        getActivity().startService(intent);
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(MyService.BROADCAST_ACTION));


        Log.d("datat",""+getHighScoreListFromSharedPreference());
        if (arrayList==null){

        }else {
            indianAdapter=new IndianAdapter(arrayList,getActivity());
            marketlist.setAdapter(indianAdapter);
            progress.setVisibility(View.GONE);
        }
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private ArrayList<IndianMarket> getHighScoreListFromSharedPreference() {
        //retrieve data from shared preference
        String jsonScore = sharedPreference.getHighScoreList();
        Type type = new TypeToken<List<IndianMarket>>(){}.getType();
        arrayList = gson.fromJson(jsonScore, type);

        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        return arrayList;
    }
    private void saveScoreListToSharedpreference(ArrayList<IndianMarket> scoresList) {
        //convert ArrayList object to String by Gson
        String jsonScore = gson.toJson(scoresList);

        //save to shared preference
        sharedPreference.saveHighScoreList(jsonScore);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_user, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.action_status:
                Intent intent=new Intent(getActivity(), UserActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("TAAAAAAAAAAAG",""+intent.getParcelableArrayListExtra("indianlist"));
           // progress.setVisibility(View.GONE);
            if (arrayList!=null){
                arrayList = new ArrayList<>();
            }
            progress.setVisibility(View.GONE);
            arrayList =intent.getParcelableArrayListExtra("indianlist");
            saveScoreListToSharedpreference(arrayList);
            indianAdapter=new IndianAdapter(arrayList,getActivity());
            marketlist.setAdapter(indianAdapter);

        }


    };

}
