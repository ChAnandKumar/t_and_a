package com.bartronics.timeattendance.ui.employeeEnrolment.validateEmployee;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bartronics.timeattendance.R;
import com.bartronics.timeattendance.ui.employeeEnrolment.set_change_pin.SetOrChangePinActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bartronics.timeattendance.Util.Constant.EmpNo;
import static com.bartronics.timeattendance.Util.Constant.MyPREFERENCES;
import static com.bartronics.timeattendance.Util.Constant.sampleEmpNo1;
import static com.bartronics.timeattendance.Util.Constant.sampleEmpNo2;
import static com.bartronics.timeattendance.Util.Constant.sampleEmpNo3;
import static com.bartronics.timeattendance.Util.Constant.sampleEmpNo4;
import static com.bartronics.timeattendance.Util.Constant.sampleEmpNo5;
import static com.bartronics.timeattendance.Util.Constant.sampleEmpNo6;

public class ValidateEmployeeActivity extends AppCompatActivity {

    @BindView(R.id.employeeId_editText)
    EditText employeeIdEditText;
    @BindView(R.id.validate_button)
    Button validateButton;



    public static Boolean isRegistered = false;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_employee);
        ButterKnife.bind(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    @OnClick(R.id.validate_button)
    public void onClick() {
        validate();
    }

    private void validate() {

        // TODO: 16-01-2017  Validate Employee No And check if registered

        if(sampleEmpNo1.equals(employeeIdEditText.getText().toString()) ||
                sampleEmpNo2.equals(employeeIdEditText.getText().toString()) ||
                sampleEmpNo3.equals(employeeIdEditText.getText().toString()) ||
                sampleEmpNo4.equals(employeeIdEditText.getText().toString()) ||
                sampleEmpNo5.equals(employeeIdEditText.getText().toString()) ||
                sampleEmpNo6.equals(employeeIdEditText.getText().toString())
                ){
            isRegistered = true;
        }else {
            isRegistered = false;
        }

        if(isRegistered){
            SharedPreferences.Editor editor = sharedpreferences.edit();

            String empNo = employeeIdEditText.getText().toString();
            editor.putString(EmpNo, empNo);
            editor.commit();

            Intent intent = new Intent(this, SetOrChangePinActivity.class);
            intent.putExtra("emp_id",employeeIdEditText.getText().toString());
            startActivity(intent);
        }else {
            Toast.makeText(this, "Please Enter Valid Employee Number", Toast.LENGTH_SHORT).show();
        }

    }
}
