package cn.edu.nottingham.zy21724.mdpcw01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class BrushActivity extends AppCompatActivity {

    Bundle b = new Bundle();
    protected Intent intent = new Intent();
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brush);

        String shape = getIntent().getExtras().getString("shape");
        CharSequence currentShape = shape;
        int width = getIntent().getExtras().getInt("width");

        EditText editText = (EditText) findViewById(R.id.width);
        editText.setText(Integer.toString(width));

        TextView textView = (TextView) findViewById(R.id.currentShape);
        textView.setText(currentShape);
    }

    public void onDone(View view){
        EditText editText = (EditText) findViewById(R.id.width);


        if(!editText.getText().toString().equals("")) {
            intent.putExtra("width", Integer.parseInt(editText.getText().toString()));
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    public void setSquare(View view) {
        intent.putExtra("shape","SQUARE");
    }

    public void setRound(View view) {
        intent.putExtra("shape","ROUND");
    }

    public void setButt(View view) {
        intent.putExtra("shape","BUTT");
    }
}