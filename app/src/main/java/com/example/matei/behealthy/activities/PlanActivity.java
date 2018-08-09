package com.example.matei.behealthy.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.matei.behealthy.R;
import com.example.matei.behealthy.custom.PlanListAdapter;
import com.example.matei.behealthy.utility.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

public class PlanActivity extends AppCompatActivity {
    static ListView listViewMonday;
    static ListView listViewTuesday;
    static ListView listViewWednesday;
    static ListView listViewThursday;
    static ListView listViewFriday;
    static ListView listViewSaturday;
    static ListView listViewSunday;

    static PlanListAdapter adapterMonday;
    static PlanListAdapter adapterTuesday;
    static PlanListAdapter adapterWednesday;
    static PlanListAdapter adapterThursday;
    static PlanListAdapter adapterFriday;
    static PlanListAdapter adapterSaturday;
    static PlanListAdapter adapterSunday;

    ArrayList<Task> monday = new ArrayList<Task>();
    ArrayList<Task> tuesday = new ArrayList<Task>();
    ArrayList<Task> wednesday = new ArrayList<Task>();
    ArrayList<Task> thursday = new ArrayList<Task>();
    ArrayList<Task> friday = new ArrayList<Task>();
    ArrayList<Task> saturday = new ArrayList<Task>();
    ArrayList<Task> sunday = new ArrayList<Task>();

    static HashMap<Boolean, ArrayList<Task>> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        if(tasks != null) {
            if (tasks.get(true) != null) tasks.get(true).clear();
            if (tasks.get(false) != null) tasks.get(false).clear();
        }

        listViewMonday = (ListView) findViewById(R.id.plan_list_monday);
        listViewTuesday = (ListView) findViewById(R.id.plan_list_tuesday);
        listViewWednesday = (ListView) findViewById(R.id.plan_list_wednesday);
        listViewThursday = (ListView) findViewById(R.id.plan_list_thursay);
        listViewFriday = (ListView) findViewById(R.id.plan_list_friday);
        listViewSaturday = (ListView) findViewById(R.id.plan_list_saturday);
        listViewSunday = (ListView) findViewById(R.id.plan_list_sunday);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ArrayList<Task> tasks = (ArrayList<Task>) bundle.get("list");

        if(tasks != null)
            for(int i = 0; i < tasks.size(); i++){
                Task x = tasks.get(i);

                switch (x.day){
                    case Monday:    monday.add(x); break;
                    case Tuesday:   tuesday.add(x); break;
                    case Wednesday: wednesday.add(x); break;
                    case Thursday:  thursday.add(x); break;
                    case Friday:    friday.add(x); break;
                    case Saturday:  saturday.add(x); break;
                    case Sunday:    sunday.add(x); break;
                }
            }

        adapterMonday    = new PlanListAdapter(this, R.layout.list_item_plan, monday, "monday");
        adapterTuesday   = new PlanListAdapter(this, R.layout.list_item_plan, tuesday, "tuesday");
        adapterWednesday = new PlanListAdapter(this, R.layout.list_item_plan, wednesday, "wednesday");
        adapterThursday  = new PlanListAdapter(this, R.layout.list_item_plan, thursday, "thursday");
        adapterFriday    = new PlanListAdapter(this, R.layout.list_item_plan, friday, "friday");
        adapterSaturday  = new PlanListAdapter(this, R.layout.list_item_plan, saturday, "saturday");
        adapterSunday    = new PlanListAdapter(this, R.layout.list_item_plan, sunday, "sunday");

        listViewMonday.setAdapter(adapterMonday);
        listViewTuesday.setAdapter(adapterTuesday);
        listViewWednesday.setAdapter(adapterWednesday);
        listViewThursday.setAdapter(adapterThursday);
        listViewFriday.setAdapter(adapterFriday);
        listViewSaturday.setAdapter(adapterSaturday);
        listViewSunday.setAdapter(adapterSunday);

        listScroll(listViewMonday);
        listScroll(listViewTuesday);
        listScroll(listViewWednesday);
        listScroll(listViewThursday);
        listScroll(listViewFriday);
        listScroll(listViewSaturday);
        listScroll(listViewSunday);
    }

    private void listScroll(ListView lv){
        lv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    public static void delete(View v, String name){

        if(tasks == null) tasks = new HashMap<>();
        if(tasks.get(false) == null) tasks.put(false, new ArrayList<Task>());

        switch (name){
            case "monday":    delete(v, listViewMonday, adapterMonday); break;
            case "tuesday":   delete(v, listViewTuesday, adapterTuesday); break;
            case "wednesday": delete(v, listViewWednesday, adapterWednesday); break;
            case "thursday":  delete(v, listViewThursday, adapterThursday); break;
            case "friday":    delete(v, listViewFriday, adapterFriday); break;
            case "saturday":  delete(v, listViewSaturday, adapterSaturday); break;
            case "sunday":    delete(v, listViewSunday, adapterSunday); break;
        }
    }

    private static void delete(View view, ListView listView, PlanListAdapter adapter){
        for(int i = 0; i < listView.getChildCount(); i++){
            View v = listView.getChildAt(i);

            if(v == view){
                tasks.get(false).add(adapter.getItem(i));
                adapter.remove(adapter.getItem(i));
            }
        }
    }

    public static PlanListAdapter getAdapter(String day){
        switch (day){
            case "monday":    return adapterMonday;
            case "tuesday":   return adapterTuesday;
            case "wednesday": return adapterWednesday;
            case "thursday":  return adapterThursday;
            case "friday":    return adapterFriday;
            case "saturday":  return adapterSaturday;
            case "sunday":    return adapterSunday;
            default:          return null;
        }
    }

    public void clickAdd(View v){
        Intent intent = new Intent(PlanActivity.this, AddWorkoutActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 100 && resultCode == RESULT_OK){
            Task t = (Task) data.getSerializableExtra("workout_task");
            if(t != null) {
                if(tasks == null) tasks = new HashMap<>();
                if(tasks.get(true) == null) tasks.put(true, new ArrayList<Task>());

                tasks.get(true).add(t);
            }
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent i = new Intent();
        i.putExtra("array_task", tasks);
        if(tasks != null)
            setResult(RESULT_OK, i);
        else
            setResult(RESULT_CANCELED, i);

        finish();
    }
}
