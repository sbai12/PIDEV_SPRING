package edu.esprit.payment.dto.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
public class ErrorsDTO {

    private List<ErrorDTO> errors = new ArrayList<>();

    public ErrorsDTO errors(List< ErrorDTO> errors) {
        this.errors = errors;
        return this;
    }

    public ErrorsDTO addErrorsItem(ErrorDTO errorsItem) {
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(errorsItem);
        return this;
    }


    @Valid   @JsonProperty("errors")
    public List<@Valid ErrorDTO> getErrors() {
        return errors;
    }

    public void setErrors(List<@Valid ErrorDTO> errors) {
        this.errors = errors;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErrorsDTO errors = (ErrorsDTO) o;
        return Objects.equals(this.errors, errors.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errors);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ErrorsDTO {\n");
        sb.append("    errors: ").append(toIndentedString(errors)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
