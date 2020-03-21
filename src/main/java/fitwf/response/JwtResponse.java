package fitwf.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private final static String TYPE = "Bearer_";
    private String token;

    public JwtResponse(String token) {
        this.token = TYPE + token;
    }
}
