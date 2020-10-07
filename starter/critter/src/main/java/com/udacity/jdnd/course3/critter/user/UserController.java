package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.ConverterUtil;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final PetService petService;
    private final ConverterUtil converter;

    public UserController(
            CustomerService customerService,
            EmployeeService employeeService,
            PetService petService,
            ConverterUtil converter) {
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.petService = petService;
        this.converter = converter;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = customerService.addCustomer(converter.convertCustomerDTOToEntity(customerDTO));
        return converter.convertEntityToCustomerDTO(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return converter.convertEntitiesToCustomerDTOList(customers);
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        Long customerId = pet.getOwner().getId();
        return converter.convertEntityToCustomerDTO(customerService.getCustomerById(customerId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.addEmployee(converter.convertEmployeeDTOToEntity(employeeDTO));
        return converter.convertEntityToEmployeeDTO(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return converter.convertEntityToEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeService.updateEmployee(employee);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeService.getEmployeesForService(employeeDTO);
        return converter.convertEntitiesToEmployeeList(employees);
    }
}
