package cotato.networking.weather_api.auth;

import cotato.networking.weather_api.common.error.ErrorCode;
import cotato.networking.weather_api.common.error.exception.AppException;
import cotato.networking.weather_api.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public Long signUp(SignUpRequest request) {
        if(authRepository.existsByLoginId(request.loginId())) {
            throw new AppException(ErrorCode.USER_INPUT_EXCEPTION);
        }

        User user = User.createUser(request, passwordEncoder);

        return authRepository.save(user).getId();
    }

    public LoginResponse login(LoginRequest request, HttpSession session) {
        User user = authRepository.findByLoginId(request.loginId())
                .orElseThrow(() -> new AppException(ErrorCode.AUTHENTICATION_EXCEPTION));

        if(!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new AppException(ErrorCode.AUTHENTICATION_EXCEPTION);
        }

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        user.getLoginId(), null, authorities
                );

        SecurityContextHolder.getContext().setAuthentication(authToken);

        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());   // 확실히 저장

        return new LoginResponse(user.getId());
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }
}
