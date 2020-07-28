package com.example.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends WearableActivity {

    private Button btLocation;
    private TextView textView1, textView2, textView3, textView4, textView5;
    private FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btLocation = (Button) findViewById(R.id.bt_location);
        textView1 = (TextView) findViewById(R.id.text_view1);
        textView2 = (TextView) findViewById(R.id.text_view2);
        textView3 = (TextView) findViewById(R.id.text_view3);
        textView4 = (TextView) findViewById(R.id.text_view4);
        textView5 = (TextView) findViewById(R.id.text_view5);

//        textView1.setText(Html.fromHtml(
//                "<font><b><Latitude :</b><br/></font>"
//        ));
//        textView2.setText(Html.fromHtml(
//                "<font><b><Longitude :</b><br/></font>"
//        ));
//        textView3.setText(Html.fromHtml(
//                "<font><b><Country Name :</b><br/></font>"
//        ));
//        textView4.setText(Html.fromHtml(
//                "<font><b><Locality :</b><br/></font>"
//        ));
//        textView5.setText(Html.fromHtml(
//                "<font><b><Address :</b><br/></font>"
//        ));

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("SAJIB: BT CLICKED");
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

        // Enables Always-on
//        setAmbientEnabled();
    }

    private void getLocation() {
        System.out.println("SAJIB : GET LOCATION: called");
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
        System.out.println("SAJIB : GET LOCATION: permission ase!");
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                System.out.println("SAJIB : GET LOCATION: getLastLocation korte parse");
                Location location = task.getResult();
                System.out.println("SAJIB : GET LOCATION: task.getResult()");
                if (location != null) {
                    System.out.println("SAJIB : GET LOCATION: Location null na");
                    Geocoder geocoder = new Geocoder(MainActivity.this,
                            Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );
                        textView1.setText("Latitude : " + addresses.get(0).getLatitude());
                        textView1.setText("Longitude : " + addresses.get(0).getLongitude());
                        textView1.setText("Country : " + addresses.get(0).getCountryName());
                        textView1.setText("Locality : " + addresses.get(0).getLocality());
                        textView1.setText("Address : " + addresses.get(0).getAddressLine(0));

                        System.out.print("TRY: "+ textView1.getText() + " " + textView2.getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("SAJIB : GET LOCATION: CATCH");
                    }
                }
            }
        });
    }
}
