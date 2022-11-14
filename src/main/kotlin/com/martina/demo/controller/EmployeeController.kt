package com.martina.demo.controller

import com.martina.demo.service.EmployeeService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/employee")
class EmployeeController(
    private val employeeService: EmployeeService
) {

    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadHierarchyDocument(@RequestBody(required = true) file: MultipartFile): ResponseEntity<String> {
        return ResponseEntity.ok(employeeService.handleHierarchyDocument(file))
    }

    @GetMapping()
    fun getHierarchyByEmployeeName(@RequestParam name: String): ResponseEntity<String> {
        return ResponseEntity.ok(employeeService.getHierarchyByEmployeeName(name))
    }
}
