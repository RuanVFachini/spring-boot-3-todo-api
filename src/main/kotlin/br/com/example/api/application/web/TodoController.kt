package br.com.example.api.application.web

import br.com.example.api.application.requests.TodoRequest
import br.com.example.api.application.responses.TodoResponse
import br.com.example.api.domain.entities.Todo
import br.com.example.api.domain.services.TodoService
import org.springframework.scheduling.annotation.Async
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("/api/todos")
class TodoController(
    private val service: TodoService
) {

    private fun Todo.mapToResponse(): TodoResponse = TodoResponse(
        id = this.id,
        description = this.description,
        completed = this.completed,
        createdAt = this.createdAt
    )

    private fun TodoRequest.toEntity(): Todo = Todo(0, description, false, Instant.now())

    @Async
    @GetMapping
    fun all(): CompletableFuture<List<TodoResponse>> {
        return CompletableFuture.supplyAsync {
            Thread.sleep(6000)
            service.all().map { it.mapToResponse()
        }}
    }

    @GetMapping("/sync")
    fun allSync(): List<TodoResponse> {
        return service.all().map { it.mapToResponse() }
    }

    @Async
    @PostMapping
    fun create(@RequestBody request: TodoRequest): CompletableFuture<TodoResponse> {
        return CompletableFuture.supplyAsync {
            val entity = request.toEntity()
            service.save(entity).mapToResponse()
        }
    }
}