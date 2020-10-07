package com.udacity.jdnd.course3.critter;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.user.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ConverterUtil {
    private final CustomerService customerService;
    private final PetService petService;
    private final EmployeeService employeeService;

    public ConverterUtil(
            CustomerService customerService,
            PetService petService,
            EmployeeService employeeService) {
        this.customerService = customerService;
        this.petService = petService;
        this.employeeService = employeeService;
    }

    public Pet convertPetDTOtoEntity(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        if (petDTO.getOwnerId() != null) {
            Customer customer = customerService.getCustomerById(petDTO.getOwnerId());
            pet.setOwner(customer);
        }
        return pet;
    }

    public PetDTO convertEntityToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if (pet.getOwner() != null) {
            Long ownerId = pet.getOwner().getId();
            petDTO.setOwnerId(ownerId);
        }
        return petDTO;
    }

    public List<PetDTO> convertEntitiesToPetDTOList(List<Pet> pets) {
        return pets.stream()
                .map(this::convertEntityToPetDTO)
                .collect(Collectors.toList());
    }

    public Customer convertCustomerDTOToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        if (customerDTO.getPetIds() != null) {
            List<Pet> pets =
                    customerDTO.getPetIds().stream()
                            .map(petService::getPetById)
                            .collect(Collectors.toList());
            customer.setPets(pets);
        }
        return customer;
    }

    public CustomerDTO convertEntityToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        if (customer.getPets() != null) {
            List<Long> petIds =
                    customer.getPets().stream()
                            .map(Pet::getId)
                            .collect(Collectors.toList());
            customerDTO.setPetIds(petIds);
        }
        return customerDTO;
    }

    public List<CustomerDTO> convertEntitiesToCustomerDTOList(List<Customer> customers) {
        return customers.stream()
                .map(this::convertEntityToCustomerDTO)
                .collect(Collectors.toList());
    }

    public Employee convertEmployeeDTOToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

    public EmployeeDTO convertEntityToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    public List<EmployeeDTO> convertEntitiesToEmployeeList(List<Employee> employees) {
        return employees.stream()
                .map(this::convertEntityToEmployeeDTO)
                .collect(Collectors.toList());
    }

    public Schedule convertScheduleDTOToEntity(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        if (scheduleDTO.getEmployeeIds() != null) {
            Set<Employee> employees =
                    scheduleDTO.getEmployeeIds().stream()
                            .map(employeeService::getEmployeeById)
                            .collect(Collectors.toSet());
            schedule.setEmployees(employees);
        }
        if (scheduleDTO.getPetIds() != null) {
            Set<Pet> pets =
                    scheduleDTO.getPetIds().stream()
                            .map(petService::getPetById)
                            .collect(Collectors.toSet());
            schedule.setPets(pets);
        }
        return schedule;
    }

    public ScheduleDTO convertEntityToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        if (schedule.getEmployees() != null) {
            List<Long> employeesIds =
                    schedule.getEmployees().stream()
                            .map(Employee::getId)
                            .collect(Collectors.toList());
            scheduleDTO.setEmployeeIds(employeesIds);
        }
        if (schedule.getPets() != null) {
            List<Long> petIds =
                    schedule.getPets().stream()
                            .map(Pet::getId)
                            .collect(Collectors.toList());
            scheduleDTO.setPetIds(petIds);
        }
        return scheduleDTO;
    }

    public List<ScheduleDTO> convertEntitiesToScheduleDTOList(List<Schedule> schedules) {
        return schedules.stream()
                .map(this::convertEntityToScheduleDTO)
                .collect(Collectors.toList());
    }
}
