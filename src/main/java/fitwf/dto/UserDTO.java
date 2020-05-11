package fitwf.dto;

import fitwf.entity.Role;
import fitwf.entity.User;
import fitwf.security.RoleName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDTO {
    private int id;
    private String username;
    private String email;
    private boolean enable;
    private List<RoleName> roles;
    private int countOfWatchFaces;
    private int countOfLikedWatchFaces;
    private int countOfFavoriteWatchFaces;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.enable = user.isEnabled();
        this.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        this.countOfWatchFaces = user.getWatchFace().size();
        this.countOfLikedWatchFaces = user.getLikedWatchFaces().size();
        this.countOfFavoriteWatchFaces = user.getFavoritedWatchFaces().size();
    }
}





















