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
    private String tollPlazaImageUrl;
}
