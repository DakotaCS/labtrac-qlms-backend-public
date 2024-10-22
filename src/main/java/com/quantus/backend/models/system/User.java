package com.quantus.backend.models.system;

import com.quantus.backend.models.GeneralModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-05-16
 */

@Getter
@Setter
@Entity
@Table(name = "sys_user")
public class User extends GeneralModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    protected Integer id;

    @Column(name = "user_name")
    protected String userName;
    @Column(name = "user_password")
    protected String userPassword;
    @Column(name = "is_disabled")
    protected Boolean isDisabled;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserRole> userRoles = new ArrayList<>();
}
