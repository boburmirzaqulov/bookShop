package uz.yt.springdata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;
    private List<CourseDTO> courses;
}
