package cotato.networking.weather_api.location.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cotato.networking.weather_api.common.response.ApiResponse;
import cotato.networking.weather_api.location.dto.request.LocationRequest;
import cotato.networking.weather_api.location.dto.response.LocationResponse;
import cotato.networking.weather_api.location.service.LocationService;
import cotato.networking.weather_api.user.User;
import cotato.networking.weather_api.user.annotation.CurrentUser;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/locations")
public class LocationController {

	private final LocationService locationService;

	@PostMapping
	public ResponseEntity<ApiResponse<LocationResponse>> addLocation(
		@RequestBody @Valid LocationRequest request,
		@CurrentUser User user) {
		return ResponseEntity.ok(
			ApiResponse.created(LocationResponse.from(locationService.addLocation(request, user)))
		);
	}
}