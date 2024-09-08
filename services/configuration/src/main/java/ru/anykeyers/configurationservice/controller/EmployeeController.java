package ru.anykeyers.configurationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.anykeyers.commonsapi.domain.user.UserDTO;
import ru.anykeyers.configurationservice.domain.ControllerName;
import ru.anykeyers.configurationservice.service.EmployeeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ControllerName.EMPLOYEE_CONTROLLER)
@Tag(name = "Обработка работников автомойки")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "Получить список работников автомойки")
    @GetMapping("/{carWashId}")
    public List<UserDTO> getEmployees(@PathVariable Long carWashId) {
        return employeeService.getCarWashEmployees(carWashId);
    }

    @Operation(summary = "Уволить работника с автомойки")
    @DeleteMapping("/{carWashId}")
    public void deleteEmployee(@PathVariable("carWashId") Long carWashId,
                               @RequestParam("userId") Long userId) {
        employeeService.deleteEmployee(carWashId, userId);
    }

}
