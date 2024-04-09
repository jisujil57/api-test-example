package modeles;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequestBody {
    private String email;
    private String password;
}
