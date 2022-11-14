package com.martina.demo.service

import org.springframework.web.multipart.MultipartFile


interface EmployeeService {
    fun handleHierarchyDocument(file: MultipartFile): String?
    fun getHierarchyByEmployeeName(name: String): String?
}
