package br.com.example.api.domain.repositories

import br.com.example.api.domain.entities.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Repository
import java.util.concurrent.CompletableFuture

@Repository
interface TodoRepository : JpaRepository<Todo, Int> {
}