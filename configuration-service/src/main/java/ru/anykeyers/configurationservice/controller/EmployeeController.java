package ru.anykeyers.configurationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.commonsapi.domain.dto.UserDTO;
import ru.anykeyers.configurationservice.service.EmployeeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/configuration/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<UserDTO> getEmployees(@RequestParam("carWashId") Long carWashId) {
        return employeeService.getCarWashEmployees(carWashId);
    }

}
