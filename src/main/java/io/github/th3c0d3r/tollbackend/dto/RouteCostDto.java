package io.github.th3c0d3r.tollbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteCostDto {
    List<LatLongDto> routeLatLongs;

    Double singleLMVCost;
    Double singleLCVCost;
    Double singleBusTruckCost;
    Double single3AxleCost;
    Double single4To6AxleCost;
    Double singleHCMCost;
    Double single7PlusAxleCost;
    Double returnLMVCost;
    Double returnLCVCost;
    Double returnBusTruckCost;
    Double return3AxleCost;
    Double return4To6AxleCost;
    Double returnHCMCost;
    Double return7PlusAxleCost;

}
