package cotato.networking.weather_api.location.dto.response;

public record LocationResponse(
	Long locationId) {
	public static LocationResponse from(Long locationId) {
		return new LocationResponse(locationId);
	}
}