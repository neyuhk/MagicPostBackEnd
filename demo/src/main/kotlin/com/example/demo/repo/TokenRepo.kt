package com.example.demo.repo

import com.example.demo.model.Token

interface TokenRepo {
    fun findAllValidTokenByUser(userId: String): List<Token>
    fun findByToken(token: String): Token?
    fun addToken(token: Token): Token
    fun updateToken(token: Token): Token
}