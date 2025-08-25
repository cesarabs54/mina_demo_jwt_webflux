package co.com.bancolombia.r2dbc.entities;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Table("roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleData implements Persistable<UUID> {

    @Id
    private UUID roleId;

    @Enumerated(EnumType.STRING)
    private ERoleData name;

    @Transient
    @SuppressWarnings("all")
    private transient boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }

    @Override
    public UUID getId() {
        return roleId;
    }

    public void setAsNotNew() {
        this.isNew = false;
    }
}
