package ec.edu.monster.phoneshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    private String username;

    private String password;

    private String serverIp;

    @Builder.Default
    private ApiCommunicationType apiCommunicationType = ApiCommunicationType.REST;
}
