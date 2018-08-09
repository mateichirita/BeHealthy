package com.example.matei.behealthy.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.matei.behealthy.R;
import com.example.matei.behealthy.utility.DaysOfTheWeek;
import com.example.matei.behealthy.utility.Task;
import com.example.matei.behealthy.utility.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {
    public static User currentUser;
    public static final String tasksFile = "tasks.bin";
    private HashMap<String, ArrayList<Task>> taskHashMap =
            new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        try {
            FileInputStream fileInputStream =
                    openFileInput(tasksFile);
            ObjectInputStream inputStream =
                    new ObjectInputStream(fileInputStream);
            taskHashMap =
                    (HashMap<String, ArrayList<Task>>)inputStream.readObject();
            inputStream.close();
            fileInputStream.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        if(taskHashMap == null) {
            taskHashMap = new HashMap<>();
        }

        ((TextView)findViewById(R.id.menu_username)).setText(currentUser.getName());
        ((TextView)findViewById(R.id.menu_motivational)).setText("You can do it");
        ((TextView)findViewById(R.id.menu_goal)).setText("Do all the planned exercises");

        /*Time t = new Time(12, 0, 0);
        Task k = new Task("push-up", 3, 5, DaysOfTheWeek.Monday, t);
        ArrayList a = new ArrayList();
        a.add(k);
        taskHashMap.put("matei",a);*/
    }

    @Override
    protected void onStop() {
        super.onStop();

        try {
            File file = new File(tasksFile);
            if(taskHashMap != null && taskHashMap.size() > 0) {
                if(file.exists()) {
                    file.delete();
                }
                FileOutputStream fileOutputStream =
                        openFileOutput(tasksFile, MODE_PRIVATE);
                ObjectOutputStream stream =
                        new ObjectOutputStream(fileOutputStream);
                stream.writeObject(taskHashMap);
                stream.close();
                fileOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clickPlan(View view){
        Intent intent = new Intent(MenuActivity.this,
                PlanActivity.class);

        intent.putExtra("list", taskHashMap.get(currentUser.getName()));
        startActivityForResult(intent ,110);
    }

    public void clickLogout(View view){
        onStop();

        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
        currentUser = null;
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 110 && resultCode == RESULT_OK){
            HashMap<Boolean, ArrayList<Task> > tasks = (HashMap<Boolean, ArrayList<Task> >) data.getExtras().get("array_task");
            ArrayList<Task> array = taskHashMap.get(currentUser.getName());
            if(array == null) {
                taskHashMap.put(currentUser.getName(), new ArrayList<Task>());
            }
            if(tasks.get(true) != null)
            for(Task t : tasks.get(true)){
                taskHashMap.get(currentUser.getName()).add(t);
            }

            ArrayList<Task> aux = new ArrayList<>();

            if(tasks.get(false) != null)
            for(Task t : tasks.get(false)){
                for(Task t1 : taskHashMap.get(currentUser.getName()))
                    if(similar(t, t1))
                        aux.add(t1);
            }

            for(Task t : aux){
                taskHashMap.get(currentUser.getName()).remove(t);
            }

        }
    }

    private boolean similar(Task t, Task t1){
        if(t.repetari != t1.repetari){
            return false;
        }

        if(t.serii != t1.serii)
        {
            return false;
        }

        /*if((String) t.time.toString() != (String) t1.time.toString()){
            return false;
        }*/

        String aux1 = t.time.toString();
        String aux2 = t1.time.toString();

        if(aux1.length() > aux2.length()) return false;

        for(int i = 0; i < aux1.length(); i++){
            if(aux1.charAt(i) != aux2.charAt(i)){
                return false;
            }
        }

        aux1 = t.name.toString();
        aux2 = t1.name.toString();

        if(aux1.length() > aux2.length()) return false;

        for(int i = 0; i < aux1.length(); i++){
            if(aux1.charAt(i) != aux2.charAt(i)){
                return false;
            }
        }

        if(t.day != t1.day){
            return false;
        }

        return true;
    }
}
