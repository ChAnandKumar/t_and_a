package com.bartronics.timeattendance.model;

/**
 * Created by anand.chandaliya on 16-02-2017.
 */

public class ReportModel {

    int keyId;

    public ReportModel() {

    }

    public int getKeyId() {
        return keyId;
    }

    public void setKeyId(int keyId) {
        this.keyId = keyId;
    }

    private String empName;
    private String empDescriton;
    String empNumber;
    private int empId;
    private int empPin;

    public int getEmpPin() {
        return empPin;
    }

    public void setEmpPin(int empPin) {
        this.empPin = empPin;
    }

    private String empInTime;
    private String empOutTime;
    private String empInDate;
    private String empLocation;
    private String empDepartment;
    private String empWorkLocation;
    private String empDesignaiton;

    public String getEmpDesignaiton() {
        return empDesignaiton;
    }

    public void setEmpDesignaiton(String empDesignaiton) {
        this.empDesignaiton = empDesignaiton;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpDescriton() {
        return empDescriton;
    }

    public void setEmpDescriton(String empDescriton) {
        this.empDescriton = empDescriton;
    }

    public String getEmpNumber() {
        return empNumber;
    }

    public void setEmpNumber(String empNumber) {
        this.empNumber = empNumber;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpInTime() {
        return empInTime;
    }

    public void setEmpInTime(String empInTime) {
        this.empInTime = empInTime;
    }

    public String getEmpOutTime() {
        return empOutTime;
    }

    public void setEmpOutTime(String empOutTime) {
        this.empOutTime = empOutTime;
    }

    public String getEmpInDate() {
        return empInDate;
    }

    public void setEmpInDate(String empInDate) {
        this.empInDate = empInDate;
    }

    public String getEmpLocation() {
        return empLocation;
    }

    public ReportModel(int keyId, String empName, String empDescriton,
                       String empNumber, int empId, String empInTime,
                       String empOutTime, String empInDate, String empLocation,
                       String empDepartment, String empWorkLocation, String empDesignaiton) {
        this.keyId = keyId;
        this.empName = empName;
        this.empDescriton = empDescriton;
        this.empNumber = empNumber;
        this.empId = empId;
        this.empInTime = empInTime;
        this.empOutTime = empOutTime;
        this.empInDate = empInDate;
        this.empLocation = empLocation;
        this.empDepartment = empDepartment;
        this.empWorkLocation = empWorkLocation;
        this.empDesignaiton = empDesignaiton;
    }

    public ReportModel( String empName,  int empId,String empDescriton,
                       String empNumber, String empInTime,
                       String empOutTime, String empInDate, String empLocation,
                       String empDepartment, String empWorkLocation, String empDesignaiton) {
        this.empName = empName;
        this.empDescriton = empDescriton;
        this.empNumber = empNumber;
        this.empId = empId;
        this.empInTime = empInTime;
        this.empOutTime = empOutTime;
        this.empInDate = empInDate;
        this.empLocation = empLocation;
        this.empDepartment = empDepartment;
        this.empWorkLocation = empWorkLocation;
        this.empDesignaiton = empDesignaiton;
    }


    public void setEmpLocation(String empLocation) {
        this.empLocation = empLocation;
    }

    public String getEmpDepartment() {
        return empDepartment;
    }

    public void setEmpDepartment(String empDepartment) {
        this.empDepartment = empDepartment;
    }

    public String getEmpWorkLocation() {
        return empWorkLocation;
    }

    public void setEmpWorkLocation(String empWorkLocation) {
        this.empWorkLocation = empWorkLocation;
    }
}
