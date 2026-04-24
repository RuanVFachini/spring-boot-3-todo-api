@file:Suppress("SpringCompilerPlugin")

package br.com.example.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
class ApiApplication

fun main(args: Array<String>) {
	runApplication<ApiApplication>(*args)
}
