package com.amedia.campusfulda;

/**
 * Created by Dini on 15.01.2018.
 */


import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;

    public CustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                       HashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        String expandedListText = (String) getChild(listPosition, expandedListPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }

        String infotext = "";
        String regex = ".*$";

        // Create a Pattern object
        Pattern r = Pattern.compile(regex);

        // Now create matcher object.
        Matcher m = r.matcher(expandedListText);
        if (m.find()) {
            //System.out.println("Found value: " + m.group(0));
            infotext = m.group(0).trim();
        }else {
            //System.out.println("NO MATCH");
        }


        //Log.d("DEBUG REGEX", expandedListText.replaceAll(regex, ""));


        TextView expandedListTextView = convertView.findViewById(R.id.expandedListItem);
        TextView expandedListTextViewDescription = convertView.findViewById(R.id.expandedListItemDescription);
        expandedListTextViewDescription.setText(infotext.trim());
        expandedListTextView.setText(expandedListText.replaceAll(regex, "").trim());
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String listTitle = (String) getGroup(listPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }

        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.NORMAL);
        listTitleTextView.setText(listTitle);


        switch(listTitle){

            case "Gebäude":
                listTitleTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_home_black_24dp, 0, 0, 0);
                break;
            case "Wasserspender":
                listTitleTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_local_drink_black_24dp, 0, 0, 0);
                break;
            case "Snacks":
                listTitleTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_free_breakfast_black_24dp, 0, 0, 0);
                break;
            case "Drucker":
                listTitleTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_local_printshop_black_24dp, 0, 0, 0);
                break;
            case "Geldautomaten":
                listTitleTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_euro_symbol_black_24dp, 0, 0, 0);
                break;
            case "Parkplätze":
                listTitleTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_local_parking_black_24dp, 0, 0, 0);
                break;
            case "Fahrradständer":
                listTitleTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_directions_bike_black_24dp, 0, 0, 0);
                break;
            case "Bushaltestellen":
                listTitleTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_directions_bus_black_24dp, 0, 0, 0);
                break;
            case "Freizeit":
                listTitleTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_accessibility_black_24dp, 0, 0, 0);
                break;

        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {

        return true;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {

        return true;
    }


}