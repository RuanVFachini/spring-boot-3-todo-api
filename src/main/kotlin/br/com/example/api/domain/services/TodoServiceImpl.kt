package br.com.example.api.domain.services

import br.com.example.api.domain.entities.Todo
import br.com.example.api.domain.repositories.TodoRepository
import org.springframework.stereotype.Service

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
}