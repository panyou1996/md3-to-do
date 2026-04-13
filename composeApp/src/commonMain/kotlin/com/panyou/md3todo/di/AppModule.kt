package com.panyou.md3todo.di

import com.panyou.md3todo.data.repository.TaskRepository
import com.panyou.md3todo.network.LlmClient
import com.panyou.md3todo.network.createHttpClient
import org.koin.dsl.module

val appModule = module {
    single { createHttpClient() }
    single { LlmClient(get()) }
    single { TaskRepository(get()) }
    
    // ViewModel logic will be injected here in Round 2
}