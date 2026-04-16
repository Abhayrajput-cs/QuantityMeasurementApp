@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {})

            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("text/plain");
                    response.getWriter().write("Unauthorized access");
                })
            )

            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/v1/users/register",
                    "/api/v1/users/login",
                    "/oauth2/**",
                    "/login/**",
                    "/login/oauth2/**",
                    "/oauth2/authorization/google"
                ).permitAll()
                .anyRequest().authenticated()
            )

            .oauth2Login(oauth -> oauth
                .defaultSuccessUrl("/api/v1/users/google/success", true)
            )

            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
