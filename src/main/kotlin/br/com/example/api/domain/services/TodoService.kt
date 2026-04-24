package br.com.example.api.domain.services

import br.com.example.api.domain.entities.Todo

interface TodoService {
    fun all(): List<Todo>
    fun save(entity: Todo): Todo
}