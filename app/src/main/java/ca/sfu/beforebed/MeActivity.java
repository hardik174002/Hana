package ca.sfu.beforebed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener , SensorEventListener {

    private float xCurrent, yCurrent, zCurrent, xPrevious, yPrevious, zPrevious, xDiffernce, yDiffernce, zDiffernce;
    private Sensor accesslaration;
    private boolean isSensorAcceslarationAvailable = false, itIsNotFirstTime = false;
    private float shakeThreshauld = 40.0f;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        BottomNavigationView bottomNavigationView= findViewById(R.id.bottom_navigation);
        //set entry selected
        bottomNavigationView.setSelectedItemId(R.id.me);
        //itemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        addingSensorFunctionality();
    }

    private void addingSensorFunctionality() {
        sensorManager= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!=null){
            accesslaration=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this,accesslaration,SensorManager.SENSOR_DELAY_NORMAL);
            isSensorAcceslarationAvailable=true;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.entry:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                overridePendingTransition(0,0);
                return true;
            case R.id.home:
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                overridePendingTransition(0,0);
                return true;
            case R.id.me:
                return true;
        }
        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        xCurrent=event.values[0];
        yCurrent=event.values[1];
        zCurrent=event.values[2];

        if(itIsNotFirstTime){
            xDiffernce=Math.abs(xPrevious-xCurrent);
            yDiffernce=Math.abs(yPrevious-yCurrent);
            zDiffernce=Math.abs(zPrevious-zCurrent);
             Log.e("Vibration Checked", String.valueOf(xDiffernce)+String.valueOf(yDiffernce)+String.valueOf(zDiffernce));
            if((xDiffernce>shakeThreshauld && yDiffernce>shakeThreshauld) || (xDiffernce>shakeThreshauld  && zDiffernce>shakeThreshauld)
                    || (yDiffernce>shakeThreshauld && zDiffernce>shakeThreshauld)){
                   Log.e("Vibration Checked","SuccessFull");

                //After Shaking Move From This Activity To Home Activity

                Intent intent =new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }

        xPrevious=xCurrent;
        yPrevious=yCurrent;
        zPrevious=zCurrent;
        itIsNotFirstTime=true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }
}