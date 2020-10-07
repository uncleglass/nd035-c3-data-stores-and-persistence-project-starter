package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final PetService petService;
    private final EmployeeService employeeService;
    private final CustomerService customerService;

    public ScheduleService(
            ScheduleRepository scheduleRepository,
            PetService petService,
            EmployeeService employeeService,
            CustomerService customerService) {
        this.scheduleRepository = scheduleRepository;
        this.petService = petService;
        this.employeeService = employeeService;
        this.customerService = customerService;
    }

    public Schedule addSchedule(Schedule schedule) {
        Schedule savedSchedule = scheduleRepository.save(schedule);
        if (schedule.getEmployees() != null) {
            Set<Employee> employees = schedule.getEmployees();
            employees.forEach(employee ->
                    employee.getSchedules().add(savedSchedule));
        }
        if (schedule.getPets() != null) {
            Set<Pet> pets = schedule.getPets();
            pets.forEach(pet ->
                    pet.getSchedules().add(savedSchedule));
        }
        return savedSchedule;
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(Long petId) {
        Pet pet = petService.getPetById(petId);
        return scheduleRepository.findAllByPetsContains(pet);
    }

    public List<Schedule> getScheduleForEmployee(Long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return scheduleRepository.findAllByEmployeesContains(employee);
    }

    public List<Schedule> getScheduleForCustomer(Long customerId) {
        List<Pet> pets = customerService.getCustomerById(customerId).getPets();
        return scheduleRepository.findAllByPetsIn(pets);
    }
}
