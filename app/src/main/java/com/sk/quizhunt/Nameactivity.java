package com.sk.quizhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class Nameactivity extends AppCompatActivity {
TextInputEditText t;
Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nameactivity);
        t=findViewById(R.id.name);
        b=findViewById(R.id.button2);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(t.getText().toString().length()==0)
                {
                    t.requestFocus();
                    t.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!t.getText().toString().matches("^[_A-Za-z]+[_A-Za-z0-9@]+"))
                {
                    t.requestFocus();
                    t.setError("ENTER ONLY ALPHANUMERIC CHARACTER");
                }
                else {
                    Intent intent = new Intent(Nameactivity.this, MainActivity.class);
                    intent.putExtra("Name", t.getText().toString());
                    startActivity(intent);
                }
            }
        });

    }
}