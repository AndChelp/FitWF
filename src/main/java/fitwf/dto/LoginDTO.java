package fitwf.dto;

import fitwf.validation.annotation.ValidPassword;
import fitwf.validation.annotation.ValidUsername;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    @ValidUsername
    private String username;

    @ValidPassword
    private String password;

    @Override
    public String toString() {
        return "Username = " + username;
    }
}
