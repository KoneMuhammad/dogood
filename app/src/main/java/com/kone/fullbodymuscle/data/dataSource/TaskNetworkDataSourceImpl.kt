package com.kone.fullbodymuscle.data.dataSource

import com.kone.fullbodymuscle.dagger.I0Dispatcher
import com.kone.fullbodymuscle.data.models.ModelTaskMessage
import com.kone.fullbodymuscle.data.models.ModelTaskWeather
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


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

