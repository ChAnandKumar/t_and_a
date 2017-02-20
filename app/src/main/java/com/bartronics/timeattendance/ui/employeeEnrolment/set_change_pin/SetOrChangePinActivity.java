package com.bartronics.timeattendance.ui.employeeEnrolment.set_change_pin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bartronics.timeattendance.R;
import com.bartronics.timeattendance.Util.Constant;
import com.bartronics.timeattendance.ui.employeeEnrolment.profile.ProfileActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bartronics.timeattendance.R.id.setPin_editText;
import static com.bartronics.timeattendance.Util.Constant.EmpNo;
import static com.bartronics.timeattendance.Util.Constant.isRegKey;
import static com.bartronics.timeattendance.Util.Constant.regPin;

public class SetOrChangePinActivity extends AppCompatActivity {

    @BindView(setPin_editText)
    EditText setPinEditText;
    @BindView(R.id.ConfirmPin_editText)
    EditText ConfirmPinEditText;
    @BindView(R.id.next_button)
    Button nextButton;
    @BindView(R.id.empNo_textView)
    TextView empNoTextView;
    private SharedPreferences sharedPref;
    private String empNo;
    private String emp_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_or_change_pin);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        emp_id = bundle.getString("emp_id");

        sharedPref = getSharedPreferences(Constant.MyPREFERENCES,Context.MODE_PRIVATE);

        empNo = sharedPref.getString(EmpNo, "");

        empNoTextView.setText("Set Pin For Employee Id : "+empNo);
    }


    @OnClick(R.id.next_button)
    public void onClick() {

    // TODO: 16-01-2017  check size of pin is 4 and send it to server when confirmation received go to profile

        String pinNo = setPinEditText.getText().toString();
        if(pinNo.length()< 4 || pinNo.length()>4)
        {
            setPinEditText.setError("Pin must have min 4 numbers.");
        }else if(!ConfirmPinEditText.getText().toString().equals(pinNo)){
            ConfirmPinEditText.setError("Pin must match.");
        }else{
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(isRegKey, true);
            editor.putString(regPin,pinNo);
            editor.commit();

            //Toast.makeText(this, "Pin No is : "+pinNo, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("pin",regPin);
            intent.putExtra("emp_id",emp_id);
            startActivity(intent);
        }
    }
}
