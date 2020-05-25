package fitwf.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import fitwf.dto.UserDTO;
import fitwf.dto.WatchFaceDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
