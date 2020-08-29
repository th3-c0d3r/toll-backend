package io.github.th3c0d3r.tollbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TollPlazaDto {

    private Integer id;
    private Integer tollPlazaId;
    private String tollName;
    private Double latitude;
    private Double longitude;
    private String costTable;
}
