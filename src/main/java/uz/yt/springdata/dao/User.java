package uz.yt.springdata.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "phonenumber")
    private String phoneNumber;

    private BigDecimal account;

    @Column(name = "username", unique = true)
    private String username;

    private String password;
}