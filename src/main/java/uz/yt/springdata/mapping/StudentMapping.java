package uz.yt.springdata.mapping;

import uz.yt.springdata.dao.Course;
import uz.yt.springdata.dao.Student;
import uz.yt.springdata.dto.CourseDTO;
import uz.yt.springdata.dto.StudentDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentMapping {
    public static StudentDTO toDto(Student student, List<Course> courses){
        if (student == null) return null;
        List<CourseDTO> courses2 = new ArrayList<>();
        if (courses != null){
            courses2 = courses
                    .stream()
                    .map(e -> CourseMapping.toDto(e, null))
                    .collect(Collectors.toList());
        }
        return new StudentDTO(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getAge(),
                courses2
        );
    }

    public static Student toEntity(StudentDTO studentDTO, List<CourseDTO> courses) {
        if (studentDTO == null) return null;
        List<Course> courses2 = new ArrayList<>();
        if (courses != null){
            courses2 = courses
                    .stream()
                    .map(e -> CourseMapping.toEntity(e, null))
                    .collect(Collectors.toList());
        }
        return new Student(
                studentDTO.getId(),
                studentDTO.getFirstName(),
                studentDTO.getLastName(),
                studentDTO.getAge(),
                courses2
        );
    }
}
