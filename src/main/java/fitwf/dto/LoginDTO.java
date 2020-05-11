package fitwf.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String username;
    private String password;

    @Override
    public String toString() {
        return "Username = " + username;
    }
}
