package com.example.asad.volontaireos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class A5_VM extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Button mLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a5_vmaps);

        mLogout=(Button) findViewById(R.id.logouttayub);

 mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent a = new Intent(A5_VM.this,A1_Main.class);
                startActivity(a);
                finish();
                return;
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("VolunteerAvailable");
        GeoFire geoFire = new GeoFire(ref);

        geoFire.setLocation(userId, new GeoLocation(5.35, 100.30), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                if (error != null)
                    System.err.println("Error" + error);
                else
                    System.out.println("Location saved");
            }
        });

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Add marker in USM
        LatLng USM = new LatLng(5.35,100.30);
        mMap.addMarker(new MarkerOptions().position(USM).title("USM"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(USM));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));

        //Add marker in QB
        LatLng QB = new LatLng(5.33,100.30);
        mMap.addMarker(new MarkerOptions().position(QB).title("QB"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(QB));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
    }



}
