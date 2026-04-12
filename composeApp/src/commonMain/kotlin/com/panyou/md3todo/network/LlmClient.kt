package com.panyou.md3todo.network

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class LlmRequest(val model: String, val messages: List<Message>)

@Serializable
data class Message(val role: String, val content: String)

class LlmClient(private val client: HttpClient) {
    suspend fun getTaskSuggestions(taskTitle: String, apiKey: String): String {
        // This simulates a call to an OpenAI compatible endpoint like RikkaHub does.
        val requestBody = LlmRequest(
            model = "gpt-3.5-turbo",
            messages = listOf(
                Message("system", "You are an AI assistant that breaks down a task into 3 bullet points."),
                Message("user", "Break down this task: $taskTitle")
            )
        )
        
        return try {
            val response = client.post("https://api.openai.com/v1/chat/completions") {
                header("Authorization", "Bearer $apiKey")
                header("Content-Type", "application/json")
                setBody(Json.encodeToString(requestBody))
            }
            response.bodyAsText()
        } catch (e: Exception) {
            "Error calling LLM: ${e.message}"
        }
    }
}