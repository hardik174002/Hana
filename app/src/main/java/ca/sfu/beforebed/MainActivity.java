package ca.sfu.beforebed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    Button memoryButton, gratitudeButton, doodleButton;

    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView= findViewById(R.id.bottom_navigation);
        //set entry selected
        bottomNavigationView.setSelectedItemId(R.id.entry);
        //itemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        memoryButton= (Button) findViewById(R.id.memoryButton);
        gratitudeButton= (Button) findViewById(R.id.gratitudeButton);
        doodleButton= (Button) findViewById(R.id.doodleButton);

        memoryButton.setOnClickListener(this);
        gratitudeButton.setOnClickListener(this);
        doodleButton.setOnClickListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.entry:
                return true;
            case R.id.home:
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                overridePendingTransition(0,0);
                return true;
            case R.id.me:
                startActivity(new Intent(getApplicationContext(),MeActivity.class));
                overridePendingTransition(0,0);
                return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.memoryButton) {

            if (hasCameraPermission()) {
                Log.d("PERMISSION", "onClick: has permission");
                enableCamera();
            } else {
                requestPermission();
            }
        }
        if (v.getId() == R.id.gratitudeButton) {
            startActivity(new Intent(this, GratitudeActivity.class));

        }
        if (v.getId() == R.id.doodleButton) {
            startActivity(new Intent(this, DoodleActivity.class));
        }

    }

    private boolean hasCameraPermission() {
       if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
           return false;
       }
       else
           return true;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                CAMERA_PERMISSION,
                CAMERA_REQUEST_CODE
        );
    }

    private void enableCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);

        //startActivityForResult(intent)
    }

  
}