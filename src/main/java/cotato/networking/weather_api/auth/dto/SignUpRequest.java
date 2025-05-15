package cotato.networking.weather_api.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotBlank @Size(min = 4, max = 20) String loginId,
        @NotBlank @Size(min = 8) String password) {}

