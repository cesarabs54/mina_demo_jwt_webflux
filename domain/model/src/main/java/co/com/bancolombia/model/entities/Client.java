package co.com.bancolombia.model.entities;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private UUID clientId;
    private String clientSecret;
    private String scopes;
    private boolean active;
}
