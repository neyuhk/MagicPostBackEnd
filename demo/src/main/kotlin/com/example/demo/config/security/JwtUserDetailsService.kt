package com.example.demo.config.security

import com.example.demo.model.User
import com.example.demo.repo.UserRepo
import com.example.demo.config.security.CustomUserDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.concurrent.ExecutionException

@Service
class JwtUserDetailsService(private val userRepo: UserRepo): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return try {
            val user: User = userRepo.findUserByEmail(username)
            User.currentUser.id = user.id
            CustomUserDetails(user)
        } catch (e: ExecutionException) {
            throw RuntimeException(e)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }
}