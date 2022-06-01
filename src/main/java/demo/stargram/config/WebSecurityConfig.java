package demo.stargram.config;

import com.google.common.collect.ImmutableList;
import demo.stargram.config.jwt.JwtAuthenticationFilter;
import demo.stargram.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // Cors Setting - 검색해보기
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "TOKEN_ID", "X-Requested-With", "Content-Type", "Content-Length", "Cache-Control"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().configurationSource(corsConfigurationSource());

        http
                .csrf().disable()
                .httpBasic().disable() // rest API 전용 - 기본설정 해제
                .headers().frameOptions().disable()

                // 세션을 사용하지 않음
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // url 설정
                .and()
                .authorizeRequests() // 요청 사용 권한 체크
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/signup").permitAll()
                .antMatchers("/info").authenticated()
                .antMatchers("/user").hasAnyRole("USER", "MANAGER", "ADMIN")
                .antMatchers("/manager").hasAnyRole("MANAGER","ADMIN")
                .antMatchers("/admin").hasRole("ADMIN")
                .anyRequest().permitAll()

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
                // JwtAuthenticationFilter 를 UsernamePasswordAuthenticationFilter 전에 넣는다

    }
}

//
//                .antMatchers(HttpMethod.POST, "/api/article/**").authenticated()
//                        .antMatchers(HttpMethod.POST, "/api/comment/**").authenticated()
//                        .antMatchers(HttpMethod.POST, "/api/account/**").authenticated()
