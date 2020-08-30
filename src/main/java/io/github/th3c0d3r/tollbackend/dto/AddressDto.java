package io.github.th3c0d3r.tollbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {

    private String road;
    private String village;
    private String county;
    private String state_district;
    private String state;
    private String country;
    private String country_code;
}
