package uz.yt.springdata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yt.springdata.dao.Course;
import uz.yt.springdata.dao.CourseStudent;
import uz.yt.springdata.dao.Student;
import uz.yt.springdata.dto.CourseDTO;
import uz.yt.springdata.dto.ResponseDTO;
import uz.yt.springdata.dto.StudentDTO;
import uz.yt.springdata.mapping.StudentMapping;
import uz.yt.springdata.repository.CourseRepository;
import uz.yt.springdata.repository.CourseStudentRepository;
import uz.yt.springdata.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final CourseStudentRepository courseStudentRepository;

    public ResponseDTO<StudentDTO> getById(Integer id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            StudentDTO studentDTO = StudentMapping.toDto(student, student.getCourses());
            return new ResponseDTO<>(true, 0, "OK", studentDTO, null);
        }
        return new ResponseDTO<>(false, -1, "Not Found", null, null);
    }

    public ResponseDTO<StudentDTO> addStudent(StudentDTO studentDTO) {
        List<Integer> listIds = studentDTO.getCourses()
                .stream()
                .map(CourseDTO::getId)
                .collect(Collectors.toList());
        List<Course> courses = courseRepository.findAllById(listIds);
        Student student = StudentMapping.toEntity(studentDTO, null);
        student.setCourses(courses);

        studentRepository.save(student);

        return new ResponseDTO<>(true, 0, "OK", StudentMapping.toDto(student, student.getCourses()), null);
    }

    public ResponseDTO<StudentDTO> addCourse(StudentDTO studentDTO) {
        Optional<Student> studentOptional1 = studentRepository.findById(studentDTO.getId());
        if (studentOptional1.isPresent()) {
            Student student = studentOptional1.get();
            List<Integer> list = studentOptional1.get().getCourses()
                    .stream()
                    .map(Course::getId)
                    .collect(Collectors.toList());

            List<Integer> listIds = studentDTO.getCourses()
                    .stream()
                    .map(CourseDTO::getId)
                    .collect(Collectors.toList());

            listIds.removeAll(list);

            List<Course> courses = courseRepository.findAllById(listIds);

            List<CourseStudent> courseStudentList = new ArrayList<>();
            for (Course cours : courses) {
                CourseStudent courseStudent = new CourseStudent();
                courseStudent.setStudentId(studentDTO.getId());
                courseStudent.setCourseId(cours.getId());
                courseStudentList.add(courseStudent);
            }
            courseStudentRepository.saveAll(courseStudentList);

            listIds.addAll(list);

            List<Course> courseList = courseRepository.findAllById(listIds);

            return new ResponseDTO<>(true, 0, "OK", StudentMapping.toDto(student, courseList), null);

        }
        return new ResponseDTO<>(false, -1, "Student id xato", studentDTO, null);
    }
}
