package com.SCHMA.SchoolManagement.SECURITY.CONFIG;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull    HttpServletResponse response,
            @NonNull   FilterChain filterChain)
            throws ServletException,
            IOException {
        //Retrieves the Authorization header from the incoming HTTP request,
        final String authHeader = request.getHeader("Authorization");
        final String Jwt;
        final String  userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        Jwt = authHeader.substring(7);
        //it has To Be Extracted From The JWT token
        userEmail= jwtService.extractUserName(Jwt);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails =this.userDetailsService.loadUserByUsername(userEmail);

            //Checking Authetication Token
            //It checks if the JWT and the userDetails Match

            if (jwtService.IstokenValid(Jwt,userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                //Setting Authentication Details Such As IP addresses
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)

                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }


        filterChain.doFilter(request,response);
    }
}