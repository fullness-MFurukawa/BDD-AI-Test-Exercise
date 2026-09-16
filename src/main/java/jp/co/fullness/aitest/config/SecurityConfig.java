package jp.co.fullness.aitest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // H2コンソールと静的リソースだけは許可（演習用途）
                .requestMatchers("/h2-console/**", "/css/**", "/js/**").permitAll()
                // それ以外（/ = メニュー含む）はログイン必須
                .anyRequest().authenticated()
            )
            // フォームログイン（/login を自動提供）
            .formLogin(form -> form
                .loginPage("/login") // 使う（下でloginページを用意 or デフォルトでもOK）
                .permitAll()
            )
            .logout(logout -> logout.permitAll());

        // H2コンソール用（frame + csrf）
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));
        http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }
}