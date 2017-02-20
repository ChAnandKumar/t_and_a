package com.bartronics.timeattendance.ui.loginPage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bartronics.timeattendance.R;
import com.bartronics.timeattendance.ui.employeeEnrolment.set_change_pin.SetOrChangePinActivity;
import com.bartronics.timeattendance.ui.employeeEnrolment.validateEmployee.ValidateEmployeeActivity;
import com.bartronics.timeattendance.ui.loginLogout.LoginAndLogoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bartronics.timeattendance.Util.Constant.EmpNo;
import static com.bartronics.timeattendance.Util.Constant.MyPREFERENCES;
import static com.bartronics.timeattendance.Util.Constant.isRegKey;
import static com.bartronics.timeattendance.Util.Constant.regPin;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.enterPinEditText)
    EditText enterPinEditText;
   /* @BindView(R.id.changePinButton)
    Button changePinButton;
    @BindView(R.id.newProfileButton)
    Button newProfileButton;*/
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.changeEmployee_button)
    Button changeEmployeeButton;
    private SharedPreferences sharedpreferences;
    private String empNo;
    private boolean isReg;
    private String rPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Toolbar tool_bar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tool_bar);
        tool_bar.setTitle("TATA MOTORS");
        //tool_bar.setLogo(R.drawable.logo_white);

        enterPinEditText.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            loginClicked();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

       /* getSupportActionBar().setDisplayShowHomeEnabled(false);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setIcon(R.drawable.intput_bg);*/
    }

    private void loginClicked() {
        empNo = sharedpreferences.getString(EmpNo, "");
        isReg = sharedpreferences.getBoolean(isRegKey, false);
        rPin = sharedpreferences.getString(regPin, "");

        if(empNo.length() != 0 && isReg){

            Log.i("anand", "onClick:  empNo : "+ empNo +"\nisReg : "+isReg);
            Log.e("anand", "onClick:  rPin : "+ rPin +"\nenterPinEditText : "+enterPinEditText.getText().toString());
            if(rPin.equals(enterPinEditText.getText().toString())){
                Intent intent2 = new Intent(this, LoginAndLogoutActivity.class);
                startActivity(intent2);
            }else {
                enterPinEditText.setError("Please enter valid pin");
                //Toast.makeText(this, "Please enter valid pin", Toast.LENGTH_SHORT).show();
            }
        }else {
            Intent intent2 = new Intent(this, ValidateEmployeeActivity.class);
            startActivity(intent2);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_page_menu, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.changeEmployeeMenu:
                Intent intent3 = new Intent(this, ValidateEmployeeActivity.class);
                startActivity(intent3);
                return true;
            case R.id.forgotPinMenu:
                Intent intent = new Intent(this, SetOrChangePinActivity.class);
                intent.putExtra("emp_id",enterPinEditText.getText().toString());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick({ R.id.login,R.id.changeEmployee_button})
    public void onClick(View view) {
        switch (view.getId()) {

           /* case R.id.changePinButton:
                Intent intent = new Intent(this, SetOrChangePinActivity.class);
                startActivity(intent);
                break;
            case R.id.newProfileButton:
                Intent intent1 = new Intent(this, ProfileActivity.class);
                startActivity(intent1);
                break;*/
            case R.id.login:
                loginClicked();
                break;
           /* case R.id.changeEmployee_button:
                Intent intent3 = new Intent(this, ValidateEmployeeActivity.class);
                startActivity(intent3);
                break;*/
        }

    }


}
