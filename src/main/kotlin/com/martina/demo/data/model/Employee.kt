package com.martina.demo.data.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "Employee")
class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "employee_id", updatable = false)
    @JsonIgnore
    var id: Int = 0,

    var name: String? = null,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "supervisor_id")
    @JsonIgnore
    var supervisor: Employee? = null,

    @OneToMany(mappedBy = "supervisor", cascade = [CascadeType.ALL])
    @Column(nullable = true)
    var subordinates: Set<Employee>? = mutableSetOf()
) {
    override fun toString(): String {
        return name!!
    }
}
