package com.pragma.powerup.usermicroservice.configuration.security.jwt;


import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter.UserDetailsServiceImpl;
import com.pragma.powerup.usermicroservice.domain.api.IAuthenticationUserInfoServicePort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JwtTokenFilter extends OncePerRequestFilter implements IAuthenticationUserInfoServicePort {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);
    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    private List<String> excludedPrefixes = Arrays.asList("/auth/**", "/swagger-ui/**", "/actuator/**", "/person/", "/user/{dniNumber}", "/user/registerClient/");
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {
        logger.debug("Iniciando JwtAuthenticationFilter");
        String token = getToken(req);
        if (token != null && jwtProvider.validateToken(token)) {
            logger.debug("Dentro del if");
            String nombreUsuario = jwtProvider.getNombreUsuarioFromToken(token);
            String getRole = jwtProvider.extractRoleFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(nombreUsuario);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
                    userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(req, res);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String currentRoute = request.getServletPath();
        for (String prefix : excludedPrefixes) {
            if (pathMatcher.matchStart(prefix, currentRoute)) {
                return true;
            }
        }
        return false;
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    @Override
    public String getIdentifierUserFromToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = getToken(request);
        if (token != null && jwtProvider.validateToken(token)) {
            return jwtProvider.extractRoleFromToken(token);
        }
        return null;
    }

}
