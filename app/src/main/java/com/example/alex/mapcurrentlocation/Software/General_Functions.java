package com.example.alex.mapcurrentlocation.Software;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.alex.mapcurrentlocation.Database.DatabaseFunctions;

import java.util.ArrayList;

/**
 * Created by James Nikolaidis on 10/19/2016.
 */
public class General_Functions {

    private static double  lon,lat ;
    private  static  Context APPLICATION_CONTEXT;
    private static double THRESHOLD_DISTANCE = 0.5; // IN KM
    private DatabaseFunctions databaseFunctions ;

    public void GeneralFunctions(Context context){
        APPLICATION_CONTEXT=context;
    }


    public void GiveLan_Lon(double ...coordinates){
        lon = coordinates[0];
        lat = coordinates[1];
    }



    public ArrayList<String> CompareLon_And_Lat(){
        //COMPARES USERS LAT,LON WITH THE STORES LAN-LON COORDINATES INSIDE THE DATABASE , AND RETURN A ARRAY LIST WITH THE VALUES OF
        //THE SHOPS THAT ARE WITHIN THE PERMITTED THRESHOLD DISTANCE.
        databaseFunctions = DatabaseFunctions.getInstance(APPLICATION_CONTEXT);
        ArrayList<String>Passes_Stores = new ArrayList<>();
        int counter1;


        if(databaseFunctions!=null){
            Cursor database_data = databaseFunctions.GetDatabase_ALL_DATA();
            database_data.moveToFirst();
            int i=0;

        do{
/*
FindDistance(lat,Double.parseDouble(database_data.getString(3)),lon,Double.parseDouble(database_data.getString(4)))
 */
                 double dlat = Double.valueOf(database_data.getString(3));
                 double dlon = Double.valueOf(database_data.getString(4));
                 double fin = FindDistance(lat,dlat,lon,dlon);



                if(fin<0.500) {


                    try {
                        Passes_Stores.add(database_data.getString(database_data.getColumnIndex("LONGTITUDE")));
                        Passes_Stores.add(database_data.getString(database_data.getColumnIndex("LATITUDE")));
                        Passes_Stores.add(database_data.getString(database_data.getColumnIndex("ONOMA")));
                    } catch (Exception ex) {
                        Log.d("System_Message", "Insert was UnSuccesfull");
                    }

                }
                database_data.moveToNext();
                i++;


            }while(i<database_data.getCount());

        }


        return Passes_Stores;
    }


    //*********** Function which return the distance in meters on two
    // LatLng positions

    public double FindDistance(double ...coordinates) {

        double R = 6371.000; // metres
        double f1 = coordinates[0] * (3.1415926535897932 / 180);
        double f2 = coordinates[1] * (3.1415926535897932 / 180);
        double df = (coordinates[1] - coordinates[0]) * (3.1415926535897932 / 180);
        double dl = (coordinates[3] - coordinates[2]) * (3.1415926535897932 / 180);

        double a = Math.sin(df / 2) * Math.sin(df / 2) +
                Math.cos(f1) * Math.cos(f2) *
                        Math.sin(dl / 2) * Math.sin(dl / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double d = R * c;
        Log.d("Life",String.valueOf(d));

        return d;
    }
    //*********************END





}
