package ge.kerketi.task.model;

import ge.kerketi.task.domain.Client;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class ClientDto {

    @NotBlank(message = "firstName must not be blank!")
    private String firstName;

    @NotBlank(message = "lastName must not be blank!")
    private String lastName;

    @NotBlank(message = "personalNumber must not be blank!")
    private String personalNumber;

    public static Client toEntity(ClientDto clientDto) {

        return Client.builder()
                .fName(clientDto.getFirstName())
                .lName(clientDto.getLastName())
                .pid(clientDto.getPersonalNumber())
                .build();
    }
}
