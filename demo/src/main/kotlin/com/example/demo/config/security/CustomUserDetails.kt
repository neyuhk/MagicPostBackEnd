package com.example.demo.config.security

import com.example.demo.model.User
import com.example.demo.model.UserResp
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.Collections


class CustomUserDetails(private val user: User): UserDetails  {

    fun getUser(): UserResp {
        return UserResp(user)
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return null
    }


    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String {
        return user.email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}