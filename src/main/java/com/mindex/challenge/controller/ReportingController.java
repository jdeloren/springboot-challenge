package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.data.Structure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReportingController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingController.class);

    @Autowired
    private EmployeeService employeeService;

    private void buildDirectReports(Structure structure, Employee employee) {
        if (employee.getDirectReports() != null) {
            employee.getDirectReports().stream().map(report -> report.getEmployeeId())
                    .forEach(e -> {
                        structure.add(employee.getEmployeeId(), e);
                        buildDirectReports(structure, employeeService.read(e));
                    });
        }
    }

    @GetMapping("/reports/{id}")
    public ReportingStructure read(@PathVariable String id) {
        LOG.debug("Received reporting get request for [{}]", id);

        Employee employee = employeeService.read(id);
        ReportingStructure structure = new ReportingStructure(employee.getEmployeeId());

        buildDirectReports(structure, employee);

        return structure;
    }
}
