package com.hamza.deardiary.arch.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.hamza.deardiary.arch.models.Model

interface Repository<M : Model> {

    @WorkerThread
    suspend fun add(item: M): Long

    @WorkerThread
    suspend fun update(item: M)

    @WorkerThread
    suspend fun delete(id: Int)

    @WorkerThread
    fun get(id: Int): LiveData<M>

    @WorkerThread
    fun getAll(): LiveData<List<M>>
}