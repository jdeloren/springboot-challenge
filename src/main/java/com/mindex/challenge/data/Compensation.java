package com.mindex.challenge.data;

import java.util.Date;

public class Compensation {
    private Employee employee;
    private Float salary;
    private Date effectiveDate;

    public Compensation() {
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
