@Getter
@Setter
public class RegisterDTO{
    @ValidUsername
    private String username;
    
    @Max(300)
    @Email
    private String email;
    
    @ValidPassword
    private String password;
}