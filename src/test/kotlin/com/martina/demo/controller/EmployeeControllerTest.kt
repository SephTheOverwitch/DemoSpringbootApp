package com.martina.demo.controller

import com.martina.demo.controller.EmployeeController
import com.martina.demo.data.model.Employee
import com.martina.demo.service.EmployeeServiceImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@ExtendWith(
    SpringExtension::class
)
@WebAppConfiguration()
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = [EmployeeController::class])
internal class EmployeeControllerTest {

    @Autowired
    lateinit var wac: WebApplicationContext
    private var mockMvc: MockMvc? = null

    @MockBean
    private lateinit var employeeService: EmployeeServiceImpl

    val expectedResult = "{\r\n" +
            "  \"name\" : \"John\",\r\n" +
            "  \"subordinates\" : [ ]\r\n" +
            "}"

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()

        val existingSupervisor = Employee(name = "John")
        Mockito.`when`(employeeService.getHierarchyByEmployeeName("John")).thenReturn(expectedResult)
    }

    @Test
    fun getHierarchyByEmployeeName() {

        val name = "John"

        mockMvc!!.get("/employee") {
            param("name", name)
        }
            .andExpect {
                status { isOk() }
                content { string(expectedResult) }
            }
    }

    @Test
    fun getHierarchyByEmployeeName_NotExistingEmployee() {

        val name = "Jonas"

        mockMvc!!.get("/employee") {
            param("name", name)
        }
            .andExpect {
                status { isOk() }
                content { }
            }
    }
}
