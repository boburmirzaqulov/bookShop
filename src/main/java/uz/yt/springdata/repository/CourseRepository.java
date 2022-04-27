package uz.yt.springdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.yt.springdata.dao.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
}
