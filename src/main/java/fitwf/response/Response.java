package fitwf.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import fitwf.dto.UserDTO;
import fitwf.dto.WatchFaceDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_DEFAULT) //Исключение всех Default значений из сериализации
public class Response {
    @Builder.Default
    @JsonInclude
    private int statusCode = ErrorCode.OK; //Единственное доступное Default значение
    private String statusMsg;
    private int itemCount;
    private List<UserDTO> userList;
    private UserDTO user;
    private List<WatchFaceDTO> watchFaceList;
    private WatchFaceDTO watchFace;
    private String jwtToken;
}
