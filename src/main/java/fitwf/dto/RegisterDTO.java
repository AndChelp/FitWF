package fitwf.dto;

import fitwf.validation.annotation.ValidPassword;
import fitwf.validation.annotation.ValidUsername;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Getter
@Setter
public class RegisterDTO {
    @ValidUsername
    private String username;

    @Email
    @Length(max = 300)
    private String email;

    @ValidPassword
    private String password;

    @Override
    public String toString() {
        return "Username = " + username + " email = " + email;
    }
}