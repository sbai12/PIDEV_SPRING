package edu.esprit.payment.dto.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;

import java.util.Arrays;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("Error")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDTO {

    private Integer errorCode;
    private String errorMessage;

    @Builder.Default
    private ErrorLevelEnum errorLevel = ErrorLevelEnum.INFO;

    private ErrorTypeEnum errorType;
    private String documentationUrl;
    private String tips;

    public enum ErrorLevelEnum {
        INFO("info"),
        WARNING("warning"),
        ERROR("error"),
        CRITICAL("critical");

        private final String value;

        ErrorLevelEnum(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @JsonCreator
        public static ErrorLevelEnum fromValue(String value) {
            return Arrays.stream(values())
                    .filter(level -> level.value.equalsIgnoreCase(value))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unexpected value: " + value));
        }
    }

    public enum ErrorTypeEnum {
        FUNCTIONAL("functional"),
        TECHNICAL("technical");

        private final String value;

        ErrorTypeEnum(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @JsonCreator
        public static ErrorTypeEnum fromValue(String value) {
            return Arrays.stream(values())
                    .filter(type -> type.value.equalsIgnoreCase(value))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unexpected value: " + value));
        }
    }
}
