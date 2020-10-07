package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    List<Schedule> findAll();

    List<Schedule> findAllByPetsContains(Pet pet);

    List<Schedule> findAllByEmployeesContains(Employee employee);

    List<Schedule> findAllByPetsIn(List<Pet> pets);
}
