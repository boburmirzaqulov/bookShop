package uz.yt.springdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.yt.springdata.dao.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
