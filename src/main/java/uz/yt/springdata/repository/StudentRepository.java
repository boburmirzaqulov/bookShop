package uz.yt.springdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.yt.springdata.dao.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
}
