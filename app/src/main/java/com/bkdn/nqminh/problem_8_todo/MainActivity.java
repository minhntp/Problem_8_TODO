package com.bkdn.nqminh.problem_8_todo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView textViewDate;
    TextView textViewHour;
    EditText editTextTitle;
    EditText editTextContent;
    Button buttonDate;
    Button buttonHour;
    Button buttonAdd;
    ListView listViewTodo;

    Calendar calendar;
    Date date;
    Date hour;
    ArrayList<Work> arrayListTodo;
    ArrayAdapter<Work> arrayAdapterTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        addEvent();
    }

    private void initUI() {
        editTextTitle = findViewById(R.id.edtTitle);
        editTextContent = findViewById(R.id.edtContent);
        textViewDate = findViewById(R.id.txtDate);
        textViewHour = findViewById(R.id.txtHour);
        buttonDate = findViewById(R.id.btnDate);
        buttonHour = findViewById(R.id.btnHour);
        buttonAdd = findViewById(R.id.btnAdd);
        listViewTodo = findViewById(R.id.lvTodo);

        arrayListTodo = new ArrayList<Work>();
        arrayAdapterTodo = new ArrayAdapter<Work>(this, android.R.layout.simple_list_item_1,
                arrayListTodo);
        listViewTodo.setAdapter(arrayAdapterTodo);

        editTextTitle.requestFocus();
        calendar = Calendar.getInstance();
        textViewDate.setText(Work.getDateFormat(calendar.getTime()));
        textViewHour.setText(Work.getHourFormat(calendar.getTime()));
        date = calendar.getTime();
        hour = calendar.getTime();
    }

    private void addEvent() {
        buttonDate.setOnClickListener(new MyButtonEvent());
        buttonHour.setOnClickListener(new MyButtonEvent());
        buttonAdd.setOnClickListener(new MyButtonEvent());
        listViewTodo.setOnItemClickListener(new MyListViewEvent());
        listViewTodo.setOnItemLongClickListener(new MyListViewEvent());
    }

    public void pickDate() {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
                textViewDate.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
                //Lưu vết lại biến ngày hoàn thành
                calendar.set(year, monthOfYear, dayOfMonth);
                date = calendar.getTime();
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s = textViewDate.getText()+"";
        String[] temp = s.split("/");
        int ngay = Integer.parseInt(temp[0]);
        int thang = Integer.parseInt(temp[1]) - 1;
        int nam = Integer.parseInt(temp[2]);

        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, callback,
                nam, thang, ngay);
        datePickerDialog.setTitle("Chọn Ngày:");
        datePickerDialog.show();
    }

    public void pickTime() {
        TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                hour = calendar.getTime();
                textViewHour.setText(Work.getHourFormat(hour));
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong TimePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s = textViewHour.getText() + "";
        String[] temp = s.split(":");
        int gio = Integer.parseInt(temp[0]);
        String[] temp2 = temp[1].split(" ");
        String ampm = temp2[1];
        int phut = Integer.parseInt(temp2[0]);
        if(ampm.equals("PM")) {
            gio += 12;
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, callback,
                gio, phut, false);
        timePickerDialog.setTitle("Chọn Giờ:");
        timePickerDialog.show();
    }

    public void addWork() {
        String title = editTextTitle.getText() + "";
        if(title.length() == 0) {
            Toast.makeText(this, "Mời nhập tên công việc", Toast.LENGTH_SHORT).show();
            return;
        }
        String content = editTextContent.getText() + "";
        Work work = new Work(title, content, date, hour);
        arrayListTodo.add(work);
        arrayAdapterTodo.notifyDataSetChanged();
        //sau khi cập nhật thì reset dữ liệu và cho focus tới editCV
        editTextTitle.setText("");
        editTextContent.setText("");
        editTextTitle.requestFocus();
    }

    private class MyButtonEvent implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btnDate:
                    pickDate();
                    break;
                case R.id.btnHour:
                    pickTime();
                    break;
                case R.id.btnAdd:
                    addWork();
                    break;
            }
        }
    }

    private class MyListViewEvent implements AdapterView.OnItemClickListener,
            AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            Work temp = arrayListTodo.get(arg2);
            AlertDialog dialog = builder .setMessage("Bạn có chắc chắn muốn xóa?")
                    .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            arrayListTodo.remove(arg2);
                            arrayAdapterTodo.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .create();
            dialog.show();

            return false;
        }

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            Work temp = arrayListTodo.get(arg2);
            AlertDialog dialog = builder .setMessage(temp.getTitle() + "\n" + temp.getContent() + "\n" +
                    Work.getDateFormat(temp.getDate()) + "\n" + Work.getHourFormat(temp.getHour()))
                    .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            arrayListTodo.remove(arg2);
                            arrayAdapterTodo.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("Trở về", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    })
                    .create();
            dialog.show();
        }

    }
}


