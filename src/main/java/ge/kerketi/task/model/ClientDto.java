package ge.kerketi.task.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ClientDto {

    @NotBlank(message = "firstName must not be blank!")
    private String firstName;

    @NotBlank(message = "lastName must not be blank!")
    private String lastName;

    @NotBlank(message = "personalNumber must not be blank!")
    private String personalNumber;
}
