package com.bartronics.timeattendance.ui.employeeEnrolment.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.bartronics.timeattendance.R;
import com.bartronics.timeattendance.model.ReportModel;
import com.bartronics.timeattendance.model.db.DbHandler;
import com.bartronics.timeattendance.ui.loginPage.LoginActivity;
import com.bartronics.timeattendance.ui.loginPage.SingInActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity implements ProfileView{

    @BindView(R.id.name_editText)
    EditText nameEditText;
    @BindView(R.id.designation_editText)
    EditText designationEditText;
    @BindView(R.id.department_editText)
    EditText departmentEditText;
    @BindView(R.id.workLocation_editText)
    EditText workLocationEditText;
    @BindView(R.id.submit_button)
    Button submitButton;
    private String empId;
    private String pin;
    private DbHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        pin = getIntent().getExtras().getString("pin");
        empId = getIntent().getExtras().getString("emp_id");

        handler = new DbHandler(this);

        List<ReportModel> allEmps = handler.getAllEmps();

        //Log.i("anand", "onCreate: "+allEmps.get(0).getEmpId());
        for (int i = 0; i < allEmps.size(); i++) {
            try {
                if (allEmps.get(i).getEmpId() == Integer.parseInt(empId)) {
                    nameEditText.setText(allEmps.get(i).getEmpName());
                    designationEditText.setText(allEmps.get(i).getEmpDesignaiton());
                    departmentEditText.setText(allEmps.get(i).getEmpDepartment());
                    workLocationEditText.setText(allEmps.get(i).getEmpWorkLocation());
                    //employeeIdTextView.setText(allEmps.get(i).getEmpId() + "");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @OnClick(R.id.submit_button)
    public void onClick() {

        String name = nameEditText.getText().toString();
        String designation = designationEditText.getText().toString();
        String department = departmentEditText.getText().toString();
        String workLocation = workLocationEditText.getText().toString();

        Log.i("anand", "empId: "+ empId);

        ReportModel reportModel = new ReportModel();
        reportModel.setEmpName(name);
        reportModel.setEmpDepartment(department);
        reportModel.setEmpDesignaiton(designation);
        reportModel.setEmpWorkLocation(workLocation);
        reportModel.setEmpId(Integer.parseInt(empId));
        reportModel.setEmpNumber(pin);
        //reportModel.setEmpPin(Integer.parseInt(pin));

        handler.addEmp(reportModel);

       Intent intent = new Intent(this, SingInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void saveData() {

    }
}
