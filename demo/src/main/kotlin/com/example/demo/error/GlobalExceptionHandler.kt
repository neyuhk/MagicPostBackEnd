package com.example.demo.error

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.google.common.base.CaseFormat
import com.google.common.base.Joiner
import com.google.common.base.Strings
import jakarta.servlet.http.HttpServletRequest
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.reactive.function.client.WebClientException
import org.springframework.web.reactive.function.client.WebClientRequestException
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.server.ResponseStatusException
import java.time.Instant
import java.util.*


/**
 * GlobalExceptionHandler will wrap all exception and provide meaningful messages to clients.
 */

@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler {
    val logger: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    /**
     * Process error contained in ResponseStatusException, caused by System Exceptions that are
     * known.
     *
     * @param ex      the ResponseStatusException
     * @param request original ServerHttpRequest Request
     * @return ErrorResp
     */
    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(request: HttpServletRequest, ex: ResponseStatusException)
        : ResponseEntity<ErrorResp?> {
        logger.error(ex.message)
        val resp: ErrorResp = buildErrorResp(ex, request)

        if (ex.statusCode == HttpStatus.BAD_REQUEST) {
            val c = getFinalCause(ex)
            logger.error(c!!.message)
            when (c) {
                is InvalidFormatException -> {
                    return handleInvalidFormatException(c, request)
                }

                is MismatchedInputException -> {
                    resp.addError(InputError(c.pathReference, "Missing or invalid"))
                }

                else -> {
                    val message = if (!Strings.isNullOrEmpty(ex.reason)) ex.reason else c.message
                    resp.addError(GenericError(message))
                }
            }
        } else {
            resp.addError(GenericError(ex.reason))
        }
//        logErrors(request, resp)
        return ResponseEntity(resp, ex.statusCode)
    }

    /**
     * Process the exception caused by invalid data input format.
     *
     * @param ex      InvalidFormatException
     * @param request original request
     * @return detailed errors message
     */
    @ExceptionHandler(InvalidFormatException::class)
    fun handleInvalidFormatException(
        ex: InvalidFormatException, request: HttpServletRequest
    ): ResponseEntity<ErrorResp?> {
        val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
        logger.error(ex.message)
        val resp: ErrorResp = buildErrorResp(ex, request)
//        logErrors(request, resp)
        return ResponseEntity<ErrorResp?>(resp, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InsufficientAuthenticationException::class)
    fun handleAuthenticationException(
        ex: InsufficientAuthenticationException, request: HttpServletRequest
    ): ResponseEntity<ErrorResp?> {
        val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
        logger.error(ex.message)
        val resp: ErrorResp = buildErrorResp(request, HttpStatus.UNAUTHORIZED)
        resp.addError(GenericError(ex.message))
//        logErrors(request, resp)
        return ResponseEntity<ErrorResp?>(resp, HttpStatus.UNAUTHORIZED)
    }

    /**
     * Handle exception caused by invalid data input format.
     *
     * @param e       WebClientException
     * @param request ServerHttpRequest
     * @return detailed errors message
     */
    @ExceptionHandler(WebClientException::class)
    fun handleWebClientException(
        e: WebClientException, request: HttpServletRequest
    ): ResponseEntity<ErrorResp> {
        val finalEx = getFinalCause(e)
        logger.error(e.message)
        var finalErrorMessage = finalEx!!.message
        if (finalErrorMessage == null) {
            logger.error(finalEx.javaClass.name)
            finalErrorMessage = finalEx.javaClass.simpleName
        }
        logger.error(finalErrorMessage)
        val resp: ErrorResp = buildErrorResp(request, HttpStatus.INTERNAL_SERVER_ERROR)
        if (e is WebClientResponseException) {
            val req: HttpRequest? = e.request
            if (req != null) {
                val message = Joiner.on(" ")
                    .join(
                        req.method.name(),
                        req.uri,
                        "failed. Root cause:",
                        finalErrorMessage
                    )
                logger.error(message)
                resp.addError(GenericError(message))
            }
        } else if (e is WebClientRequestException) {
            val message = Joiner.on(" ")
                .join(

                    e.method,
                    e.uri,
                    "failed. Root cause:",
                    finalErrorMessage

                )
            logger.error(message)
            resp.addError(GenericError(message))
        }
        return ResponseEntity<ErrorResp>(resp, HttpStatus.INTERNAL_SERVER_ERROR)
    }


    /**
     * Process error contained in WebExchangeBindException, caused by wrong input data.
     *
     * @param ex      the WebExchangeBindException
     * @param request original ServerHttpRequest Request
     * @return ErrorResp
     */
    @ExceptionHandler(WebExchangeBindException::class)
    fun handleWebExchangeBindException(
        ex: WebExchangeBindException, request: HttpServletRequest
    ): ResponseEntity<ErrorResp?> {
        val resp: ErrorResp = buildErrorResp(ex, request)
        for (error in ex.bindingResult.fieldErrors) {
            val field = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, error.field)
            resp.addError(InputError(field, error.defaultMessage))
        }
        logger.error(ex.message)
        return ResponseEntity(resp, ex.statusCode)
    }

    /**
     * Process all unexpected errors.
     *
     * @param request original ServerHttpRequest Request
     * @return ErrorResp
     */
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception, request: HttpServletRequest): ResponseEntity<ErrorResp> {
        println("This is exception")
        getFinalCause(ex)
        logger.error(ex.javaClass.name)
        val resp: ErrorResp = buildErrorResp(request, HttpStatus.INTERNAL_SERVER_ERROR)
        resp.addError(
            GenericError(ex.message)
        )

        return ResponseEntity<ErrorResp>(resp, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun buildErrorResp(
        ex: ResponseStatusException,
        request: HttpServletRequest
    ): ErrorResp {
        return ErrorResp(
            timestamp = Instant.now().toEpochMilli(),
            path = request.servletPath,
            status = HttpStatus.valueOf(ex.statusCode.value()),
            requestId = request.requestId,
            traceId = request.requestId
        )
    }

    private fun buildErrorResp(ex: InvalidFormatException, request: HttpServletRequest): ErrorResp {
        val resp = ErrorResp(
            timestamp = Instant.now().toEpochMilli(),
            path = request.servletPath,
            status = HttpStatus.BAD_REQUEST,
            requestId = request.requestId,
            traceId = request.requestId
        )
        val fieldsNames: List<String> = ex.path
            .map { it.fieldName }
            .filter { obj: Any? -> Objects.nonNull(obj) }

        val field = Joiner.on(".").join(fieldsNames)
        val type: Class<*> = ex.targetType
        var message = java.lang.String.format(
            "Cannot convert value: %s to type %s",
            ex.value,
            type.simpleName
        )
        if (type.isEnum) {
            val possibleValues = listOf(*type.enumConstants)
            val nextMessage = possibleValues.toString()
            message = "$message. Possible values are: $nextMessage"
        }
        resp.addError(InputError(field, message))
        return resp
    }

    private fun buildErrorResp(request: HttpServletRequest, status: HttpStatus): ErrorResp {
        return ErrorResp(
            timestamp = Instant.now().toEpochMilli(),
            path = request.servletPath,
            status = status,
            requestId = request.requestId,
            traceId = request.requestId
        )

    }

    protected fun getFinalCause(e: Exception?): Throwable? {
        var x: Throwable? = e
        while (x!!.cause != null) {
            x = x.cause
        }
        return x
    }
}

