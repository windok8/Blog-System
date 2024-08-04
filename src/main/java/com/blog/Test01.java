package com.blog;

import java.util.*;
import java.util.stream.Collectors;


public class Test01 {

    public static void main(String[] args) {
        List<Employee> employeeList = Arrays.asList(
                new Employee("HR", 5000),
                new Employee("HR", 6000),
                new Employee("HR", 7000),
                new Employee("IT", 10000),
                new Employee("IT", 12000),
                new Employee("IT", 11000),
                new Employee("Sales", 8000),
                new Employee("Sales", 8500)
        );

        Map<String, List<Employee>> top5EmployeesByDepartment = employeeList
                .stream()
                .collect(Collectors.groupingBy(Employee::getDepartment))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue()
                                .stream()
                                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                                .limit(5)
                                .collect(Collectors.toList())
                ));
        top5EmployeesByDepartment.forEach((department, employees) -> {
            System.out.println("Department: " + department);
            employees.forEach(System.out::println);
        });

    }

}
