package com.romeulima.daojdbc.dao.department;

import com.romeulima.daojdbc.domain.department.Department;

import java.util.List;

public interface DepartmentDao {

    void insert(Department department);
    void update(Department department);
    void deleteById(Integer id);
    Department findById(Integer id);
    List<Department> findAllDepartments();
}
