package cn.edu.nottingham.zy21724.mdpcw01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class ColourActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getName();
    Intent intent = new Intent();

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour);
        TextView textView = findViewById(R.id.currentcolour);
        int colourNo = getIntent().getExtras().getInt("colour");

        switch (colourNo){
            case Color.BLACK:
                textView.setText("Black");
                break;
            case Color.BLUE:
                textView.setText("Blue");
                break;
            case Color.GREEN:
                textView.setText("Green");
                break;
            case Color.RED:
                textView.setText("Red");
                break;
            case Color.WHITE:
                textView.setText("White");
                break;
            case Color.YELLOW:
                textView.setText("Yellow");
                break;
            default:
                textView.setText("Black");
        }
    }

    public void setBlack(View view){
        intent.putExtra("colour",Color.BLACK);
    }
    public void setRed(View view){
        intent.putExtra("colour",Color.RED);
    }
    public void setGreen(View view){
        intent.putExtra("colour", Color.GREEN);
    }
    public void setBlue(View view){
        intent.putExtra("colour",Color.BLUE);
    }
    public void setWhite(View view){
        intent.putExtra("colour",Color.WHITE);
    }
    public void setYellow(View view){
        intent.putExtra("colour",Color.YELLOW);
    }

    public void onDone(View view) {
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}