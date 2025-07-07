package com.blogset.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String requestPath = request.getRequestURI();

	    // ✅ Skip JWT validation for public endpoints
	    if (requestPath.contains("/api/v1/auth/login") || requestPath.contains("/api/v1/auth/register") || 
	        requestPath.contains("/swagger-ui") || requestPath.contains("/v3/api-docs")) {
	        filterChain.doFilter(request, response);
	        return;
	    }
		
//		1. get token
		
		String requestToken=request.getHeader("Authorization");
		
//		bearer 4215653bvdbhf
		
		System.out.println(requestToken);
		
		String username=null;
		
		String token = null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer")) {
			
			token = requestToken.substring(7);
			try 
			{
			username = this.jwtTokenHelper.getUsernameFromToken(token);
			}catch(IllegalArgumentException e) {
				System.out.println("unable to get jwt token");
			}
			catch(ExpiredJwtException e) {
				System.out.println("jwt token has expired");
			}
			catch(MalformedJwtException e) {
				System.out.println("invalid jwt exception");
			}
		}
		else {
			System.out.println("Jwt Token does not begin with Bearer");
		}
//		once we get the token, now validate
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			if(this.jwtTokenHelper.validateToken(token, userDetails)) {
//				everythings alright
//				authentication karna h
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				System.out.println("Authorities from JWT: " + userDetails.getAuthorities());

			}
			else {
				System.out.println("Invalid jwt token ");
			}
			
		}
		else {
			System.out.println("username is null or context is not null");
		}
		
		filterChain.doFilter(request, response);
	}

}
