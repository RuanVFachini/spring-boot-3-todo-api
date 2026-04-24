package br.com.example.api.application.web

import br.com.example.api.application.requests.TodoRequest
import br.com.example.api.application.responses.TodoResponse
import br.com.example.api.domain.entities.Todo
import br.com.example.api.domain.services.TodoService
import org.springframework.scheduling.annotation.Async
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
        createdAt = this.createdAt,
        null
    )

    private fun TodoRequest.toEntity(): Todo = Todo(null, description, false, Instant.now())

    @Async
    @GetMapping
    fun all(): CompletableFuture<List<TodoResponse>> {
        return CompletableFuture.supplyAsync {
            service.all().map { it.mapToResponse()
        }}
    }

    @Async
    @PostMapping
    fun create(@RequestBody request: TodoRequest): CompletableFuture<TodoResponse> {
        return CompletableFuture.supplyAsync {
            val entity = request.toEntity()
            service.save(entity).mapToResponse()
        }
    }

    @Async
    @PostMapping("/{id}/complete")
    fun complete(@PathVariable id: Int): CompletableFuture<TodoResponse> {
        return CompletableFuture.supplyAsync {
            service.complete(id).mapToResponse()
        }
    }
}