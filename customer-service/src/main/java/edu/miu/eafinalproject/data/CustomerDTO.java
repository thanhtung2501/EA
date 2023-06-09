package edu.miu.eafinalproject.data;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class CustomerDTO {
    private Long id;

    private String firstName;
    private String lastName;
    private String emailAddress;

    private AddressDTO billingAddress;

}
