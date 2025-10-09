package com.eyesgood.mylibrary.dependancyinjection


import com.eyesgood.mylibrary.data.repository.TaskNetworkDataSource
import com.eyesgood.mylibrary.data.repository.TaskNetworkDataSourceImpl
import com.eyesgood.mylibrary.data.repository.TaskRepository
import com.eyesgood.mylibrary.data.repository.TaskRepositoryImpl
import com.eyesgood.mylibrary.data.repository.TextRemoteDataSource
import com.eyesgood.mylibrary.data.repository.TextRemoteDataSourceImpl
import com.eyesgood.mylibrary.data.repository.TextRepository
import com.eyesgood.mylibrary.data.repository.TextRepositoryImpl
import com.google.firebase.Firebase
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.PropertyConversionMethod
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {

    @Provides
    @Singleton
    fun provideSupabaseInstance(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = "https://mgstcpzcjdoxpcbsgfgv.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im1nc3RjcHpjamRveHBjYnNnZmd2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDcxMDM2OTksImV4cCI6MjA2MjY3OTY5OX0.oxVGazaUbOxqHpMbrlf42x0tQGXsMdZMwIoQtY2CCsY") {
            install(Auth) {
                autoLoadFromStorage = false
            }
            install(Postgrest) {
                defaultSchema = "public"
                propertyConversionMethod = PropertyConversionMethod.SERIAL_NAME
            }
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
object GenerativeAIModule{

    @Provides
    @Singleton
    fun provideGenerativeAiInstance(): GenerativeModel{
        return Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel("gemini-2.5-flash")
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class I0Dispatcher

@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {

    @I0Dispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO


}

@Module
@InstallIn(SingletonComponent::class)
abstract class  TaskDataSourceModule {

    @Binds
    abstract fun bindTaskDataSource(taskNetworkDataSourceImpl: TaskNetworkDataSourceImpl): TaskNetworkDataSource
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindTaskRepository(
        impl: TaskRepositoryImpl,
    ): TaskRepository
}


@Module
@InstallIn(SingletonComponent::class)
abstract class TextRemoteModule{

    @Binds
    abstract fun bindTextRemoteDataSource(
        impl: TextRemoteDataSourceImpl
    ): TextRemoteDataSource

}

@Module
@InstallIn(SingletonComponent::class)
abstract class TextRepositoryModule {

    @Binds
    abstract fun  bindTextRepository(impl: TextRepositoryImpl): TextRepository
}
