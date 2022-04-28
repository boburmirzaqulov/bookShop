package uz.yt.springdata.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "course")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(generator = "course_id_seq")
    @SequenceGenerator(name = "course_id_seq", sequenceName = "course_id_seq", allocationSize = 1)
    private Integer id;

    private String name;

    private Integer duration;

    @ManyToMany
    @JoinTable(
            name = "course_student",
            inverseJoinColumns = @JoinColumn(name = "student_id"),
            joinColumns = @JoinColumn(name = "course_id"))
    List<Student> students;
}
