package com.example.minor_project;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.health.connect.datatypes.ExerciseRoute;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
public class Barcode_Read_Mark_Atten extends AppCompatActivity {

    private static final int REQUEST_CHECK_SETTING = 1001;
    ImageView imgCamera;
    private LocationRequest locationRequest;
    TextView messageText, messageFormat;
    public static TextView scantext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_read_mark_atten);

        TextView Back = findViewById(R.id.Back);
        Button  Location_Button = findViewById(R.id.get_Currnt_Location);
        TextView txt = findViewById(R.id.Location_txt);
        locationRequest = LocationRequest.create(); // create a location request
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); // set how accurately the location will be provided
        locationRequest.setInterval(5000); // interval at with the location is set
        locationRequest.setFastestInterval(2000);
        TextView Date_Display = findViewById(R.id.Date_txt);
        Button date_btn = findViewById(R.id.get_Currnt_Date);
        EditText name = findViewById(R.id.Name_edittxt);
        EditText enrollment_no = findViewById(R.id.Enroll_no_edittxt);
        EditText subject = findViewById(R.id.Subject_edittxt);
        Button Mark_Atten = findViewById(R.id.Mark_Attendence_btn);
        Button scanBtn;

        scanBtn = findViewById(R.id.Barcode_btn);
        messageText = findViewById(R.id.textContent);
        messageFormat = findViewById(R.id.textFormat);

        scanBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                IntentIntegrator intentIntegrator = new IntentIntegrator(Barcode_Read_Mark_Atten.this);
                intentIntegrator.setPrompt("Scan a barcode or QR Code");
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.initiateScan();
            }
        });

        Back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Barcode_Read_Mark_Atten.this, Mark_Atten.class);
                startActivity(intent);
            }
        });
        date_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Date dateCurrent = Calendar.getInstance().getTime();
                Date_Display.setText(dateCurrent.toString());
            }
        });

        Location_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if(ActivityCompat.checkSelfPermission(Barcode_Read_Mark_Atten.this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) // checks if the gps is on or not
                    {
                        if(isGPSEnable())
                        {
                            LocationServices.getFusedLocationProviderClient(Barcode_Read_Mark_Atten.this).requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);
                                    LocationServices.getFusedLocationProviderClient(Barcode_Read_Mark_Atten.this).removeLocationUpdates(this);

                                    if(locationResult != null && locationResult.getLocations().size() > 0)
                                    {
                                        int index =  locationResult.getLocations().size() - 1;
                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                        double longitude = locationResult.getLocations().get(index).getLongitude();

                                        double Latitude = (double)latitude;
                                        double Longitude = (double) longitude;

                                        if(latitude >= 28.6812700 && latitude < 28.6813500  && longitude >= 77.3766800 && longitude < 77.3767500)
                                        {
                                            txt.setText("You are in class");
                                            Toast.makeText(Barcode_Read_Mark_Atten.this, "LONGITUDE : " + Longitude + "\nLATITUDE : " + Latitude,Toast.LENGTH_LONG);
                                        }

                                        else
                                        {
                                            txt.setText("You are not in class");
                                            Toast.makeText(Barcode_Read_Mark_Atten.this, "LONGITUDE : " + Longitude + "\nLATITUDE : " + Latitude,Toast.LENGTH_LONG);
                                        }
                                    }
                                }
                            }, Looper.getMainLooper());
                        }

                        else // when gps is off
                        {
                            turnOnGPS();
                        }
                    }

                    else
                    {
                        requestPermissions(new String[]{ACCESS_FINE_LOCATION}, 44);
                    }
                }
            }
        });

        Mark_Atten.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String Name = name.getText().toString();
                String Enrollment_no = enrollment_no.getText().toString();
                String Subject = subject.getText().toString();
                String Date = Date_Display.getText().toString();
                String Location = txt.getText().toString();

                if(TextUtils.isEmpty(Name))
                {
                    Toast.makeText(Barcode_Read_Mark_Atten.this, "ENTER YOUR Name!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(Enrollment_no))
                {
                    Toast.makeText(Barcode_Read_Mark_Atten.this, "ENTER YOUR ENROLLMENT NUM!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (TextUtils.isEmpty(Subject))
                {
                    Toast.makeText(Barcode_Read_Mark_Atten.this, "ENTER YOUR SUBJECT!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (TextUtils.isEmpty(Date))
                {
                    Toast.makeText(Barcode_Read_Mark_Atten.this, "ENTER YOUR DATE!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (TextUtils.isEmpty(Location))
                {
                    Toast.makeText(Barcode_Read_Mark_Atten.this, "ENTER YOUR LOCATION!!", Toast.LENGTH_SHORT).show();
                    return;
                }

               /* else if(Location.equalsIgnoreCase("You Are Not In Class"))
                {
                    Toast.makeText(Face_Dection_Mark_Atten.this, "Attendance cannot be marked as you are not in class", Toast.LENGTH_SHORT).show();
                    return;
                } */
                else if (Name.equalsIgnoreCase("Harsh") && Enrollment_no.equalsIgnoreCase("01996203120"))
                {
                    Intent intent = new Intent(Barcode_Read_Mark_Atten.this, Attendance_Marked_Sucess.class);
                    startActivity(intent);
                }

                else
                {
                    Toast.makeText(Barcode_Read_Mark_Atten.this, "WRONG CREDENTIALS!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Barcode_Read_Mark_Atten.this, Attendence_Marked_Fail.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void turnOnGPS()
    {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addAllLocationRequests(Collections.singleton(locationRequest)); //used to get the response as gps location of client
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext()).checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(Barcode_Read_Mark_Atten.this, "GPS is On!!", Toast.LENGTH_LONG).show();

                }
                catch (ApiException e) // e here is the api exception error
                {
                    switch (e.getStatusCode())
                    {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED : //location setting status code used to get the location setting status(wether its turn on, or the device have location access or not etc)
                            //Resolution Required is used to check the location being turned on

                            try
                            {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e; // used to get the api exception error
                                resolvableApiException.startResolutionForResult(Barcode_Read_Mark_Atten.this, REQUEST_CHECK_SETTING);
                            }
                            catch (IntentSender.SendIntentException ex)
                            {
                                ex.printStackTrace();
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE :
                            break;
                    }
                }
            }
        });
    }

    private boolean isGPSEnable() // this method returns true is gps is turned on and returns false if gps is turned off
    {
        LocationManager locationManager = null;
        boolean isEnable = false;

        if(locationManager == null)
        {
            locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        }

        isEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnable;
    }

    public void get_Location_Page(int latitude, int longitude)
    {
        Intent intent = new Intent(this, Location_Display.class);
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                // if the intentResult is not null we'll set
                // the content and format of scan message
                messageText.setText(intentResult.getContents());
                messageFormat.setText(intentResult.getFormatName());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}