package uz.yt.springdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.yt.springdata.dao.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
