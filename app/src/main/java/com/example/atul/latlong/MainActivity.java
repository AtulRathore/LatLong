package com.example.atul.latlong;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    TextView textView, textView1;
    LocationManager locationManager;
    double lat, lng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        textView1 = (TextView) findViewById(R.id.textView1);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 5000, 20, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
        textView.setText(""+lat+"\n"+lng);

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.ENGLISH);

        try {
            List<Address> myadd = geocoder.getFromLocation(lat,lng,5);
            Address myFinalAdd = myadd.get(0);
            StringBuilder sb = new StringBuilder();
            for (int i=0; i<myFinalAdd.getMaxAddressLineIndex(); i++){
                sb.append(myFinalAdd.getAddressLine(i));
            }
            textView1.setText(""+sb);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(this, "On", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(this, "Off", Toast.LENGTH_SHORT).show();
    }
}
