package com.example.sapui.googlemapexample;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private HashMap<Marker, MyMarker> mMarkersHashMap;
    private ArrayList<MyMarker> mMyMarkersArray = new ArrayList<MyMarker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        addMarker();
        setUpMapIfNeeded();

        plotMarkers(mMyMarkersArray);


    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            } else {
                Toast.makeText(getApplicationContext(), "Unable to create Maps",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
//        mMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(10, 10))
//                        .title("Marker")
//                        .visible(true)
//                        .alpha(0.8f)
//        );
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return false;
            }
        });

    }

    private void addMarker() {
        // Initialize the HashMap for Markers and MyMarker object
        mMarkersHashMap = new HashMap<Marker, MyMarker>();

        mMyMarkersArray.add(new MyMarker("Brasil", "icon1", Double.parseDouble("-28.5971788"), Double.parseDouble("-52.7309824")));
        mMyMarkersArray.add(new MyMarker("United States", "icon2", Double.parseDouble("33.7266622"), Double.parseDouble("-87.1469829")));
        mMyMarkersArray.add(new MyMarker("Canada", "icon3", Double.parseDouble("51.8917773"), Double.parseDouble("-86.0922954")));
        mMyMarkersArray.add(new MyMarker("England", "icon4", Double.parseDouble("52.4435047"), Double.parseDouble("-3.4199249")));
        mMyMarkersArray.add(new MyMarker("España", "icon5", Double.parseDouble("41.8728262"), Double.parseDouble("-0.2375882")));
        mMyMarkersArray.add(new MyMarker("Portugal", "icon6", Double.parseDouble("40.8316649"), Double.parseDouble("-4.936009")));
        mMyMarkersArray.add(new MyMarker("Deutschland", "icon7", Double.parseDouble("51.1642292"), Double.parseDouble("10.4541194")));
        mMyMarkersArray.add(new MyMarker("Atlantic Ocean", "icondefault", Double.parseDouble("-13.1294607"), Double.parseDouble("-19.9602353")));
    }

    private void plotMarkers(ArrayList<MyMarker> markers) {
        if (markers.size() > 0) {
            for (MyMarker myMarker : markers) {

                // Create user marker with custom icon and other options
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getmLatitude(), myMarker.getmLongitude()));
                markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.currentlocation_icon));

                Marker currentMarker = mMap.addMarker(markerOption);
                mMarkersHashMap.put(currentMarker, myMarker);

                mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
            }
        }
    }

    private int manageMarkerIcon(String markerIcon) {
        if (markerIcon.equals("icon1"))
            return R.drawable.currentlocation_icon;
        else if (markerIcon.equals("icon2"))
            return R.drawable.currentlocation_icon;
        else if (markerIcon.equals("icon3"))
            return R.drawable.currentlocation_icon;
        else if (markerIcon.equals("icon4"))
            return R.drawable.currentlocation_icon;
        else if (markerIcon.equals("icon5"))
            return R.drawable.currentlocation_icon;
        else if (markerIcon.equals("icon6"))
            return R.drawable.currentlocation_icon;
        else if (markerIcon.equals("icon7"))
            return R.drawable.currentlocation_icon;
        else
//            return R.drawable.icondefault;
            return R.drawable.currentlocation_icon;
    }


    public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        public MarkerInfoWindowAdapter() {
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            View v = getLayoutInflater().inflate(R.layout.infowindow_layout, null);

            MyMarker myMarker = mMarkersHashMap.get(marker);

            ImageView markerIcon = (ImageView) v.findViewById(R.id.marker_icon);

            TextView markerLabel = (TextView) v.findViewById(R.id.marker_label);

            markerIcon.setImageResource(manageMarkerIcon(myMarker.getmIcon()));

            markerLabel.setText(myMarker.getmLabel());

            return v;
        }

    }
}

