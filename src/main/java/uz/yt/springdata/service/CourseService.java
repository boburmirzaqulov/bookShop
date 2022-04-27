package uz.yt.springdata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yt.springdata.dao.Course;
import uz.yt.springdata.dto.CourseDTO;
import uz.yt.springdata.dto.ResponseDTO;
import uz.yt.springdata.mapping.CourseMapping;
import uz.yt.springdata.repository.CourseRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public ResponseDTO<CourseDTO> getById(Integer id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            CourseDTO courseDTO = CourseMapping.toDto(course, course.getStudents());
            return new ResponseDTO<>(true, 0, "OK", courseDTO, null);
        }
        return new ResponseDTO<>(false, -1, "Not Found", null, null);
    }
}
