package com.amedia.campusfulda.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.amedia.campusfulda.CampusItems;
import com.amedia.campusfulda.CustomExpandableListAdapter;
import com.amedia.campusfulda.DataImporterExpandable;
import com.amedia.campusfulda.DatabaseHelper;
import com.amedia.campusfulda.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Dini on 15.01.2018.
 *
 * Zeigt die ExpandableListView mit den Kategorien für die Suche an
 *
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


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_category, container, false);

        DatabaseHelper myDatabase = DatabaseHelper.createInstance(getContext(), "so_systems.db", 1);

        expandableListView = view.findViewById(R.id.expandableListView);

        //Holt die Daten aus der Datenbank als List mit den CampusItems
        final List<CampusItems> campuslist = myDatabase.getAllItems();

        //Übergibt die CampusList dem DataImporterExpandable
        expandableListDetail = DataImporterExpandable.getData(campuslist);

        //Holt die Keys, die gleichzeitig auch die Kategorien sind und setzt sie als ListTitle
        expandableListTitle =  new ArrayList<String>(expandableListDetail.keySet());

        expandableListAdapter = new CustomExpandableListAdapter(getContext(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);


        //Der OnclickListener für die Untermenüpunkte der ExpandableListView
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                String context = expandableListTitle.get(groupPosition);
                String subject = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition);


                //Erzeugt einen neuen Bundle und übergibt die benötigten Daten damit sie in dem
                //nächsten Fragment angezeigt werden können
                Bundle b = new Bundle();


                //Durchläuft die CampusItems bis das Subject der Untermenüpunkte in der CampusItemsList gefunden wurde
                for (ListIterator<CampusItems> iter = campuslist.listIterator(); iter.hasNext(); ) {
                    CampusItems c = iter.next();

                    //DEBUG
                    //Log.d("ICH BIN DEBUUUUUUUUUUUG", "NAMEEEE: "+c.getName()+" SUBJEEEEECT: "+subject);

                    if(subject.equals(c.getName()+"\n "+c.getInfo())){

                        b.putString("context", context);
                        b.putString("subject", c.getName());
                        b.putString("info", c.getInfo());
                        b.putString("address", c.getAddress());
                        b.putString("tags", c.getTags());
                        b.putString("opened", c.getOpened());
                        b.putDouble("clat", c.getLat());
                        b.putDouble("clong", c.getLong());

                        break;

                    }

                }


                FragmentManager fmanager = getActivity().getSupportFragmentManager();
                FragmentTransaction ftransaction = fmanager.beginTransaction();

                //Erzeugt ein neues Objekt der CategoryItemDetailsFragment-Klasse
                CategoryItemDetailsFragment itemdetails = new CategoryItemDetailsFragment();

                //Übergibt das Bundle an das Fragment
                itemdetails.setArguments(b);

                //Ersetzt den FragmentContainer und ruft das Fragment auf
                ftransaction.replace(R.id.flContent, itemdetails);
                ftransaction.addToBackStack(null);
                ftransaction.commit();

                //DEBUG
                /*Toast.makeText(
                        getContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();*/
                return false;
            }
        });

        setRightIndicator();


        return view;

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }



    //Setzt den Indicator für die ListView nach rechts anstatt nach Links
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
