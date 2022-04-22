package uz.yt.springdata.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "publisher")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Publisher {
    @Id
    @GeneratedValue(generator = "publisher_id_seq")
    @SequenceGenerator(name = "publisher_id_seq", sequenceName = "publisher_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "addressid")
    private Address address;

}
