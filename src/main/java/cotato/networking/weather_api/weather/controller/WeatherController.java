package cotato.networking.weather_api.weather.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cotato.networking.weather_api.common.response.ApiResponse;
import cotato.networking.weather_api.weather.dto.response.WeatherResponse;
import cotato.networking.weather_api.weather.service.WeatherService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/weather")
public class WeatherController {

	private final WeatherService weatherService;

	@GetMapping
	public Mono<ResponseEntity<ApiResponse<WeatherResponse>>> getWeather(
		@RequestParam @Min(-90) @Max(90) double lat,
		@RequestParam @Min(-180) @Max(180) double lon) {
		return weatherService.getWeatherData(lat, lon)
			.map(data -> ResponseEntity.ok(ApiResponse.ok(data)));
	}
}