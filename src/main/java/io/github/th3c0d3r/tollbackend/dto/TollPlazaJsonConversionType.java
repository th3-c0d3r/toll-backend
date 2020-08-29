package io.github.th3c0d3r.tollbackend.dto;

import io.github.th3c0d3r.tollbackend.entity.TollPlaza;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TollPlazaJsonConversionType {
    private List<TollPlaza> tollPlazaList;
}
