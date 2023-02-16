package vaspiakou.citylistapplication.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityDto {
    Long id;
    String name;
    String photo;
}
