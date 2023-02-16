package vaspiakou.citylistapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AccessTokenDto {
    private String accessToken;
}
