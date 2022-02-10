package com.mindex.challenge.data;

import java.util.Map;
import java.util.Set;

import java.util.HashMap;
import java.util.HashSet;

public class ReportingStructure implements Structure {
    private final Map<String, Set<String>> structure = new HashMap<>();

    private Integer numberOfReports = 0;
    private String employee;

    public ReportingStructure(String employee) {
        this.employee = employee;
        structure.put(this.employee, new HashSet<>());
    }

    @Override
    public void add(String manager, String employee) {
        if (!structure.keySet().contains(manager)) {
            structure.put(manager, new HashSet<>());
        }

        if (structure.get(manager).add(employee)) {
            numberOfReports += 1;
        }
    }

    public Integer getNumberOfReports() {
        return numberOfReports;
    }

    public String getEmployee() {
        return employee;
    }
}
