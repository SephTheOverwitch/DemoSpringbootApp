package com.martina.demo

import com.fasterxml.jackson.databind.ObjectMapper
import com.martina.demo.data.model.Employee
import com.martina.demo.data.repository.EmployeeRepository
import com.martina.demo.service.EmployeeServiceImpl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

internal class EmployeeServiceImplTest {

    @InjectMocks
    lateinit var employeeServiceImpl: EmployeeServiceImpl

    @Mock
    lateinit var employeeRepository: EmployeeRepository

    private val objectMapper = ObjectMapper()


    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        val existingSupervisor = Employee(name = "John")
        `when`(employeeRepository.findByName("John")).thenReturn(existingSupervisor)
    }

    @Test
    fun getHierarchyByEmployeeName_returnHierarchy() {
        val name = "John"
        val expectedResult = "{\n" +
                "  \"name\" : \"John\",\n" +
                "  \"subordinates\" : [ ]\n" +
                "}"

        val hierarchy = employeeServiceImpl.getHierarchyByEmployeeName(name)
        assertEquals(hierarchy, expectedResult)
    }

    @Test
    fun getHierarchyByEmployeeName_employeeNotFound() {
        val name = "George"

        val hierarchy = employeeServiceImpl.getHierarchyByEmployeeName(name)
        assertEquals(hierarchy, null)
    }
}
