package com.mindex.challenge.service.impl;

import java.util.Calendar;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {

    private final float STARTING_SALARY = 5000.00f;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public Compensation create(String id) {
        Employee employee = employeeService.read(id);
        employee.setSalary(STARTING_SALARY);
        employee.setEffectiveDate(Calendar.getInstance().getTime());
        return buildCompensation(employee);
    }

    @Override
    public Compensation read(String id) {
        return buildCompensation(employeeService.read(id));
    }

    private Compensation buildCompensation(Employee employee) {
        Compensation compensation = new Compensation();
        compensation.setEmployee(employee);
        compensation.setEffectiveDate(employee.getEffectiveDate());
        compensation.setSalary(employee.getSalary());

        return compensation;
    }

}
