package com.amedia.campusfulda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;

/*
*   DataImporterExpandable ist dafür zuständig, die Elemente und Kategorien
*   zu erstellen und zuzuordnen und dann als HashMap zurückzugeben
*
*
* */

public class DataImporterExpandable {


    public DataImporterExpandable(){


    }


    public static HashMap<String, List<String>> getData(List<CampusItems> campuslist) {

        //ExpandableListDetail beinhaltet alle Elemente als ArrayLists, welche den Kategorien entsprechen
        //Jene Kategorien enthalten alle Elemente des CampusPlans
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

        //StringList für die Elemente der einzelnen Kategorien
        List<String> gebauede = new ArrayList<String>();
        List<String> wasserspender = new ArrayList<String>();
        List<String> snacks = new ArrayList<String>();
        List<String> drucker = new ArrayList<String>();
        List<String> geldautomaten = new ArrayList<String>();
        List<String> parkplaetze = new ArrayList<String>();
        List<String> bushaltestellen = new ArrayList<String>();
        List<String> fahrradstaender = new ArrayList<String>();
        List<String> freizeit = new ArrayList<String>();


        //Die Kategorien und Elemente werden der HashMap hinzugefügt
        expandableListDetail.put("Gebäude", gebauede);
        expandableListDetail.put("Wasserspender", wasserspender);
        expandableListDetail.put("Parkplätze", parkplaetze);
        expandableListDetail.put("Geldautomaten", geldautomaten);
        expandableListDetail.put("Drucker", drucker);
        expandableListDetail.put("Snacks", snacks);
        expandableListDetail.put("Fahrradständer", fahrradstaender);
        expandableListDetail.put("Bushaltestellen", bushaltestellen);
        expandableListDetail.put("Freizeit", freizeit);

        //Alle Elemente werden den zugehörigen StringLists hinzugefügt
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

            //Die einzelnen Elemente werden den Kategorien zugewiesen
            //Die Statematrix hilft hierbei
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
