package co.com.bancolombia.r2dbc.entities;


import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserData implements Persistable<UUID> {

    @Id
    private UUID userId;

    private String username;

    private String name;

    private String email;

    private String password;

    @Transient
    @SuppressWarnings("all")
    private transient boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }

    @Override
    public UUID getId() {
        return userId;
    }

    public void setAsNotNew() {
        this.isNew = false;
    }
}

