package br.com.example.api.application.responses

import java.time.Instant

class TodoResponse(
    var id: Long? = null,
    var description: String,
    var completed: Boolean,
    var createdAt: Instant,
    var completedAt: Instant?,
)