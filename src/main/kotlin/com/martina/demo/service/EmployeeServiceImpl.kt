package com.martina.demo.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.martina.demo.exception.JsonParsingException
import com.martina.demo.exception.LoopException
import com.martina.demo.data.model.Employee
import com.martina.demo.data.repository.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.charset.Charset
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import org.apache.commons.io.IOUtils

@Service
class EmployeeServiceImpl(
    private val employeeRepository: EmployeeRepository
) : EmployeeService {

    @Autowired
    private val objectMapper = ObjectMapper()

    override fun handleHierarchyDocument(file: MultipartFile): String? {
        val input = IOUtils.toString(file.inputStream, Charset.defaultCharset())

        val parsedJson = Json.parseToJsonElement(input).jsonObject
        val root = findTheRoot(parsedJson)
        storeTheNewFile(parsedJson)
        return buildTheHierarchyResponse(root)
    }

    override fun getHierarchyByEmployeeName(name: String): String? {
        val employee = employeeRepository.findByName(name)
        return if (employee != null) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(employee)
        } else {
            null
        }
    }

    private fun buildTheHierarchyResponse(root: String): String? {
        val value = findOrCreate(root)
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value)
    }

    private fun storeTheNewFile(parsedJson: JsonObject) {
        parsedJson.entries.forEach { entry ->
            run {
                val employeeName = entry.key
                val employee = findOrCreate(employeeName)
                val supervisorName = entry.value.toString()
                val supervisor = try {
                    findOrCreate(
                        supervisorName.trim()
                            .subSequence(1, supervisorName.length - 1).toString()
                    )
                } catch (e: IndexOutOfBoundsException) {
                    throw JsonParsingException("Error while parsing supervisor name")
                }

                if (employeeRepository.existsByNameAndSupervisor_Name(supervisorName, employeeName)) {
                    throw  ("Loop found, invalid hierarchy file", null)
                }

                if (!employeeRepository.existsByNameAndSupervisor_Name(employeeName, supervisorName)) {
                    employee.supervisor = supervisor
                    employeeRepository.saveAndFlush(employee)
                }
            }
        }
    }

    private fun findOrCreate(key: String): Employee {
        val foundEmployee = employeeRepository.findByName(key)
        return if (foundEmployee == null) {
            val employee = Employee(name = key)
            employeeRepository.saveAndFlush(employee)
        } else {
            foundEmployee
        }
    }

    private fun findTheRoot(parsedJson: JsonObject): String {
        var rootCounter = 0
        var root = ""

        parsedJson.values.forEach {
            val trim = it.toString().trim().subSequence(1, it.toString().length - 1).toString()
            if (!parsedJson.containsKey(trim) && root != trim) {
                rootCounter++
                root = trim
            }

            if (rootCounter > 1) {
                throw java.lang.Exception("Multiple roots found, invalid file")
            }
        }

        if (rootCounter == 0) {
            throw Exception("No roots found, invalid file")
        }
        return root
    }
}
