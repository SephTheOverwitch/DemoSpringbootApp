package com.martina.demo.data.repository

import com.martina.demo.data.model.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : JpaRepository<Employee, Int> {
    fun existsByNameAndSupervisor_Name(name: String, supervisorName: String): Boolean
    fun findByName(name: String): Employee?
}
