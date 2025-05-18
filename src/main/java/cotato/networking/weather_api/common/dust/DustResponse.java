package cotato.networking.weather_api.common.dust;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(toBuilder = true)
public class DustResponse {
    private final List<DustDto> dustDtoList;
}
