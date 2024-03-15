package com.example.myapplication.di

import com.example.myapplication.data.MovieApi
import com.example.myapplication.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module //Esta anotación marca la clase como un módulo Dagger. Un módulo proporciona las dependencias que Dagger puede inyectar en las clases solicitantes
@InstallIn(SingletonComponent::class) // especifica en qué componente de Dagger debe instalarse el módulo. En este caso, el módulo se instala en
//SingletonComponent, lo que significa que las dependencias proporcionadas por este módulo serán únicas y vivirán durante toda la vida de la aplicación.
object AppModule {

    @Singleton
    @Provides
    /*
    @Provides: Esta anotación se usa para marcar los métodos que proporcionan las dependencias.
    Estos métodos deben ser métodos de instancia (no estáticos) dentro del objeto AppModule.
     */
    fun providesRetrofit(): Retrofit { //Este método proporciona una instancia de Retrofit, que es una biblioteca de cliente HTTP para Android y Java que simplifica la comunicación con servicios web RESTful.
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Este método proporciona una instancia de la interfaz MovieApi, que se utiliza para realizar
     * llamadas a la API de anime utilizando Retrofit.
     */
    @Singleton
    @Provides
    fun providesMovieApi(retrofit: Retrofit): MovieApi {
        return  retrofit.create(MovieApi::class.java)
    }

}