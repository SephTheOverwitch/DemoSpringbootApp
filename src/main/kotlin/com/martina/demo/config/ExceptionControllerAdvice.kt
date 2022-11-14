package com.martina.demo.config

import com.martina.demo.exception.JsonParsingException
import com.martina.demo.exception.LoopException
import com.martina.demo.utils.ErrorMessage
import kotlinx.serialization.SerializationException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler
    fun handleIllegalStateException(ex: IllegalStateException): ResponseEntity<ErrorMessage> {

        val errorMessage = ErrorMessage(
            BAD_REQUEST.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleGeneralException(ex: Exception): ResponseEntity<ErrorMessage> {

        val errorMessage = ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleSerializerException(ex: SerializationException): ResponseEntity<ErrorMessage> {

        val errorMessage = ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleLoopException(ex: LoopException): ResponseEntity<ErrorMessage> {

        val errorMessage = ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleJsonParseException(ex: JsonParsingException): ResponseEntity<ErrorMessage> {

        val errorMessage = ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }
}
