package uz.yt.springdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yt.springdata.dao.CourseStudent;

public interface CourseStudentRepository extends JpaRepository<CourseStudent, Integer> {

}
