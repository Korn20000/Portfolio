package com.example.korn.hoc.HOC.HOC_ASSIGNMENT;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.example.korn.hoc.R;

/**
 * Created by KoRn on 15-03-2018.
 *
 *
 * Logic should be correct for showing device location, but does not work!
 *
 */

public class Maps extends AppCompatActivity implements OnMapReadyCallback
{

    public static final String CHECK_SERVICE = "PhotosetViewer";
    public static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String TAG = "Map";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean locationPermission = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private GoogleMap gMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final float DEFAULT_ZOOM = 15f;


    //check if services are ok, if they are ok request map
    public boolean isServicesOK()
    {
        Log.d(CHECK_SERVICE, "IS OK: Checking version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Maps.this);

        if (available == ConnectionResult.SUCCESS)
        {
            Log.d(CHECK_SERVICE, "IS OK: Everything is ok");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) ;
        {
            Log.d(CHECK_SERVICE, "IS NOT OK: Error occured");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Maps.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }

        Toast.makeText(this, "You cannot request map", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
    }

    private void getLocationPermission()
    {

        String[] permission = {FINE_LOCATION, COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED)
        {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED)
            {
                locationPermission = true;
                initMap();
            }
            else
            {
                ActivityCompat.requestPermissions(this, permission, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        locationPermission = false;

        switch (requestCode)
        {
            case LOCATION_PERMISSION_REQUEST_CODE:
                {
                for (int i = 0; i < grantResults.length; i++)
                {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED)
                    {
                        locationPermission = false;
                        return;
                    }
                }
                locationPermission = true;
            }
        }
    }

    private void initMap()
    {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(Maps.this);
    }

    private void getDeviceLocation()
    {
        Log.d(TAG, "Device location: Retrieving current location");

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try
        {
            if (locationPermission)
            {
                Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener((new OnCompleteListener()
                {
                    @Override
                    public void onComplete(@NonNull Task task)
                    {
                        if (task.isSuccessful())
                        {
                            Log.d(TAG, "onComplte: Found location");
                            Location currentLocation = (Location) task.getResult();

                            panMap(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);
                        }
                        else
                        {
                            Log.d(TAG, "onComplte: Current location is null");
                            Toast.makeText(Maps.this, "Unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                }));
            }

        }
        catch (SecurityException e)
        {
            Log.e(TAG, "Device location: Security exception " + e.getMessage());

        }
    }

    private void panMap(LatLng latLng, float zoom)
    {
        Log.d(TAG, "panMap: Moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }


    @Override
    public void onMapReady(GoogleMap googleMap)
    {

        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: Map is ready");
                gMap = googleMap;

                if (locationPermission)
                {
                    getDeviceLocation();

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED)
                    {

                return;
            }
            gMap.setMyLocationEnabled(true);

        }
    }
}
