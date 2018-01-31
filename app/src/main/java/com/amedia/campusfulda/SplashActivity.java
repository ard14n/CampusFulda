package com.amedia.campusfulda;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.amedia.campusfulda.fragments.CategoryFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Dini on 18.01.2018.
 *
 * SplashActivity zeigt das Logo der App an und holt
 * beim ersten Start der App die Daten vom Server,
 * und schreibt sie in die Datenbank.
 * Falls die Datenbank existiert wird die SplashActivity
 * bei allen anderen Starts nicht mehr angezeigt.
 *
 */

public class SplashActivity extends AppCompatActivity {


    private String json;
    private DatabaseHelper dbhelper;
    private boolean isNetworkAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        isNetworkAvailable = isDataConnectionAvailable(this);

        //Falls Datenbank existiert dann wird die MainActivity aufgerufen,
        //falls nicht wird die Datenbank erstellt und der Request zum Server wird gemacht
        if(doesDbExist(this, "so_systems.db")){

            //DEBUG
            //Toast.makeText(this, "Ich existiere", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, PermissionActivity.class);
            startActivity(intent);
            finish();

        } else {

            //Überprüft App auf Internetverbindung
            if (isNetworkAvailable){
                //DEBUG
                //Toast.makeText(this, "Ich werde erstellt", Toast.LENGTH_SHORT).show();
                dbhelper = DatabaseHelper.createInstance(this, null,  1);

                new GetDataJSON().execute();

            } else {

                Toast.makeText(this, "Sorry, aber ohne Internet kommen wir hier nicht weiter", Toast.LENGTH_LONG);

            }


        }
    }

    //AsyncTask um die Daten als JSON String vom Server zu holen und in die Datenbank zu schreiben
    private class GetDataJSON extends AsyncTask<Void, Void, String> {

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

            JSONArray array = null;
            try {

                array = new JSONArray(json);

                for (int i = 0; i < array.length(); i++){

                    JSONObject obj = array.getJSONObject(i);

                    String name = obj.getString("name");
                    String oezeiten = obj.getString("oezeiten");
                    String adress = obj.getString("adresse");
                    String info = obj.getString("info");
                    double clat = obj.getDouble("lat");
                    double clong = obj.getDouble("long");
                    int gebaeude = obj.getInt("isgebaeude");
                    int wasser = obj.getInt("iswasser");
                    int snack = obj.getInt("issnack");
                    int drucker = obj.getInt("isdrucker");
                    int geld = obj.getInt("isgeld");
                    int park = obj.getInt("ispark");
                    int bus = obj.getInt("isbus");
                    int fahrrad = obj.getInt("isfahrrad");
                    int freizeit = obj.getInt("isfreizeit");
                    String tags = obj.getString("tags");

                    dbhelper.insertData(name, oezeiten, adress, info, tags, clat, clong, gebaeude, wasser, snack, drucker, geld, park, bus, fahrrad, freizeit);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String result) {

            List<CampusItems> campuslist = dbhelper.getAllItems();
            DataImporterExpandable.getData(campuslist);

            Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
            startActivity(intent);
            finish();

        }

    }

    /*
    * Überprüft ob die Datenbank schon vorhanden ist
    * Falls Datenbank vorhanden ist, dann gibt die Methode true zurück,
    * falls nicht, dann false
    *
    * @param context
    * @param dbname Vom Typ String und ist der Name der gesuchten Datenbank
    *
    * @return boolean
    * */
    private static boolean doesDbExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }



    public static boolean isDataConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }



}
