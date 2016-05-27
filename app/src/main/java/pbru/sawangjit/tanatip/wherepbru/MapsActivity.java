package pbru.sawangjit.tanatip.wherepbru;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String[] resultStrings;
    private int[] iconInts = {R.drawable.build1, R.drawable.build2,
            R.drawable.build3, R.drawable.build4, R.drawable.build5,
            R.drawable.build6,};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        resultStrings = getIntent().getStringArrayExtra("Result");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }   //Main method

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Setup center Map
        setupCenterMap();


    }   //onMapReady

    private void setupCenterMap() {

        double doulat = Double.parseDouble(resultStrings[3]);
        double duuLng = Double.parseDouble(resultStrings[4]);

        LatLng latLng = new LatLng(doulat, duuLng);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

        mMap.addMarker(new MarkerOptions()
        .position(latLng)
        .icon(BitmapDescriptorFactory
        .fromResource(iconInts[Integer.parseInt(resultStrings[5])]))
        .title("ห้อง " + resultStrings[2])
        .snippet("อยู่ที่ อาคาร" + resultStrings[1]));


    }   //setup center

}   //Mainclass


