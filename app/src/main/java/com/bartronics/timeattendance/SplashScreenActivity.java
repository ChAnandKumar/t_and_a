package com.bartronics.timeattendance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bartronics.timeattendance.ui.employeeEnrolment.set_change_pin.SetOrChangePinActivity;
import com.bartronics.timeattendance.ui.employeeEnrolment.validateEmployee.ValidateEmployeeActivity;
import com.bartronics.timeattendance.ui.loginPage.LoginActivity;
import com.bartronics.timeattendance.ui.loginPage.SingInActivity;

import static com.bartronics.timeattendance.Util.Constant.EmpNo;
import static com.bartronics.timeattendance.Util.Constant.MyPREFERENCES;
import static com.bartronics.timeattendance.Util.Constant.isRegKey;

public class SplashScreenActivity extends AppCompatActivity {


    private SharedPreferences sharedpreferences;
    private String empNo;
    private boolean isReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    empNo = sharedpreferences.getString(EmpNo, "");
                    isReg = sharedpreferences.getBoolean(isRegKey, false);

                    Intent intent = new Intent(SplashScreenActivity.this, SingInActivity.class);
                    startActivity(intent);

                    /*if(empNo.length() != 0)
                    {
                        if(isReg){
                            Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(SplashScreenActivity.this, SetOrChangePinActivity.class);
                            startActivity(intent);
                        }

                    }else {
                        Intent intent = new Intent(SplashScreenActivity.this, ValidateEmployeeActivity.class);
                        startActivity(intent);
                    }*/


                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
