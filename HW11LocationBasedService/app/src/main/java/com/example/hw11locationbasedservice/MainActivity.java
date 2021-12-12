package com.example.hw11locationbasedservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    Button btnMapView, btnSatView, btnSubmit, btnCurrentLocation;
    EditText etNewLocation;
    GoogleMap mMap;
    Geocoder geocoder;
    final private static int REQUEST_COARSE_ACCESS = 123;
    LocationListener locationListener;
    boolean permissionGranted = false;

    private LatLng currentLocation;
    LocationManager lm;

    @Override
    protected void onPause() {
        super.onPause();

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_COARSE_ACCESS);
            return;
        }else{
            permissionGranted = true;
        }
        if(permissionGranted) {
            lm.removeUpdates(locationListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMapView      = findViewById(R.id.buttonMapView);
        btnSatView      = findViewById(R.id.buttonSatView);
        etNewLocation   = findViewById(R.id.editTextNewLocation);
        btnSubmit       = findViewById(R.id.buttonSubmit);
        btnCurrentLocation  =   findViewById(R.id.buttonCurrentLocation);

        geocoder =  new Geocoder(getApplicationContext());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    //Suppressing because it is setup correctly
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap =  googleMap;

        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();

        if(checkLocationPermission(this)){
            Log.d("ListenerSetup", "onMapReady: setting up listener");

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, locationListener);
        }else Log.d("ListenerSetup", "onMapReady: wont setup listener");

        btnMapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            }
        });

        btnSatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitLocation();
            }


        });

        btnCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentLocation!= null){
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(7));
                } else
                    Toast.makeText(getApplicationContext(), "Current Location Not updated", Toast.LENGTH_LONG ).show();
            }
        });

        etNewLocation.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    return submitLocation();
                }
                return false;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                try {
                    List<Address> addresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
                    String add = "";
                    if (addresses.size() > 0)
                    {
                        for (int i=0; i<addresses.get(0).getMaxAddressLineIndex(); i++)
                            add += addresses.get(0).getAddressLine(i) + "\n";
                    }
                    Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT).show();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                LatLng p = new LatLng(latLng.latitude, latLng.longitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(p));
                mMap.addMarker(new MarkerOptions().position(latLng));

            }
        });


    }

    private boolean submitLocation() {
        try {
            List<Address> addresses = geocoder.getFromLocationName(etNewLocation.getText().toString(), 3);
            Address address = addresses.get(0);
            LatLng place = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(place));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(7));

            return true;
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),"Location Not found", Toast.LENGTH_LONG).show();
            return false;
        }

    }

    private class MyLocationListener implements LocationListener
    {
        @Override
        public void onLocationChanged(Location loc) {
            Log.d(this.getClass().getName(), "onLocationChanged: new Location");
            if (loc != null) {
                LatLng p = new LatLng(
                        (int) (loc.getLatitude()),
                        (int) (loc.getLongitude()));
                currentLocation = p;
            } else {
                System.out.println("loc is null");
            }

        }
        public void onProviderDisabled(String provider) {
        }
        public void onProviderEnabled(String provider) {
        }
        public void onStatusChanged(String provider, int status,
                                    Bundle extras) {
        }
    }

    public static boolean checkLocationPermission(Activity activity){
        if(ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_COARSE_ACCESS);
            return false;
        }
        return true;
    }

}



