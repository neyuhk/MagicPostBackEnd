package com.example.demo.config.security

import com.example.demo.config.security.JwtProvider
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils.hasText
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.HandlerExceptionResolver
import java.time.Instant
import kotlin.jvm.Throws


@Component
class JwtAuthFilter(
        val tokenProvider: JwtProvider,
        val userDetailsService: JwtUserDetailsService,
        @Qualifier("handlerExceptionResolver") val resolver: HandlerExceptionResolver,
): OncePerRequestFilter() {
    @Throws(ResponseStatusException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.servletPath.contains("/api/v1/register") || request.servletPath.contains("/api/v1/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            val jwt = getJwtFromRequest(request)
            if (hasText(jwt) && tokenProvider.validateToken(jwt!!)) {
                setAuthenticateForToken(request, jwt)
            }

        } catch (ex: Exception) {
            val log: Logger = LoggerFactory.getLogger(JwtAuthFilter::class.java)
            log.error("failed on set user authentication", ex)
        }
        filterChain.doFilter(request, response)
    }

    private fun setAuthenticateForToken(request: HttpServletRequest, jwt: String) {
        val username = tokenProvider.getUsernameFromToken(jwt)
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(username)
        val authentication = UsernamePasswordAuthenticationToken(
            userDetails, null,
            userDetails.authorities
        )
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authentication
    }

    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }

}