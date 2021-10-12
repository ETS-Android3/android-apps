package cn.edu.nottingham.zy21724.mdpcw01;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    FingerPainterView mFingerPainterView;      //instance of FingerPainterView
    public static final int COLOUR_CODE = 0;           //identifier for color date
    public static final int BRUSH_CODE = 1;            //identifier for size and shape

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFingerPainterView = findViewById(R.id.myFingerPainterViewId);
        //load image onto the canvas
        mFingerPainterView.load(getIntent().getData());
    }

    //will be called when click Color Button
    public void setColour(View view){
        Bundle b = new Bundle();
        b.putInt("colour", mFingerPainterView.getColour());
        Intent intent = new Intent(this, ColourActivity.class);
        intent.putExtras(b);
        startActivityForResult(intent, COLOUR_CODE);
    }

    //will be called when click Size Button
    public void setBrush(View view){
        Bundle b = new Bundle();
        b.putInt("width", mFingerPainterView.getBrushWidth());
        b.putString("shape", mFingerPainterView.getBrush().toString());
        Intent intent = new Intent(this, BrushActivity.class);
        intent.putExtras(b);
        startActivityForResult(intent, BRUSH_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        Bundle b = data.getExtras();
        //return the intended setting of brush colour
        if(requestCode == COLOUR_CODE && resultCode == Activity.RESULT_OK) {
            assert b != null;
            int colour = b.getInt("colour");
            mFingerPainterView.setColour(colour);
        }
        //return the intended setting of brush size and shape
        else if(requestCode == BRUSH_CODE && resultCode == Activity.RESULT_OK) {
            String shape;
            assert b != null;
            shape = b.getString("shape");
            Paint.Cap realShape = Paint.Cap.valueOf(shape);
            mFingerPainterView.setBrush(realShape);

            int width = b.getInt("width");
            mFingerPainterView.setBrushWidth(width);
        }
    }

    // will be called when click Save Button
    public void onSave(View view) {
        String[] PERMISSIONS = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE" };
        //examine the permission
        int permission = ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // ask for permission if denied
            ActivityCompat.requestPermissions(this, PERMISSIONS,1);
        }

        int w = mFingerPainterView.getWidth();
        int h = mFingerPainterView.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        mFingerPainterView.layout(0, 0, w, h);
        mFingerPainterView.draw(c);

        Matrix matrix = new Matrix();
        matrix.postScale(0.5f,0.5f); //resize
        bmp = Bitmap.createBitmap(bmp,0,0,        bmp.getWidth(),bmp.getHeight(),matrix,true);
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        saveBitmap(bmp,format.format(new Date())+".JPEG");
    }

    //save the image file
    public void saveBitmap(Bitmap bitmap, String bitName){
        String fileName ;
        File file ;

        file=new File(getExternalCacheDir(),bitName);
        if(file.exists()){
            file.delete();
        }
        FileOutputStream out;
        try{
            out = new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)) {
                out.flush();
                out.close();
                // insert into Media
                MediaStore.Images.Media.insertImage(this.getContentResolver(), file.getAbsolutePath(), bitName, null);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(MainActivity.this,"Successfully saved",Toast.LENGTH_SHORT).show();
    }
}