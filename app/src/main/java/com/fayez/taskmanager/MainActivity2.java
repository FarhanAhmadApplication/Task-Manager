package com.fayez.taskmanager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity2 extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String > task_name1,task_details1,starting_date1,ending_date1,task_status1,id1;
    FloatingActionButton floatingActionButton;
    showtasksdata std;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        floatingActionButton=findViewById(R.id.floatingActionButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        task_name1=new ArrayList<>();
        task_details1=new ArrayList<>();
        starting_date1=new ArrayList<>();
        ending_date1=new ArrayList<>();
        task_status1=new ArrayList<>();
        id1=new ArrayList<>();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBox();
            }
        });
        storeinArrays(1,"Store All");
        std=new showtasksdata(MainActivity2.this,task_name1,task_details1,starting_date1,ending_date1,task_status1,id1);
        recyclerView.setAdapter(std);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing); // Adjust the spacing as needed
        recyclerView.addItemDecoration(new ItemDecorationExample(spacingInPixels));

    }
    void showDialogBox(){
        Dialog dialog=new Dialog(MainActivity2.this);
        dialog.setContentView(R.layout.dialog_box);
        EditText taskname=dialog.findViewById(R.id.taskname);
        EditText taskdetails=dialog.findViewById(R.id.taskdetails);
        EditText enddate=dialog.findViewById(R.id.enddate);
        Button okay=dialog.findViewById(R.id.okay);
        enddate.setClickable(true);
        enddate.setFocusable(false);
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int dayofmonth=calendar.get(Calendar.DAY_OF_MONTH);
        final int[] year1 = new int[1];
        final int[] month1 = new int[1];
        final int[] dayofmonth1 = new int[1];
        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpg=new DatePickerDialog(MainActivity2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
                        String s=dayofmonth+"/"+(month+1)+"/"+year;
                        year1[0] = year;
                        month1[0] = (month);
                        dayofmonth1[0] = dayofmonth-1;
                        enddate.setText(s);
                    }
                },dayofmonth,month,year);
                dpg.show();
            }
        });
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sd1=getCurrentDate();
                String tn=taskname.getText().toString();
                String td=taskdetails.getText().toString();
                String ed1=enddate.getText().toString();
                if (tn.isEmpty() || td.isEmpty() || ed1.isEmpty())
                    Toast.makeText(MainActivity2.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                else {
                    String[] EndingDateParts = ed1.split("/");
                    String[] StartingDateParts = sd1.split("/");
                    if(Integer.parseInt(EndingDateParts[2])<Integer.parseInt(StartingDateParts[2])){
                        Toast.makeText(MainActivity2.this, "Please select correct date", Toast.LENGTH_SHORT).show();
                    }
                    else if(Integer.parseInt(EndingDateParts[2])>=Integer.parseInt(StartingDateParts[2])){
                        if(Integer.parseInt(EndingDateParts[1])<Integer.parseInt(StartingDateParts[1])){
                            Toast.makeText(MainActivity2.this, "Please select correct date", Toast.LENGTH_SHORT).show();
                        }
                        else if(Integer.parseInt(EndingDateParts[1])==Integer.parseInt(StartingDateParts[1])) {
                            if (Integer.parseInt(EndingDateParts[0]) <= Integer.parseInt(StartingDateParts[0])) {
                                Toast.makeText(MainActivity2.this, "Please choose next date", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            TaskManagerDatabase tmDB=new TaskManagerDatabase(MainActivity2.this);
                            tmDB.insert(tn,td,sd1,ed1,"Task Not Complete");
                            CreateNotificationChannel();
                            storeinArrays(1,"Store All");
                            String task_name=task_name1.get(task_name1.size()-1);
                            Calendar calendar1=Calendar.getInstance();
                            calendar1.set(Calendar.YEAR,year1[0]);
                            calendar1.set(Calendar.MONTH,month1[0]);
                            calendar1.set(Calendar.DAY_OF_MONTH,dayofmonth1[0]);
                            calendar1.set(Calendar.HOUR_OF_DAY,20);
                            calendar1.set(Calendar.MINUTE,30);
                            calendar1.set(Calendar.SECOND,0);
                            calendar1.set(Calendar.MILLISECOND,0);
                            long alarmTimeMillis = calendar1.getTimeInMillis();
                            scheduleNotification(task_name);
                            recreate();
                        }
                    }
                }
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    void storeinArrays(int i,String s){
        task_name1.clear();
        task_details1.clear();
        starting_date1.clear();
        ending_date1.clear();
        task_status1.clear();
        id1.clear();
        TaskManagerDatabase tmDB=new TaskManagerDatabase(MainActivity2.this);
        Cursor cursor=null;
        if(i==1||(i==0&&s.equals("Store All")))
            cursor=tmDB.showdata();
        else if(i==0)
            cursor=tmDB.showSearchedData(s);
        if(cursor.getCount()==0){
            Toast.makeText(this, "No Task", Toast.LENGTH_SHORT).show();
        }
        else if(cursor!=null){
            while (cursor.moveToNext()){
                id1.add(cursor.getString(0));
                task_name1.add(cursor.getString(1));
                task_details1.add(cursor.getString(2));
                starting_date1.add(cursor.getString(3));
                ending_date1.add(cursor.getString(4));
                task_status1.add(cursor.getString(5));
            }
        }
    }
    public static String getCurrentDate(){
        LocalDate startingDate=null;
        String s = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startingDate=LocalDate.now();
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            s= DateUtils.formatDateToString(startingDate);
        }
        return s;
    }
    public static LocalDate stringToDate(String s) {
        LocalDate date = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date=DateUtils.parseStringToDate(s);
        }
        return date;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        MenuItem deleteItem= menu.findItem(R.id.action_delete);
        deleteItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                if(id1.size()==0){

                }
                else showAlertDialog();
                return false;
            }
        });
        if (searchView != null) {
            searchView.setQueryHint("Enter Task Name...");
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    storeinArrays(0, s);
                    std.notifyDataSetChanged();
                    return true;
                }
            });
        }

        return true;
    }

    void showAlertDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity2.this);
        builder.setTitle("Do you want to delete all tasks?");
        builder.setMessage("Are you sure you want to delete all tasks?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TaskManagerDatabase tmDB=new TaskManagerDatabase(getApplicationContext());
                tmDB.deleteAll();
                recreate();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    void CreateNotificationChannel(){
        NotificationManager nm=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel("NewTaskCreated", "NewTaskCreated", NotificationManager.IMPORTANCE_HIGH);
            nm.createNotificationChannel(channel1);

        }
    }
    void scheduleNotification(String task_name){
        scheduleNotifications("NewTaskCreated",0,task_name);

    }
    void scheduleNotifications(String channelId,long time,String task_name){
        Intent intent=new Intent(MainActivity2.this,NotificationReciever.class);
        intent.putExtra("CHANNELID",channelId);
        intent.putExtra("Task_Name",task_name);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(MainActivity2.this,channelId.hashCode(),intent,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
        AlarmUtils au=new AlarmUtils(MainActivity2.this);
        if(channelId.equals("NewTaskCreated"))
            au.setAlarm(System.currentTimeMillis()+time,pendingIntent);
    }
}