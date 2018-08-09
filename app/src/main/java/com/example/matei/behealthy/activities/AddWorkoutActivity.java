package com.example.matei.behealthy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.matei.behealthy.R;
import com.example.matei.behealthy.utility.DaysOfTheWeek;
import com.example.matei.behealthy.utility.Task;

import java.sql.Time;

public class AddWorkoutActivity extends AppCompatActivity {

    EditText day;
    EditText name;
    EditText series;
    EditText reps;
    EditText time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);

        day      = (EditText) findViewById(R.id.add_workout_day);
        name     = (EditText) findViewById(R.id.add_workout_name);
        series   = (EditText) findViewById(R.id.add_workout_series);
        reps     = (EditText) findViewById(R.id.add_workout_reps);
        time     = (EditText) findViewById(R.id.add_workout_time);
    }

    public void clickSave(View v){
        if(!checkDay(day.getText().toString())){
            Toast.makeText(getApplicationContext(), "The day may be Monday, Tuesday, Wednesday, Thursday, Friday, Sunday or Saturday", Toast.LENGTH_LONG).show();
        }

        if(!checkTime(time.getText().toString())){
            Toast.makeText(getApplicationContext(), "The time needs to have a hh:mm format", Toast.LENGTH_LONG).show();
        }

        DaysOfTheWeek d = getDay(day.getText().toString());

        String aux = time.getText().toString();
        String h = new String();
        if(aux.charAt(0) != '0') h += aux.charAt(0);
        h += aux.charAt(1);

        String min = new String();

        if(aux.charAt(3) != '0') min += aux.charAt(3);
        min += aux.charAt(4);
        Time t = new Time(new Integer(h), new Integer(min), 0);

        Task task = new Task(name.getText().toString(), new Integer(series.getText().toString()), new Integer(reps.getText().toString()), d, t);

        (PlanActivity.getAdapter(day.getText().toString())).add(task);

        Intent i = new Intent();
        i.putExtra("workout_task", task);
        setResult(RESULT_OK, i);
        finish();
    }

    private boolean checkDay(String day){
        day = day.toLowerCase();

        switch (day){
            case "monday":    return true;
            case "tuesday":   return true;
            case "wednesday": return true;
            case "thursday":  return true;
            case "friday":    return true;
            case "saturday":  return true;
            case "sunday":    return true;
            default:          return false;
        }
    }

    private boolean checkTime(String time){
        if(time.length() > 5 || time.length() < 5){
            return false;
        }

        if((!(time.charAt(0) >= '0' && time.charAt(0) <= '9')) || (!(time.charAt(1) >= '0' && time.charAt(1) <= '9')) || time.charAt(2) != ':' || (!(time.charAt(3) >= '0' && time.charAt(3) <= '9')) || (!(time.charAt(4) >= '0' && time.charAt(4) <= '9'))){
            return false;
        }

        return true;
    }

    private DaysOfTheWeek getDay(String day){
        switch (day){
            case "monday":    return DaysOfTheWeek.Monday;
            case "tuesday":   return DaysOfTheWeek.Tuesday;
            case "wednesday": return DaysOfTheWeek.Wednesday;
            case "thursday":  return DaysOfTheWeek.Thursday;
            case "friday":    return DaysOfTheWeek.Friday;
            case "saturday":  return DaysOfTheWeek.Saturday;
            case "sunday":    return DaysOfTheWeek.Sunday;
            default:          return null;
        }
    }

    public void clickCancel(View view){
        Intent i = new Intent();
        i.putExtra("workout_task", (Task)null);
        setResult(RESULT_CANCELED, i);
        finish();
    }
}
