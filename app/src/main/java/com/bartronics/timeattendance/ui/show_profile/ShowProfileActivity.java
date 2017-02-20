package com.bartronics.timeattendance.ui.show_profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.bartronics.timeattendance.R;
import com.bartronics.timeattendance.model.ReportModel;
import com.bartronics.timeattendance.model.db.DbHandler;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bartronics.timeattendance.Util.Constant.EmpNo;
import static com.bartronics.timeattendance.Util.Constant.MyPREFERENCES;
import static com.bartronics.timeattendance.Util.Constant.isRegKey;

public class ShowProfileActivity extends AppCompatActivity {

    @BindView(R.id.name_textView)
    TextView nameTextView;
    @BindView(R.id.designation_textView)
    TextView designationTextView;
    @BindView(R.id.department_textView)
    TextView departmentTextView;
    @BindView(R.id.work_location_textView)
    TextView workLocationTextView;
    @BindView(R.id.employee_id_textView)
    TextView employeeIdTextView;
    private SharedPreferences sharedpreferences;
    private String empNo;
    private boolean isReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);
        ButterKnife.bind(this);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        empNo = sharedpreferences.getString(EmpNo, "");
        isReg = sharedpreferences.getBoolean(isRegKey, false);

        DbHandler db = new DbHandler(this);

        List<ReportModel> allEmps = db.getAllEmps();

        //Log.i("anand", "onCreate: "+allEmps.get(0).getEmpId());
        for (int i = 0; i < allEmps.size(); i++) {
            try {
                if (allEmps.get(i).getEmpId() == Integer.parseInt(empNo)) {
                    nameTextView.setText(allEmps.get(i).getEmpName());
                    designationTextView.setText(allEmps.get(i).getEmpDesignaiton());
                    departmentTextView.setText(allEmps.get(i).getEmpDepartment());
                    workLocationTextView.setText(allEmps.get(i).getEmpWorkLocation());
                    employeeIdTextView.setText(allEmps.get(i).getEmpId() + "");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
