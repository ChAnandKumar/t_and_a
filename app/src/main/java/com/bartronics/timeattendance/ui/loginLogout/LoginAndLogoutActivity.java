package com.bartronics.timeattendance.ui.loginLogout;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bartronics.timeattendance.R;
import com.bartronics.timeattendance.Util.Constant;
import com.bartronics.timeattendance.Util.widget.SlideToUnlock;
import com.bartronics.timeattendance.model.ReportModel;
import com.bartronics.timeattendance.model.db.DbHandler;
import com.bartronics.timeattendance.ui.report.ReportActivity;
import com.bartronics.timeattendance.ui.show_profile.ShowProfileActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bartronics.timeattendance.Util.Constant.EmpNo;
import static com.bartronics.timeattendance.Util.Constant.MyPREFERENCES;

public class LoginAndLogoutActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener ,OnMapReadyCallback, SlideToUnlock.OnSlideToUnlockEventListener {

    private static final String TAG = "anand";
    @BindView(R.id.getLocation_button)
    Button getLocationButton;

    @BindView(R.id.empId_tv)
    TextView empId_tv;

    @BindView(R.id.current_date_txt)
    TextView current_date_txt;

    @BindView(R.id.current_location_txt)
    TextView current_location_txt;

    private GoogleMap mMap;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;

    private LocationManager locationManager;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private SlideToUnlock slideToUnlockView1,slideToUnlockView2;
    private DbHandler db;
    private SharedPreferences sharedpreferences;
    private String empNo;
    private String district;
    private String empName;
    private String empWorkLocation;
    private String empDepartment;
    private String empDesignaiton;
    private boolean in_out_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_logout);
        ButterKnife.bind(this);



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        else if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showGPSDisabledAlertToUser();
        }


        /*slideToUnlock = (SlideToUnlock) findViewById(R.id.slidetounlock);
        slideToUnlock.setOnUnlockListener(this);*/
        slideToUnlockView1 = (SlideToUnlock) findViewById(R.id.slideToUnlock);
        slideToUnlockView1.setExternalListener(this);

        slideToUnlockView2 = (SlideToUnlock) findViewById(R.id.slideToUnlock2);
        slideToUnlockView2.setExternalListener(this);

        db = new DbHandler(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        boolean check_in_out =sharedpreferences.getBoolean(Constant.IN_OUT_CHECK,false);

        if(check_in_out){
            slideToUnlockView1.setVisibility(View.GONE);
            slideToUnlockView2.setVisibility(View.VISIBLE);
            //in_out_check = true;
        }else{
            slideToUnlockView1.setVisibility(View.VISIBLE);
            slideToUnlockView2.setVisibility(View.GONE);
            //in_out_check = false;
        }

        empNo = sharedpreferences.getString(EmpNo, "");

        empId_tv.setText("Employee Id : "+empNo);
        loadOldData();


        Calendar cal = Calendar.getInstance();

        int millisecond = cal.get(Calendar.MILLISECOND);
        int second = cal.get(Calendar.SECOND);
        int minute = cal.get(Calendar.MINUTE);
        //12 hour format
        int hour = cal.get(Calendar.HOUR);
        //24 hour format
        int hourofday = cal.get(Calendar.HOUR_OF_DAY);

        int dayofyear = cal.get(Calendar.DAY_OF_YEAR);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        int dayofmonth = cal.get(Calendar.DAY_OF_MONTH);

        current_date_txt.setText(dayofmonth+"/"+month+"/"+year);

       /* Log.d(TAG, "onCreate: db data");
        List<ReportModel> list = db.getAllEmps();
        for (int i = 0; i < list.size(); i++) {
            Log.i(TAG, "name: "+list.get(i).getEmpName());
            Log.i(TAG, "department: "+list.get(i).getEmpDepartment());
            Log.i(TAG, "designation: "+list.get(i).getEmpDesignaiton());
            Log.i(TAG, "pin: "+list.get(i).getEmpNumber());
        }
        db.close();*/

    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);


                        //mapFrag.getMapAsync(MainActivity.this);
                    }
                });

        alertDialogBuilder.setNegativeButton("CAncel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        //alertDialogBuilder.setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());
        AlertDialog alert = alertDialogBuilder.create();
        if(alert.isShowing()) {
            alert.dismiss();
        }
        alert.show();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
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
        mMap.setMyLocationEnabled(true);

        //add this here:
        buildGoogleApiClient();

        //LatLng loc = new LatLng(lat, lng);
        //mMap.addMarker(new MarkerOptions().position(loc).title("New Marker"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
    }

    protected synchronized void buildGoogleApiClient() {


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_screen_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.profile:
                startActivity(new Intent(this, ShowProfileActivity.class));
                return true;
            case R.id.report:
                startActivity(new Intent(this,ReportActivity.class));
                return true;

            case R.id.logout:
                Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
               /* SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(Constant.IN_OUT_CHECK,false);
                editor.commit();*/
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.getLocation_button)
    public void onClick() {

        if(checkLocationPermission()) {
            if (mGoogleApiClient != null) {
                mGoogleApiClient.connect();
            } else {
                buildGoogleApiClient();
            }
        }else {
            showGPSDisabledAlertToUser();
        }
        /*gps = new TrackGPS(LoginAndLogoutActivity.this);


        if(gps.canGetLocation()){


            longitude = gps.getLongitude();
            latitude = gps .getLatitude();

            Toast.makeText(getApplicationContext(),"Longitude:"+Double.toString(longitude)+"\nLatitude:"+Double.toString(latitude),Toast.LENGTH_SHORT).show();
        }
        else
        {

            gps.showSettingsAlert();
        }*/
    }

    /*@Override
    public void onConnected(@Nullable Bundle bundle) {
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
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();

            LatLng loc = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(loc).title("New Marker"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Lost", Toast.LENGTH_SHORT).show();
    }*/


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                showGPSDisabledAlertToUser();
            }

            return true;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "onConnected: is called");
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "onConnected: is called and premission granted");

           /* LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);*/
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null){
                Log.i(TAG, "onConnected: is called and premission granted and mLastLocation");
                //getWeatherAcyc(String.valueOf(mLastLocation.getLatitude()), String.valueOf(mLastLocation.getLongitude()));

                showMap(mLastLocation.getLatitude(),mLastLocation.getLongitude());

                String address =  getAddress(mLastLocation.getLatitude(),mLastLocation.getLongitude());
                current_location_txt.setText("Current Location:\n"+address);
                //Toast.makeText(this, ""+ mLastLocation.getLatitude() +"\n"+String.valueOf(mLastLocation.getLongitude())+"\n"+address, Toast.LENGTH_SHORT).show();
            }
        }
    }

    String getAddress(double lat,double lng){
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        try {
            List<Address> address = geoCoder.getFromLocation(lat, lng, 1);
            int maxLines = address.get(0).getMaxAddressLineIndex();
            for (int i=0; i<maxLines; i++) {
                String addressStr = address.get(0).getAddressLine(i);
                builder.append(addressStr);
                builder.append(" ");
            }

            district =  address.get(0).getAddressLine(1)+","+address.get(0).getAddressLine(2)+","+address.get(0).getLocality();
            String fnialAddress = builder.toString(); //This is the complete address.

            return district;
        } catch (IOException e) {}
        catch (NullPointerException e) {}
        return  null;
    }


    private void showMap(double latitude, double longitude) {
        LatLngBounds latLngBounds;
        LatLng latLng = new LatLng(latitude,longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
        //mMap.setLatLngBoundsForCameraTarget();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Connection Suspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "ConnectionFailed ", Toast.LENGTH_SHORT).show();
    }

    protected void onStop() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

 /*   @Override
    public void onUnlock() {
        Toast.makeText(this, "slided...", Toast.LENGTH_SHORT).show();
    }
*/
    @Override
    public void onSlideToUnlockCanceled() {
        Toast.makeText(this, "onSlideToUnlockCanceled...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSlideToUnlockDone() {


        SharedPreferences.Editor editor = sharedpreferences.edit();

        //Toast.makeText(this, "slided...", Toast.LENGTH_SHORT).show();
        if(slideToUnlockView1.getVisibility() == View.VISIBLE){
            slideToUnlockView1.setVisibility(View.GONE);
            slideToUnlockView2.setVisibility(View.VISIBLE);
            in_out_check = true;
        }else if(slideToUnlockView2.getVisibility() == View.VISIBLE){
            slideToUnlockView1.setVisibility(View.VISIBLE);
            slideToUnlockView2.setVisibility(View.GONE);
            in_out_check = false;
        }

        editor.putBoolean(Constant.IN_OUT_CHECK,in_out_check);
        editor.commit();
        db.addEmpSwipeInAndSwipeOut(loadDataForUpdate());
    }

    ReportModel loadDataForUpdate(){

        Calendar cal = Calendar.getInstance();

        int millisecond = cal.get(Calendar.MILLISECOND);
        int second = cal.get(Calendar.SECOND);
        int minute = cal.get(Calendar.MINUTE);
        //12 hour format
        int hour = cal.get(Calendar.HOUR);
        //24 hour format
        int hourofday = cal.get(Calendar.HOUR_OF_DAY);

        int dayofyear = cal.get(Calendar.DAY_OF_YEAR);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        int dayofmonth = cal.get(Calendar.DAY_OF_MONTH);

       // Toast.makeText(this, district +"\ntoday time : "+hourofday+":"+ minute+"\n"+dayofmonth+"/"+(month+1)+"/"+year, Toast.LENGTH_SHORT).show();

        ReportModel emp = new ReportModel();
        emp.setEmpId(Integer.parseInt(empNo));
        emp.setEmpDepartment(empDepartment);
        emp.setEmpName(empName);
        emp.setEmpDesignaiton(empDesignaiton);
        emp.setEmpWorkLocation(empWorkLocation);

        emp.setEmpLocation(district);

        String min;

        if(minute <10){
            min = 0+""+minute;
        }else {
            min = minute+"";
        }

        emp.setEmpInDate(dayofmonth+"/"+(month+1)+"/"+year);
        if(slideToUnlockView1.getVisibility() == View.VISIBLE) {
            emp.setEmpInTime("");
            emp.setEmpOutTime(hourofday + ":" + min);
        }else if(slideToUnlockView2.getVisibility() == View.VISIBLE){
            emp.setEmpInTime(hourofday + ":" + min);
            emp.setEmpOutTime("");
        }

        return emp;
    }

    void loadOldData(){
        List<ReportModel> allEmps = db.getAllEmps();

        //Log.i("anand", "onCreate: "+allEmps.get(0).getEmpId());
        for (int i = 0; i < allEmps.size(); i++) {
            try {
                if (allEmps.get(i).getEmpId() == Integer.parseInt(empNo)) {
                    empName = allEmps.get(i).getEmpName();
                    empDesignaiton = allEmps.get(i).getEmpDesignaiton();
                    empDepartment = allEmps.get(i).getEmpDepartment();
                    empWorkLocation = allEmps.get(i).getEmpWorkLocation();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing App")
                .setMessage("Are you sure you want to close this app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
