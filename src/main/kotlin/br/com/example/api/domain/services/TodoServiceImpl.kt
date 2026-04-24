package br.com.example.api.domain.services

import br.com.example.api.domain.entities.Todo
import br.com.example.api.domain.repositories.TodoRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Instant
import kotlin.jvm.optionals.getOrElse

@Service
class TodoServiceImpl(
    private val repository: TodoRepository
) : TodoService {
    override fun all(): List<Todo> {
        return repository.findAll()
    }

    override fun save(entity: Todo): Todo {
        return repository.save(entity)
    }

    override fun complete(id: Int): Todo {
        val entity = repository.findById(id).getOrElse {
            throw EntityNotFoundException("Entity not found with id: $id")
        }

        entity.completed = true
        entity.completedAt = Instant.now()

        return repository.save(entity)
    }
}