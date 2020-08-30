package io.github.th3c0d3r.tollbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReverseGeoCodeDto {

    private Integer place_id;
    private AddressDto address;
}
