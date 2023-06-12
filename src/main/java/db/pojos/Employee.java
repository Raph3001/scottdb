package db.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class Employee {
    private String name, job, manager;
    private LocalDate hireDate;
    private Integer salary, comm;
    private Department department;

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", job='" + job + '\'' +
                ", manager='" + manager + '\'' +
                ", hireDate=" + hireDate +
                ", salary=" + salary +
                ", comm=" + comm +
                ", department = " + department.toString() +
                '}';
    }
}
