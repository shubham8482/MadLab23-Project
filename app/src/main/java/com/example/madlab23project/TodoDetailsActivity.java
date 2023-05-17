package com.hitanshudhawan.todo.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hitanshudhawan.todo.R;
import com.hitanshudhawan.todo.database.Todo;
import com.hitanshudhawan.todo.database.TodoContract;
import com.hitanshudhawan.todo.utils.Constants;
import com.hitanshudhawan.todo.utils.NotificationHelper;
import com.hitanshudhawan.todo.utils.WidgetHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TodoDetailsActivity extends AppCompatActivity {

    private EditText mTodoEditText;
    private TextView mTodoDateTimeTextView;

    private Long mTodoId;
    private Todo mTodo;
    private String mTodoTitle;
    private Calendar mTodoDateTime;
    private Boolean mDateTimeChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTodoEditText = findViewById(R.id.todo_edit_text_todo_details);
        mTodoDateTimeTextView = findViewById(R.id.todo_date_time_text_view_todo_details);

        init(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        init(intent);
    }

    private void init(Intent intent) {
        mTodoId = intent.getLongExtra(Constants.TODO_ID, -1);
        if (mTodoId == -1)
            finishAndRemoveTask();
        Cursor cursor = getContentResolver().query(ContentUris.withAppendedId(TodoContract.TodoEntry.CONTENT_URI, mTodoId), null, null, null, null);
        cursor.moveToFirst();
        mTodo = Todo.fromCursor(cursor);
        mTodoTitle = mTodo.getTitle();
        mTodoDateTime = mTodo.getDateTime();
        mDateTimeChanged = false;

        mTodoEditText.setText(mTodoTitle);

        mTodoDateTimeTextView.setText(mTodoDateTime.getTimeInMillis() == 0 ? "" : DateFormat.is24HourFormat(TodoDetailsActivity.this) ? new SimpleDateFormat("MMMM dd, yyyy  h:mm").format(mTodoDateTime.getTime()) : new SimpleDateFormat("MMMM dd, yyyy  h:mm a").format(mTodoDateTime.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_todo_details, menu);
        return true;
    }
