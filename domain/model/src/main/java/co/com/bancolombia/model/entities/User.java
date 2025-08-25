package co.com.bancolombia.model.entities;

import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String userId;
    private String username;
    private String name;
    private String email;
    private String password;
    private Set<Role> roles;

    public List<String> getRolesAsString() {
        return this.roles.stream()
                .map(role -> role.getName().name())
                .toList();
    }
}
