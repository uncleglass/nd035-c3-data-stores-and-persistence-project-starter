package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.repository.CrudRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    List<Employee> findAllByDaysAvailableContains(DayOfWeek dayOfWeek);
}
