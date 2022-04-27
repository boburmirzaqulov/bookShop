package uz.yt.springdata.mapping;

import uz.yt.springdata.dao.Course;
import uz.yt.springdata.dao.Student;
import uz.yt.springdata.dto.CourseDTO;
import uz.yt.springdata.dto.StudentDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CourseMapping {
    public static CourseDTO toDto(Course course, List<Student> students){
        if (course == null) return null;
        List<StudentDTO> students2 = null;
        if (students != null){
            students2 = students
                    .stream()
                    .map(e -> StudentMapping.toDto(e, null))
                    .collect(Collectors.toList());
        }
        return new CourseDTO(
                course.getId(),
                course.getName(),
                course.getDuration(),
                students2
        );
    }
}
