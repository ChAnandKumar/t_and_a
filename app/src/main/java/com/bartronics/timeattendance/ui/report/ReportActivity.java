package com.bartronics.timeattendance.ui.report;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class ReportActivity extends AppCompatActivity {

    @BindView(R.id.report_rv)
    RecyclerView reportRv;

    @BindView(R.id.empId_textView)
    TextView empId_textView;

    private SharedPreferences sharedpreferences;
    private String empNo;
    private RecyclerView report_rv;
    private ReportAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        empNo = sharedpreferences.getString(EmpNo, "");
        empId_textView.setText("Employee ID : "+empNo);

        DbHandler db = new DbHandler(this);
        List<ReportModel> reports = db.getAllReport();

        /*for (int i = 0; i < reports.size(); i++) {
            Log.i("anand", "loc : " + reports.get(i).getEmpLocation() + " | date: " + reports.get(i).getEmpInDate() + "| in time : " + reports.get(i).getEmpInTime()
                    + " | out time : " + reports.get(i).getEmpOutTime());
        }*/

        report_rv = (RecyclerView) findViewById(R.id.report_rv);

        mAdapter = new ReportAdapter(reports);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        report_rv.setLayoutManager(mLayoutManager);
        report_rv.setItemAnimator(new DefaultItemAnimator());
        report_rv.setAdapter(mAdapter);

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
