package com.example.hava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    //
    //Toggle switch.
    Switch toggle_switch;
    //
    //Keyword.
    EditText keyword;
    //
    //Radio group1.
    RadioGroup radioGroup1;
    //
    //Radio button.
    RadioButton radioButton1;
    //
    //Radio button.
    RadioButton radioButton2;
    //
    //Radio group2.
    RadioGroup radioGroup2;
    //
    //The main filter button.
    Button search_button;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        //Get the button
        search_button = findViewById(R.id.search_button);
        //
        //Listen to button events.
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                //Get the keyword edit field.
                keyword = findViewById(R.id.editTextTextPersonName);
                String keyword_val = keyword.getText().toString();
                //
                //Get the switch button and see if it's checked or not.
                toggle_switch = findViewById(R.id.switch1);
                boolean toggle_switch_val = toggle_switch.isChecked();
                //
                //Get radio group1 and the selected radio button from radioGroup.
                radioGroup1 = findViewById(R.id.radio_group1);
                int selected1 = radioGroup1.getCheckedRadioButtonId();
                //
                //Get the radio button.
                radioButton1 = findViewById(selected1);
                String radioButton1_val = (String) radioButton1.getText();
                //
                //Get selected radio button from radioGroup and radio group2..
                radioGroup2 = findViewById(R.id.radio_group2);
                int selected2 = radioGroup2.getCheckedRadioButtonId();
                //
                //Get the radio button selected in radioGroup2 and its and its text.
                radioButton2 = findViewById(selected2);
                String radioButton2_val = (String) radioButton2.getText();
                //
                //
                Toast.makeText(MainActivity.this,
                        "Selected values: "+keyword_val+toggle_switch_val+radioButton1_val+radioButton2_val,
                        Toast.LENGTH_SHORT).show();

                //
                //Call the search results activity.
                Intent myIntent = new Intent(MainActivity.this, search_results.class);
                //
                //Pass the data to the search results activity.
                myIntent.putExtra("keyword", keyword_val);
                //
                //Gether the variables(user input) to be sent to the search results activity.
                Bundle extras = new Bundle();
                extras.putString("keyword", keyword_val);
                extras.putString("toggle_switch_val", String.valueOf(toggle_switch_val));
                extras.putString("radioButton1_val", radioButton1_val);
                extras.putString("radioButton2_val", radioButton2_val);
                //
                //Prepare the data to be sent.
                myIntent.putExtras(extras);
                //
                //
                startActivity(myIntent);
            }
        });
    }
}