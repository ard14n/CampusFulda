package com.amedia.campusfulda;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by Dini on 15.01.2018.
 */

public class DataImporterExpandable {


    public DataImporterExpandable(){


    }


    public static HashMap<String, List<String>> getData(List<CampusItems> campuslist) {

        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();


        List<String> gebauede = new ArrayList<String>();
        List<String> wasserspender = new ArrayList<String>();
        List<String> snacks = new ArrayList<String>();
        List<String> drucker = new ArrayList<String>();
        List<String> geldautomaten = new ArrayList<String>();
        List<String> parkplaetze = new ArrayList<String>();
        List<String> bushaltestellen = new ArrayList<String>();
        List<String> fahrradstaender = new ArrayList<String>();
        List<String> freizeit = new ArrayList<String>();


        expandableListDetail.put("Freizeit", freizeit);
        expandableListDetail.put("Fahrradständer", fahrradstaender);
        expandableListDetail.put("Bushaltestellen", bushaltestellen);
        expandableListDetail.put("Parkplätze", parkplaetze);
        expandableListDetail.put("Geldautomaten", geldautomaten);
        expandableListDetail.put("Drucker", drucker);
        expandableListDetail.put("Snacks", snacks);
        expandableListDetail.put("Wasserspender", wasserspender);
        expandableListDetail.put("Gebäude", gebauede);




        for (ListIterator<CampusItems> iter = campuslist.listIterator(); iter.hasNext(); ) {
            CampusItems c = iter.next();

            HashMap<String, Number> statematrix = c.getStatematrix();
            int s_gebaeude = (int) statematrix.get("gebaeude");
            int s_wasser = (int) statematrix.get("wasserspender");
            int s_snacks = (int) statematrix.get("snacks");
            int s_drucker = (int) statematrix.get("drucker");
            int s_geld = (int) statematrix.get("geld");
            int s_park = (int) statematrix.get("park");
            int s_bus = (int) statematrix.get("bus");
            int s_fahrrad = (int) statematrix.get("fahrrad");
            int s_freizeit = (int) statematrix.get("freizeit");


            if(s_gebaeude == 1){

                gebauede.add(c.getName()+"\n "+c.getInfo());

            }

            if(s_wasser == 1){

                wasserspender.add(c.getName()+"\n "+c.getInfo());

            }

            if(s_snacks == 1){

                snacks.add(c.getName()+"\n "+c.getInfo());

            }

            if(s_drucker == 1){

                drucker.add(c.getName()+"\n "+c.getInfo());

            }

            if(s_geld == 1){

                geldautomaten.add(c.getName()+"\n "+c.getInfo());

            }

            if(s_park == 1){

                parkplaetze.add(c.getName()+"\n "+c.getInfo());

            }

            if(s_bus == 1){

                bushaltestellen.add(c.getName()+"\n "+c.getInfo());

            }

            if(s_fahrrad == 1){

                fahrradstaender.add(c.getName()+"\n "+c.getInfo());

            }
            if(s_freizeit == 1){

                freizeit.add(c.getName()+"\n "+c.getInfo());

            }


        }


        return expandableListDetail;


    }







}
