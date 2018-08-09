package com.example.matei.behealthy.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.matei.behealthy.R;
import com.example.matei.behealthy.activities.PlanActivity;
import com.example.matei.behealthy.utility.Task;

import java.util.List;

public class PlanListAdapter extends ArrayAdapter<Task>{

    private String mName;

    public PlanListAdapter(@NonNull Context context, int resource, @NonNull List<Task> objects, String name) {
        super(context, resource, objects);
        mName = name;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View v;

        if(convertView != null){
            v = convertView;
        }

        else v = LayoutInflater.from(getContext()).inflate(R.layout.list_item_plan, parent, false);

        Task item = getItem(position);


        ((TextView)v.findViewById(R.id.list_item_no)).setText(String.format("%01d", position + 1) + ".");
        ((TextView)v.findViewById(R.id.list_item_name)).setText("Name: " + item.name);
        ((TextView)v.findViewById(R.id.list_item_time)).setText("Time: " + item.time.toString().substring(0, 5));
        ((TextView)v.findViewById(R.id.list_item_repetari)).setText("Repeats/serie: " + String.format("%01d", item.repetari));
        ((TextView)v.findViewById(R.id.list_item_serii)).setText("Series: " + String.format("%01d", item.serii));
        ((TextView)v.findViewById(R.id.bulaneala)).setText(mName);


        Button deleteButton = (Button)v.findViewById(R.id.list_item_button_delete);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlanActivity.delete(v, mName);
            }
        });

        return v;
    }

    @Override
    public void remove(@Nullable Task object) {
        super.remove(object);
    }
}
