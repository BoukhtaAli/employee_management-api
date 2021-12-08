package ma.java.tutorials.employees.config;

import lombok.SneakyThrows;
import ma.java.tutorials.employees.exception.BusinessException;
import ma.java.tutorials.employees.service.impl.UserDetailsServiceImpl;
import ma.java.tutorials.employees.utilis.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if( request.getServletPath().equals("/login") || request.getServletPath().equals("/register")){
            logger.info("Login and Register attempts are excluded from this filter");

        } else{

            final String header = request.getHeader("Authorization");
            String jwtToken;
            String username = null;

            if(header != null && header.startsWith("Bearer ")){

                jwtToken = header.substring(7);

                try {
                    username = jwtUtil.getUsernameFromToken(jwtToken);
                } catch (Exception exception){
                    logger.error(exception.getMessage());
                    filterChain.doFilter(request,response);
                }

            } else {
                logger.info("Header does not contain Bearer Token");
                throw new BusinessException("Header does not contain Bearer Token", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if( username != null && SecurityContextHolder.getContext().getAuthentication() == null ) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if(jwtUtil.validateToken(jwtToken, userDetails)){

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }

            }

        }

        filterChain.doFilter(request,response);
    }
}
