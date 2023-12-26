package com.example.demo.service

import com.example.demo.config.security.JwtProvider
import com.example.demo.model.*
import com.example.demo.repo.UserRepoImpl
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@Service
class UserServiceImpl(
        val userRepo: UserRepoImpl,
        val authenticationManager: AuthenticationManager,
        val jwtProvider: JwtProvider,
        val passwordEncoder: PasswordEncoder,
) : UserService {
    override fun createManager(userReq: UserReq, serviceAddressId : String): Mono<UserResp> {
        userReq.password = passwordEncoder.encode(userReq.password)
        val user = userRepo.addManager(userReq, serviceAddressId)
        return Mono.just(UserResp(user))
    }

    override fun createEmployee(userReq: UserReq, serviceAddressId : String): Mono<UserResp> {
        userReq.password = passwordEncoder.encode(userReq.password)
        val user = userRepo.addEmployee(userReq, serviceAddressId)
        return Mono.just(UserResp(user))
    }

    override fun authenticate(loginReq: LoginReq): Mono<LoginResp> {
        try {
            authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(
                            loginReq.email,
                            loginReq.password
                    )
            )
            val user = userRepo.findUserByEmail(loginReq.email)
            val jwtToken = jwtProvider.generateToken(user)
            val refreshToken = jwtProvider.generateRefreshToken(user)
            return Mono.just(LoginResp(UserResp(user), jwtToken, refreshToken))
        } catch (e: BadCredentialsException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong username or password")
        }
    }

    override fun updateUser(userReq: UserReq, userId: String): UserResp {
        userReq.password = passwordEncoder.encode(userReq.password)
        val user = userRepo.findUserById(userId)
        val updatedUser = user.update(userReq)
        return UserResp(userRepo.updateUser(updatedUser))
    }

    override fun getCurrentUser(): Mono<UserResp> {
        val id = User.currentUser.id
        return getUserById(id)
    }

    override fun getUserById(id: String): Mono<UserResp> {
        val user = userRepo.findUserById(id)
        val userResp = UserResp(user)
        return Mono.just(userResp)
    }

    override fun searchUser(): Mono<List<UserResp>> {
        TODO("Not yet implemented")
    }

}