package ec.edu.monster.phoneshop.config;

import ec.edu.monster.phoneshop.dto.UserDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationContext {
    private UserDto user;
}
