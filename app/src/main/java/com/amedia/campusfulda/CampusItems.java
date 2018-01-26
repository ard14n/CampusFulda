package com.amedia.campusfulda;

import android.util.Log;

import java.util.HashMap;

/**
 * Created by Dini on 17.01.2018.
 */

public class CampusItems {


    private String name;
    private double clat, clong;
    private int id;
    private String tags;
    private String opened;
    private String address;
    private String info;
    private HashMap<String, Number> statematrix;



    public CampusItems(){


    }

    public String getName(){

        return this.name;

    }

    public void setName(String name){


        this.name = name;
    }

    public String getTags(){

        return this.tags;

    }

    public void setTags(String tags){

        this.tags = tags;

    }

    public int getId(){

        return this.id;

    }

    public void setId(int id){


        this.id = id;

    }

    public double getLat(){

        return this.clat;

    }

    public void setLat(double clat){
       // Log.d("DEBUUUUUUUUUUUUUUUUUG", "CLAAAAAAAAAAAAAAAAAAAAT "+clat);
        this.clat = clat;

    }

    public double getLong(){

        return this.clong;

    }

    public void setLong(double clong){

        this.clong = clong;

    }

    public String getAddress(){

        return this.address;

    }

    public void setAddress(String address){

        this.address = address;

    }

    public String getInfo(){

        return this.info;

    }

    public void setInfo(String info){

        this.info = info;

    }

    public String getOpened(){

        return this.opened;

    }

    public void setOpened(String opened){

        this.opened = opened;

    }

    public HashMap<String, Number> getStatematrix(){

        return this.statematrix;

    }


    public void setStatematrix(HashMap statematrix){

        this.statematrix = statematrix;

    }


}
