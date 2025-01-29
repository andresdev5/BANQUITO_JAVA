package ec.edu.monster.phoneshop.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SnackBarMessageType {
    SUCCESS("success"),
    ERROR("error"),
    INFO("info"),
    WARNING("warning");

    private final String cssClass;
}
