package uz.yt.springdata.dao;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(generator = "book_id_seq")
    @SequenceGenerator(name = "book_id_seq", sequenceName = "books_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "nameuz")
    private String nameUz;

    @Column(name = "nameru")
    private String nameRu;

    private BigDecimal cost;

    @Column(name = "publisheddate")
    private Date publishedDate;

    @Column(name = "pagecount")
    private Integer pageCount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "authorid")
    private Author author;

    private String genre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "publisherid")
    private Publisher publisher;
}
