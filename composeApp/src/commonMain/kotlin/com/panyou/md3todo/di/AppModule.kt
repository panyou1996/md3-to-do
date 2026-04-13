package com.panyou.md3todo.di

import com.panyou.md3todo.data.repository.InMemoryTaskRepository
import com.panyou.md3todo.domain.repository.TaskRepository
import com.panyou.md3todo.ui.TaskViewModel
import com.panyou.md3todo.network.LlmClient
import com.panyou.md3todo.network.createHttpClient
import org.koin.dsl.module

val appModule = module {
    single { createHttpClient() }
    single { LlmClient(get()) }
    single<TaskRepository> { InMemoryTaskRepository() }
    single { TaskViewModel(get(), get()) }
}