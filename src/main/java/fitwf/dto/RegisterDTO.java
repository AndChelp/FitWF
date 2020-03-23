package fitwf.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

//TODO: realize validation
@Getter
@Setter
public class RegisterDTO {
    //@ValidUsername
    private String username;

    @Email
    private String email;

    //@ValidPassword
    private String password;
}