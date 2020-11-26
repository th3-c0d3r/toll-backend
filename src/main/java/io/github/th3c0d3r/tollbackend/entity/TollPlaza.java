package io.github.th3c0d3r.tollbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class TollPlaza extends BaseEntity {

    private Integer tollPlazaId;
    private String tollName;
    private Double latitude;
    private Double longitude;
    private String costTable;
    private String state;
    private String district;
    private String tollPlazaImageUrl;

    private Double LMVSingle;
    private Double LMVReturn;
    private Double LMVMonthly;
    private Double LMVCommercial;
    private Double LCVSingle;
    private Double LCVReturn;
    private Double LCVMonthly;
    private Double LCVCommercial;
    private Double BusTruckSingle;
    private Double BusTruckReturn;
    private Double BusTruckMonthly;
    private Double BusTruckCommercial;
    private Double Axle3Single;
    private Double Axle3Return;
    private Double Axle3Monthly;
    private Double Axle3Commercial;
    private Double Axle4To6Single;
    private Double Axle4To6Return;
    private Double Axle4To6Monthly;
    private Double Axle4To6Commercial;
    private Double HCMSingle;
    private Double HCMReturn;
    private Double HCMMonthly;
    private Double HCMCommercial;
    private Double Axle7PlusSingle;
    private Double Axle7PlusReturn;
    private Double Axle7PlusMonthly;
    private Double Axle7PlusCommercial;

}
