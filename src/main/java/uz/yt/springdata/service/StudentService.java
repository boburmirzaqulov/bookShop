package uz.yt.springdata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yt.springdata.dao.Student;
import uz.yt.springdata.dto.ResponseDTO;
import uz.yt.springdata.dto.StudentDTO;
import uz.yt.springdata.mapping.StudentMapping;
import uz.yt.springdata.repository.StudentRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public ResponseDTO<StudentDTO> getById(Integer id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            StudentDTO studentDTO = StudentMapping.toDto(student, student.getCourses());
            return new ResponseDTO<>(true, 0, "OK", studentDTO, null);
        }
        return new ResponseDTO<>(false, -1, "Not Found", null, null);
    }
}
