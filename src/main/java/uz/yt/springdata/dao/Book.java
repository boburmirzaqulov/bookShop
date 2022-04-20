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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nameuz")
    private String nameUz;
    @Column(name = "nameru")
    private String nameRu;
    @Column(name = "cost")
    private BigDecimal cost;
    @Column(name = "publisheddate")
    private Date publishedDate;
    @Column(name = "pagecount")
    private Integer pageCount;
    @Column(name = "authorid")
    private Integer authorId;
    @Column(name = "genre")
    private String genre;
    @Column(name = "publisherid")
    private Integer publisherId;
}
