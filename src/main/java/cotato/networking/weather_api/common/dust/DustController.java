package cotato.networking.weather_api.common.dust;

import cotato.networking.weather_api.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dust")
@RequiredArgsConstructor
public class DustController {

	private final DustService dustService;

	@GetMapping()
	public ApiResponse<DustDto> getDustInfo(@RequestParam String city, @RequestParam String stationName) {

		return ApiResponse.ok(dustService.getDustInfo(city, stationName));
	}
}
