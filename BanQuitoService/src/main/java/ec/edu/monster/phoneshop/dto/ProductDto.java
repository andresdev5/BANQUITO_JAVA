package ec.edu.monster.phoneshop.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private UUID id;
    private String name;
    private BigDecimal price;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private byte[] imageFile;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String imagePath;
}
