package cotato.networking.weather_api.common.property.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cotato.networking.weather_api.common.property.property.SwaggerProperties;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// 전역적으로 사용되는 상수
@Configuration
@EnableConfigurationProperties(value = {
	SwaggerProperties.class
})
@EnableWebSecurity
public class PropertyConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())      // 필요 시 변경
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
						.requestMatchers("/test/**").hasRole("USER")
						.anyRequest().authenticated()
				)
				.formLogin(form -> form.disable())   // session + 기본 formLogin (필요 없으면 disable)
				.sessionManagement(sess -> sess.maximumSessions(1))
				.logout(logout -> logout.logoutUrl("/auth/logout")); // 편의상 REST 경로 맞추기

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
