package uz.yt.springdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.yt.springdata.dao.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
}
