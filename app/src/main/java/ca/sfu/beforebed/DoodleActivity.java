package ca.sfu.beforebed;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.graphics.Bitmap.Config;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DoodleActivity extends AppCompatActivity implements View.OnClickListener {
    MyDoodleView myDoodleView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // myDrawView = new MyDrawView(this, null);
        setContentView(R.layout.activity_doodle);
        myDoodleView = (MyDoodleView) findViewById(R.id.draw);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if(Environment.isExternalStorageManager()){


                File folder = new File(Environment.getExternalStorageDirectory().toString());
                boolean success = false;
                if (!folder.exists()) {
                    success = folder.mkdirs();
                }

                System.out.println(success + "folder");

                File file = new File(Environment.getExternalStorageDirectory().toString() + "/sample.png");

                if (!file.exists()) {
                    try {
                        success = file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(success + "file");


                FileOutputStream ostream = null;
                try {
                    ostream = new FileOutputStream(file);

                    System.out.println(ostream);
                    View targetView = myDoodleView;

                    Bitmap well = myDoodleView.getBitmap();
                    Bitmap save = Bitmap.createBitmap(320, 480, Config.ARGB_8888);
                    Paint paint = new Paint();
                    paint.setColor(Color.WHITE);
                    Canvas now = new Canvas(save);
                    now.drawRect(new Rect(0, 0, 320, 480), paint);
                    now.drawBitmap(well, new Rect(0, 0, well.getWidth(), well.getHeight()), new Rect(0, 0, 320, 480), null);

                    if (save == null) {
                        System.out.println("NULL bitmap save\n");
                    }
                    assert save != null;
                    save.compress(Bitmap.CompressFormat.PNG, 100, ostream);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Toast.makeText(v.getContext(), "Null error", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(v.getContext(), "File error", Toast.LENGTH_SHORT).show();
                }

            }
            else{
                Intent permission = new Intent(android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION );
                startActivity(permission);
            }
        }



    }

}
