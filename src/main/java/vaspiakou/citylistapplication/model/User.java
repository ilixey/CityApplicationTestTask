package vaspiakou.citylistapplication.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    private int id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private int role_id;
}
