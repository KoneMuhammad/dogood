package com.eyesgood.mylibrary.data.repository

import com.eyesgood.mylibrary.data.ModelTaskMessage
import com.eyesgood.mylibrary.dependancyinjection.I0Dispatcher
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class TaskNetworkDataSourceImpl @Inject constructor(
    private val supabase: SupabaseClient,
    @I0Dispatcher private val ioDispatcher: CoroutineDispatcher,
) :
    TaskNetworkDataSource {

    override suspend fun getMessageData(): ModelTaskMessage =
        withContext(ioDispatcher) {
            supabase.postgrest.rpc("get_random_taskmessage")
                .decodeSingle<ModelTaskMessage>()
        }
}