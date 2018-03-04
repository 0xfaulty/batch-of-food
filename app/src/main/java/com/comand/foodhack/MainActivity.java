package com.comand.foodhack;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.comand.foodhack.dao.UserDao;
import com.comand.foodhack.dao.UserDaoImpl;
import com.comand.foodhack.entity.User;
import com.comand.foodhack.utils.DatabaseHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    public SQLiteDatabase mSqLiteDatabase;
    private TextView resultView;

    private UserDao userDao;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_menu:
                    mTextMessage.setText(R.string.title_menu);
                    return true;
                case R.id.navigation_user:
                    mTextMessage.setText(R.string.title_user);
                    return true;
                /*case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;*/
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Button writeToDB = findViewById(R.id.show_button);
        writeToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printUsers(v);
            }
        });

        Button readFromDB = findViewById(R.id.add_button);
        readFromDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("lol");
            }
        });

        DatabaseHelper mDatabaseHelper = new DatabaseHelper(this, "fooddatabase.db", null, 1);
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
        userDao = new UserDaoImpl(mSqLiteDatabase);
    }

    public void printUsers(View view) {
        List<User> users = userDao.getUsers();
        for (User user : users) {
            showMessage(user.toString());
        }
    }

    public void createUser(User user) {
        userDao.addUser(user);
    }


    private void showMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }

}