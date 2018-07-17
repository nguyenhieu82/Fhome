package vn.itplus.AD0318E.Fhome;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    //khai bao bien lay vi tri hien tai
    private FusedLocationProviderClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        client = LocationServices.getFusedLocationProviderClient(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //ban do duoc load thanh cong
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION, 1368);
    }

    public void gotoHanoi(View view) {
        //tao 1 vi tri can di truyen toi
        CameraPosition position = new CameraPosition(new LatLng(28.68, 105.68),12,0,0);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
        addMarker();
        showLayerMap();
    }

    public void zoomOut(View view) {
        mMap.animateCamera(CameraUpdateFactory.zoomOut());
    }

    public void zoomIn(View view) {
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
    }

    public void addMarker(){
        LatLng hanoi = new LatLng(28.68, 105.68);
        mMap.addMarker(new MarkerOptions()
                .position(hanoi)
                .title("ATM")
                .snippet("vietcombank")
        );
    }

    public void showLayerMap(){
        //ban do ve tinh
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    //xay dung phuong thuc req de cap quyen dinh vi nguoi dung
    public  void requestPermission(String permission, int requestCode){
        if(ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED){
            //da duoc cho phep
            // todo lay my location
            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null){
                        //go to my location
                        CameraPosition position = new CameraPosition(new LatLng(location.getLatitude(), location.getLongitude()),12,0,0);
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
                        Toast.makeText(MapsActivity.this, "Lat" + location.getLatitude(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(MapsActivity.this, "Lon" + location.getLongitude(), Toast.LENGTH_SHORT).show();
                        //Log.e("Location", "MyLocation:",)
                    }
                }
            });
        } else {
            //gui yeu cau duoc cap quyen
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{permission},
                    requestCode);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //kiem tra ket qua nguoi dung cho phep hay khong
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
            //nguoi dung cho phep
            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null){
                        //go to my location
                        CameraPosition position = new CameraPosition(new LatLng(location.getLatitude(), location.getLongitude()),12,0,0);
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
                        //Log.e("Location", "MyLocation:",)
                    }
                }
            });
        } else {
            Toast.makeText(this, "Ko cho phep", Toast.LENGTH_SHORT).show();
        }
    }
}
