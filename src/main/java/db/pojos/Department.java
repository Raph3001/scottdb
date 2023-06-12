package db.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@ToString
public class Department {
    private Integer deptno;
    private String name, location;
}
