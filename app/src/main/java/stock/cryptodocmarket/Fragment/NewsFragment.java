package stock.cryptodocmarket.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import stock.cryptodocmarket.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

   // String url="http://www.bitnewz.net/rss/Feed/25";
    String url="https://cointelegraph.com/rss";
    //String url="https://letstalkbitcoin.com/rss/feed/blog?limit=35&sites=1&categories=7,13,15,16,30";
    RecyclerView newslist;
    ArrayList<NewsData> arrayList=new ArrayList<>();
    NewsAdapter newsAdapter;
    RecyclerView.LayoutManager layoutManager;
    String imgRegex = "<[iI][mM][gG][^>]+[sS][rR][cC]\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
    public NewsFragment() {
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
        View v=inflater.inflate(R.layout.fragment_contacts, container, false);
        newslist= (RecyclerView) v.findViewById(R.id.newslist);
        layoutManager=new LinearLayoutManager(getActivity());
        newslist.setLayoutManager(layoutManager);
      //  Toast.makeText(getActivity(), "redere", Toast.LENGTH_SHORT).show();


        new MyNewsAsync().execute();
        return v;
    }
    private class MyNewsAsync extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
                Document document=documentBuilder.parse(url);
                document.normalize();
                NodeList nodeList=document.getElementsByTagName("item");
                for (int i=0;i<nodeList.getLength();i++)
                {
                    Node node=nodeList.item(i);
                    Element element= (Element) node;

                    String title=getDOMdata(element,"title");
                    String pubDate=getDOMdata(element,"pubDate");
                    String description=getDOMdata(element,"description");
                    String mediacontent=getDOMdata(element,"media:content");
                    Pattern p = Pattern.compile(imgRegex);
                    Matcher m = p.matcher(description.replace("<img>", ""));
                    String link=getDOMdata(element,"link");
                    NewsData newsdata=new NewsData("",title,pubDate,"",description,link);
                    if (m.find()) {
                        String imgSrc = m.group(1);
                        newsdata.setImage(imgSrc);

                    }
                    arrayList.add(newsdata);


                }



            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        private String getDOMdata(Element element, String title) {
            NodeList nolist=element.getElementsByTagName(title);
            Node child=nolist.item(0);
            Element ele= (Element) child;
            String data=ele.getTextContent();
            return data;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            newsAdapter=new NewsAdapter(arrayList,getActivity());
            newslist.setAdapter(newsAdapter);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
/*        inflater.inflate(R.menu.menu_contacts_fragment, menu);*/
        super.onCreateOptionsMenu(menu, inflater);
    }

}
