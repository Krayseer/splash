package ru.anykeyers.configurationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.commonsapi.domain.dto.UserDTO;
import ru.anykeyers.configurationservice.domain.ControllerName;
import ru.anykeyers.configurationservice.service.EmployeeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ControllerName.EMPLOYEE_NAME)
@Tag(name = "Обработка работников автомойки")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "Получить список работников автомойки")
    @GetMapping
    public List<UserDTO> getEmployees(@RequestParam("carWashId") Long carWashId) {
        return employeeService.getCarWashEmployees(carWashId);
    }

}
