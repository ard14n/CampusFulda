package com.amedia.campusfulda;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


/*
*   Ist für den Berechtigungsscreen zuständig und regelt die Logik
*
*
*
* */

public class PermissionActivity extends AppCompatActivity {

    private Button continue_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_permission);

        continue_button = findViewById(R.id.continue_button);

        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                askForPermission();

            }
        });

        //Abfrage für den Fall wenn die Berechtigung noch nicht erteilt wurde
        //Im If-Teil wird nichts abgefragt, da das Fenster erst nach dem Weiter-Klick erscheinen soll
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //DEBUG
            //Toast.makeText(this, "Überprüfe bitte die Berechtigungen deiner App", Toast.LENGTH_SHORT).show();


        } else {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        }


    }

    //Zeigt das Berechtigungsfenster an
    private void askForPermission(){

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }


    /*
    * OnPermissionResult ist die Callback Methode für requestPermission und
    * ist für den Fall zuständig, wenn der Nutzer eine Eingabe im Berechtigungsfenster getätigt hat
    *
    *
    * */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();


                }
                return;
            }

        }
    }



}
