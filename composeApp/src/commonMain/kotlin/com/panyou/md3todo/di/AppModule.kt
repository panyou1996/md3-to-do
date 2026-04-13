package com.panyou.md3todo.di

import com.panyou.md3todo.data.repository.TaskRepository
import com.panyou.md3todo.network.LlmClient
import com.panyou.md3todo.network.createHttpClient
import com.panyou.md3todo.ui.viewmodel.TaskDetailViewModel
import com.panyou.md3todo.ui.viewmodel.TaskViewModel
import org.koin.dsl.module

val appModule = module {
    single { createHttpClient() }
    single { LlmClient(get()) }
    single { TaskRepository(get()) }
    
    factory { TaskViewModel(get()) }
    factory { (taskId: String) -> TaskDetailViewModel(get(), get(), taskId) }
}