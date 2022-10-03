package web.model;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;

    public Role(String role) {
        this.role = role;
    }

    public Role() {
    }

    @Override
    public String getAuthority() {
        return role;
    }

    @Transient
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER, cascade = {CascadeType.DETACH})
    private Set<User> users;

    @Override
    public String toString() {
        return role;
    }


}
