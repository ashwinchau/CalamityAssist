package com.calamity_assist.calamityassist;


import android.content.Context;
import android.content.pm.PackageManager;

import android.location.Criteria;
import android.location.Location;

import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.calamity_assist.calamityassist.Location.GetNearbyPlacesData;
import com.calamity_assist.calamityassist.Location.PlaceJSONParser;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class GmapFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {
    private Context mContext;
    private SupportMapFragment supportMapFragment;
    private GoogleMap map;
    public Location mLastLocation;
    private MarkerOptions currentPositionMarker = null;
    private Marker currentLocationMarker;
    private LocationManager locationManager;
    String[] mPlaceType = null;
    String[] mPlaceTypeName = null;


    Spinner mSprPlaceType;
    Button fbtn;

    double mLatitude;
    double mLongitude;


    public GmapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragment_gmap, container, false);

        fbtn = (Button) view.findViewById(R.id.btn_find);

        fbtn.setOnClickListener(this);

        mSprPlaceType = (Spinner) view.findViewById(R.id.spr_place_type);

        // GoogleMap map=((SupportMapFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

        mSprPlaceType = (Spinner) view.findViewById(R.id.spr_place_type);


        // Array of place types
        mPlaceType = getResources().getStringArray(R.array.place_type);

        // Array of place type names
        mPlaceTypeName = getResources().getStringArray(R.array.place_type_name);

        // Creating an array adapter with an array of Place types


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, mPlaceTypeName);

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Getting reference to the Spinner

        // Setting adapter on Spinner to set place types
        mSprPlaceType.setAdapter(adapter);


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mContext = getActivity();

        FragmentManager fm = getActivity().getSupportFragmentManager();/// getChildFragmentManager();
        supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.Gps_map);
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.Gps_map, supportMapFragment).commit();
        }

        supportMapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
// Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();
// Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        final Location myLocation = locationManager.getLastKnownLocation(provider);

       //Toast.makeText(mContext,  myLocation.toString(), Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            map.setMyLocationEnabled(true);

           // updateCurrentLocationMarker(myLocation);


        }


        }
    public void updateCurrentLocationMarker(Location currentLatLng) {
        mLastLocation = currentLatLng;




        if (map != null) {
            if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            map.setMyLocationEnabled(true);

            LatLng latLng = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
            if(currentPositionMarker == null){
                currentPositionMarker = new MarkerOptions();

                currentPositionMarker.position(latLng)
                        .title("My Location").
                        icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));;
                currentLocationMarker = map.addMarker(currentPositionMarker);
                map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                map.animateCamera(CameraUpdateFactory.zoomTo(17));

            }

            mLatitude=mLastLocation.getLatitude();
            mLongitude=mLastLocation.getLongitude();
            Log.d("Lat Long     :", String.valueOf(mLatitude));
            Toast.makeText(mContext, (int) mLatitude, Toast.LENGTH_SHORT).show();


            if(currentLocationMarker != null)
                currentLocationMarker.setPosition(latLng);

            ///currentPositionMarker.position(latLng);
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }


    private String getUrl(double latitude, double longitude, String nearbyPlace) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + 1000);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyATuUiZUkEc_UgHuqsBJa1oqaODI-3mLs0");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }
    @Override
    public void onClick(View v) {
        Log.d("onClick", "Button is Clicked");
        int selectedPosition = mSprPlaceType.getSelectedItemPosition();
        String type = mPlaceType[selectedPosition];

        map.clear();
        if (currentLocationMarker != null) {
            currentLocationMarker.remove();
        }
        String url = getUrl(mLatitude,mLongitude, type);
        Toast.makeText(mContext, url, Toast.LENGTH_SHORT).show();
        Object[] DataTransfer = new Object[2];
        DataTransfer[0] = map;
        DataTransfer[1] = url;
        Log.d("onClick", url);
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        getNearbyPlacesData.execute(DataTransfer);
        //Toast.makeText(this.getActivity(),"Nearby Schools", Toast.LENGTH_LONG).show();
    }

    /*


    @Override
    public void onClick(View v) {
        int selectedPosition = mSprPlaceType.getSelectedItemPosition();
        String type = mPlaceType[selectedPosition];

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        LatLng latLng = new LatLng(mLatitude, mLongitude);
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(17));


        sb.append("location="+mLatitude+","+mLongitude);
        sb.append("&radius"+10000);
        sb.append("&types="+type);
        sb.append("&sensor=true");
        sb.append("&key"+"AIzaSyATuUiZUkEc_UgHuqsBJa1oqaODI-3mLs0");

        Toast.makeText(mContext, sb.append("&key=AIzaSyATuUiZUkEc_UgHuqsBJa1oqaODI-3mLs0"), Toast.LENGTH_SHORT).show();

    }

*/


}
