package co.com.bancolombia.r2dbc.entities;


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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("oauth_clients")
public class ClientData implements Persistable<UUID> {

    @Id
    private UUID clientId;
    private String clientSecret;
    private String scopes;
    private boolean active;

    @Transient
    @SuppressWarnings("all")
    private transient boolean isNew = true;

    @Override
    public UUID getId() {
        return getClientId();
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
