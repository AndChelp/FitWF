package fitwf.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
//TODO: return validate back to the User
@Getter
@Setter
public class RegisterDTO{
    //@ValidUsername
    private String username;

    @Email
    private String email;
    
    //@ValidPassword
    private String password;
}