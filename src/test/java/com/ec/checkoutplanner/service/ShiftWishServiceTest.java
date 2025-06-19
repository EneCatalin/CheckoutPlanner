package com.ec.checkoutplanner.service;

import com.ec.checkoutplanner.constants.Role;
import com.ec.checkoutplanner.constants.ShiftType;
import com.ec.checkoutplanner.dto.CreateShiftWishRequest;
import com.ec.checkoutplanner.entity.Employee;
import com.ec.checkoutplanner.entity.ShiftWish;
import com.ec.checkoutplanner.exception.employee.EmployeeNotFoundException;
import com.ec.checkoutplanner.exception.shiftWish.ShiftWishRetrievalException;
import com.ec.checkoutplanner.exception.shiftWish.WishAlreadyExistsException;
import com.ec.checkoutplanner.repository.EmployeeRepository;
import com.ec.checkoutplanner.repository.ShiftWishRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class ShiftWishServiceTest {

    @Mock
    private ShiftWishRepository shiftWishRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private ShiftWishService shiftWishService;

    @Test
    void createWish_success() {
        CreateShiftWishRequest request = new CreateShiftWishRequest(1L, LocalDate.now(), ShiftType.EARLY);
        Employee employee = new Employee(1L, "Alice", Role.EMPLOYEE);
        ShiftWish wish = new ShiftWish();
        wish.setId(10L);
        wish.setEmployee(employee);
        wish.setDate(request.date());
        wish.setShiftType(request.shiftType());

        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        Mockito.when(shiftWishRepository.existsByEmployeeAndDate(employee, request.date())).thenReturn(false);
        Mockito.when(shiftWishRepository.save(any())).thenReturn(wish);

        ShiftWish result = shiftWishService.createWish(request);

        assertEquals(10L, result.getId());
        assertEquals(employee, result.getEmployee());
        assertEquals(ShiftType.EARLY, result.getShiftType());
    }

    @Test
    void createWish_employeeNotFound() {
        CreateShiftWishRequest request = new CreateShiftWishRequest(1L, LocalDate.now(), ShiftType.LATE);
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> shiftWishService.createWish(request));
    }

    @Test
    void createWish_duplicateWish() {
        CreateShiftWishRequest request = new CreateShiftWishRequest(1L, LocalDate.now(), ShiftType.LATE);
        Employee employee = new Employee(1L, "Bob", Role.EMPLOYEE);

        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        Mockito.when(shiftWishRepository.existsByEmployeeAndDate(employee, request.date())).thenReturn(true);

        assertThrows(WishAlreadyExistsException.class, () -> shiftWishService.createWish(request));
    }

    @Test
    void getAllShiftWishes_success() {
        List<ShiftWish> wishes = List.of(new ShiftWish(), new ShiftWish());
        Mockito.when(shiftWishRepository.findAll()).thenReturn(wishes);

        List<ShiftWish> result = shiftWishService.getAllShiftWishes();
        assertEquals(2, result.size());
    }

    @Test
    void getWishesByEmployeeName_success() {
        String name = "Alice";
        Employee employee = new Employee(1L, name, Role.EMPLOYEE);
        List<ShiftWish> wishes = List.of(new ShiftWish());

        Mockito.when(employeeRepository.findByName(name)).thenReturn(Optional.of(employee));
        Mockito.when(shiftWishRepository.findByEmployee(employee)).thenReturn(wishes);

        List<ShiftWish> result = shiftWishService.getWishesByEmployeeName(name);
        assertEquals(1, result.size());
    }

    @Test
    void getWishesByEmployeeName_notFound() {
        Mockito.when(employeeRepository.findByName("Ghost")).thenReturn(Optional.empty());
        assertThrows(ShiftWishRetrievalException.class, () -> shiftWishService.getWishesByEmployeeName("Ghost"));
    }
}