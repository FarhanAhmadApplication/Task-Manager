package com.fayez.taskmanager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class Update_And_Delete_Activity extends AppCompatActivity {
    TextView task_details1, updateTextView1, deleteTextView1;
    ImageView updateImageView1, deleteImageView1;
    String tn, td, sd, ed, ts, id,sd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_and_delete);
        task_details1=findViewById(R.id.task_details1);
        updateTextView1=findViewById(R.id.updateTextView1);
        deleteTextView1=findViewById(R.id.deleteTextView1);
        updateImageView1=findViewById(R.id.updateImageView1);
        deleteImageView1=findViewById(R.id.deleteImageView1);
        tn=getIntent().getStringExtra("Task_Name");
        td=getIntent().getStringExtra("Task_Details");
        ed=getIntent().getStringExtra("Ending_date");
        id=getIntent().getStringExtra("Id");
        ts=getIntent().getStringExtra("Task_Status");
        sd1=getIntent().getStringExtra("Starting_Date");
        task_details1.setText(td);
        ActionBar ab=getSupportActionBar();
        if(ab!=null)
            ab.setTitle(tn);
        sd=MainActivity2.getCurrentDate();
        updateImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] EndingDateParts = ed.split("/");
                String[] StartingDateParts = sd.split("/");
                if(Integer.parseInt(EndingDateParts[2])<Integer.parseInt(StartingDateParts[2])){
                    Toast.makeText(Update_And_Delete_Activity.this, "You can not update this infomation as your time for this task has exceeded", Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(EndingDateParts[2])>=Integer.parseInt(StartingDateParts[2])){
                    if(Integer.parseInt(EndingDateParts[1])<Integer.parseInt(StartingDateParts[1])){
                        Toast.makeText(Update_And_Delete_Activity.this, "You can not update this infomation as your time for this task has exceeded", Toast.LENGTH_SHORT).show();
                    }
                    else if(Integer.parseInt(EndingDateParts[1])==Integer.parseInt(StartingDateParts[1])){
                        if(Integer.parseInt(EndingDateParts[0])<Integer.parseInt(StartingDateParts[0])){
                            Toast.makeText(Update_And_Delete_Activity.this, "You can not update this infomation as your time for this task has exceeded", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        showUpdateDialogBox();
                    }
                }
            }
        });


        deleteImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });
    }
    void showUpdateDialogBox(){
        Dialog dialog=new Dialog(Update_And_Delete_Activity.this);
        dialog.setContentView(R.layout.dialog_update_box);
        EditText taskname=dialog.findViewById(R.id.taskname);
        EditText taskdetails=dialog.findViewById(R.id.taskdetails);
        EditText enddate=dialog.findViewById(R.id.enddate);
        EditText taskstatus=dialog.findViewById(R.id.taskstatus);
        Button okay=dialog.findViewById(R.id.okay);
        taskname.setText(tn);
        taskdetails.setText(td);
        enddate.setText(ed);
        taskstatus.setText(ts);
        enddate.setFocusable(false);
        enddate.setClickable(true);
        taskstatus.setFocusable(false);
        taskstatus.setClickable(true);
        Calendar calendar=Calendar.getInstance();
        int years=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int dayofmonth=calendar.get(Calendar.DAY_OF_MONTH);
        final int[]years1=new int[1];
        final int[]month1=new int[1];
        final int[]dayofmonth1=new int[1];
        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpg=new DatePickerDialog(Update_And_Delete_Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int years, int month, int dayofmonth) {
                        String updateDate=dayofmonth+"/"+(month+1)+"/"+years;
                        enddate.setText(updateDate);
                        years1[0]=years;
                        month1[0]=month;
                        dayofmonth1[0]=dayofmonth-1;

                    }
                },dayofmonth,month,years);
                dpg.show();
            }
        });
        taskstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog1=new Dialog(Update_And_Delete_Activity.this);
                dialog1.setContentView(R.layout.task_status);
                CheckBox taskcomplete=dialog1.findViewById(R.id.taskcomplete);
                CheckBox tasknotcomplete=dialog1.findViewById(R.id.tasknotcomplete);
                taskcomplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        taskstatus.setText("Task Complete");
                        dialog1.dismiss();
                    }
                });
                tasknotcomplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        taskstatus.setText("Task Not Complete");
                        dialog1.dismiss();
                    }
                });
                dialog1.show();
            }
        });
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskn=taskname.getText().toString();
                String taskd=taskdetails.getText().toString();
                String endd=enddate.getText().toString();
                String tasks=taskstatus.getText().toString();
                if (taskn.isEmpty() || taskd.isEmpty() || endd.isEmpty() || tasks.isEmpty())
                    Toast.makeText(Update_And_Delete_Activity.this, "Details are not complete", Toast.LENGTH_SHORT).show();
                else {
                    String[] EndingDateParts = endd.split("/");
                    String[] StartingDateParts = sd.split("/");
                    if (Integer.parseInt(EndingDateParts[2]) < Integer.parseInt(StartingDateParts[2])){
                        Toast.makeText(Update_And_Delete_Activity.this, "Please select correct date", Toast.LENGTH_SHORT).show();}
                    else if (Integer.parseInt(EndingDateParts[2]) >= Integer.parseInt(StartingDateParts[2])) {
                        if (Integer.parseInt(EndingDateParts[1]) < Integer.parseInt(StartingDateParts[1]))
                            Toast.makeText(Update_And_Delete_Activity.this, "Please select correct date", Toast.LENGTH_SHORT).show();
                        else if(Integer.parseInt(EndingDateParts[1]) == Integer.parseInt(StartingDateParts[1])){
                            if (Integer.parseInt(EndingDateParts[0]) <= Integer.parseInt(StartingDateParts[0]))
                                Toast.makeText(Update_And_Delete_Activity.this, "Please choose next date", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            TaskManagerDatabase tmDB = new TaskManagerDatabase(Update_And_Delete_Activity.this);
                            tmDB.update(taskn, taskd, sd1, endd, tasks, id);
                            Toast.makeText(Update_And_Delete_Activity.this, "Task Updated", Toast.LENGTH_SHORT).show();
                            Intent iMain = new Intent(getApplicationContext(), MainActivity2.class);
                            iMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(iMain);
                        }
                    }
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    void showAlertDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(Update_And_Delete_Activity.this);
        builder.setTitle("Do you want to delete this task?");
        builder.setMessage("Are you sure you want to delete this task?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TaskManagerDatabase tmDB=new TaskManagerDatabase(getApplicationContext());
                tmDB.delete(id);
                Toast.makeText(Update_And_Delete_Activity.this, "Item Deleted", Toast.LENGTH_SHORT).show();
                Intent iMain=new Intent(getApplicationContext(),MainActivity2.class);
                iMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(iMain);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}