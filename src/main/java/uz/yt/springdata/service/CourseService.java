package uz.yt.springdata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yt.springdata.dao.Course;
import uz.yt.springdata.dao.CourseStudent;
import uz.yt.springdata.dao.Student;
import uz.yt.springdata.dto.CourseDTO;
import uz.yt.springdata.dto.ResponseDTO;
import uz.yt.springdata.dto.StudentDTO;
import uz.yt.springdata.mapping.CourseMapping;
import uz.yt.springdata.repository.CourseRepository;
import uz.yt.springdata.repository.CourseStudentRepository;
import uz.yt.springdata.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final CourseStudentRepository courseStudentRepository;

    public ResponseDTO<CourseDTO> getById(Integer id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            CourseDTO courseDTO = CourseMapping.toDto(course, course.getStudents());
            return new ResponseDTO<>(true, 0, "OK", courseDTO, null);
        }
        return new ResponseDTO<>(false, -1, "Not Found", null, null);
    }

    public ResponseDTO<CourseDTO> addCourse(CourseDTO courseDTO){
        List<Integer> listIds = courseDTO.getStudents()
                .stream()
                .map(StudentDTO::getId)
                .collect(Collectors.toList());
        List<Student> students = studentRepository.findAllById(listIds);
        Course course = CourseMapping.toEntity(courseDTO, null);
        course.setStudents(students);

        courseRepository.save(course);

        return new ResponseDTO<>(true, 0, "OK", CourseMapping.toDto(course, course.getStudents()), null);
    }

    public ResponseDTO<CourseDTO> addStudent(CourseDTO courseDTO) {
        Optional<Course> courseOptional1 = courseRepository.findById(courseDTO.getId());
        if (courseOptional1.isPresent()) {
            Course course = courseOptional1.get();
            List<Integer> list = courseOptional1.get().getStudents()
                    .stream()
                    .map(Student::getId)
                    .collect(Collectors.toList());

            List<Integer> listIds = courseDTO.getStudents()
                    .stream()
                    .map(StudentDTO::getId)
                    .collect(Collectors.toList());

            listIds.removeAll(list);

            List<Student> students = studentRepository.findAllById(listIds);

            List<CourseStudent> courseStudentList = students
                    .stream()
                    .map(student -> {
                        CourseStudent courseStudent = new CourseStudent();
                        courseStudent.setCourseId(courseDTO.getId());
                        courseStudent.setStudentId(student.getId());
                        return courseStudent;
                    })
                    .collect(Collectors.toList());

            courseStudentRepository.saveAll(courseStudentList);

            listIds.addAll(list);

            List<Student> studentList = studentRepository.findAllById(listIds);

            return new ResponseDTO<>(true, 0, "OK", CourseMapping.toDto(course, studentList), null);
        }
        return new ResponseDTO<>(false, -1, "Course id xato", courseDTO, null);
    }
}
