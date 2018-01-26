package com.amedia.campusfulda.fragments;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.amedia.campusfulda.CampusItems;
import com.amedia.campusfulda.CustomExpandableListAdapter;
import com.amedia.campusfulda.DataImporterExpandable;
import com.amedia.campusfulda.DatabaseHelper;
import com.amedia.campusfulda.R;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Dini on 15.01.2018.
 */

public class CategoryFragment extends Fragment {

    private String json;
    private String requestresult;
    private TextView textresult;
    private SearchView searchView;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    private DatabaseHelper myDatabase;

    public CategoryFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_category, container, false);

        myDatabase = new DatabaseHelper(getContext(), null, null, 1);

        expandableListView = view.findViewById(R.id.expandableListView);
        final List<CampusItems> campuslist = myDatabase.getAllItems();

        expandableListDetail = DataImporterExpandable.getData(campuslist);


        expandableListTitle =  new ArrayList<String>(expandableListDetail.keySet());

        expandableListAdapter = new CustomExpandableListAdapter(getContext(), expandableListTitle, expandableListDetail);

        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                //Toast.makeText(getContext(), expandableListTitle.get(groupPosition) + " List Expanded.", Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                //Toast.makeText(getContext(), expandableListTitle.get(groupPosition) + " List Collapsed.", Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                String context = expandableListTitle.get(groupPosition);
                String subject = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition);

                String regex = ".*$";

                subject = subject.replaceAll(regex, "").trim();

                double clat = 0;
                double clong = 0;
                String info = "";
                String address = "";
                String tags = "";
                String opened = "";


                for (ListIterator<CampusItems> iter = campuslist.listIterator(); iter.hasNext(); ) {
                    CampusItems c = iter.next();
                    Log.d("ICH BIN DEBUUUUUUUUUUUG", "NAMEEEE: "+c.getName()+" SUBJEEEEECT: "+subject);
                    if(c.getName().equals(subject)){

                        info = c.getInfo();
                        address = c.getAddress();
                        tags = c.getTags();
                        Log.d("TAAAAAAAG", "CLAAAAAAAAAAAAAAAAAAAAAAAAAAT222222222"+c.getLat());
                        clat = c.getLat();
                        clong = c.getLong();
                        opened = c.getOpened();

                        break;

                    }

                }

                Bundle b = new Bundle();
                b.putString("context", context);
                b.putString("subject", subject);
                b.putString("info", info);
                b.putString("address", address);
                b.putString("tags", tags);
                b.putString("opened", opened);
                b.putDouble("clat", clat);
                b.putDouble("clong", clong);

                FragmentManager fmanager = getActivity().getSupportFragmentManager();
                FragmentTransaction ftransaction = fmanager.beginTransaction();

                CategoryItemDetailsFragment itemdetails = new CategoryItemDetailsFragment();
                itemdetails.setArguments(b);

                ftransaction.replace(R.id.flContent, itemdetails);
                //ftransaction.addToBackStack("categorydetails");
                ftransaction.commit();

                Toast.makeText(
                        getContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });

        setRightIndicator();


        return view;

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static void setAllData(){



    }


    private class GetDataJSON extends AsyncTask<Void, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(Void... arg0) {
            try {
                json = Jsoup.connect("http://91.205.173.172/t3_project/fileadmin/myphp/campusDetails.php")
                        .timeout(1000000)
                        .header("Accept", "text/javascript")
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:40.0) Gecko/20100101 Firefox/40.0")
                        .get()
                        .body()
                        .text();
            } catch (IOException e) {
                e.printStackTrace();
            }



            return null;
        }


        protected void onPostExecute(String result){




        }
    }


    private void setRightIndicator(){


        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expandableListView.setIndicatorBounds(width - GetPixelFromDips(90), width - GetPixelFromDips(80));
            //searchExpListView.setIndicatorBounds(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
        } else {
            expandableListView.setIndicatorBoundsRelative(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
            //searchExpListView.setIndicatorBoundsRelative(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
        }

    }

    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }






}
