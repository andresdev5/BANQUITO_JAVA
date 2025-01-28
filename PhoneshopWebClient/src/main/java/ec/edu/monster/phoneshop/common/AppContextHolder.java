package ec.edu.monster.phoneshop.common;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class AppContextHolder {
    private AppContext appContext = new AppContext();
}
