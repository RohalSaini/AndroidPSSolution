package sanssys.solution.pssolutions.hilt

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sanssys.solution.pssolutions.server.Api
import sanssys.solution.pssolutions.server.Retrofit
import sanssys.solution.pssolutions.util.Session
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun getSessionObj(context: Application) :Session {
        return Session(context)
    }

    @Provides
    @Singleton
    fun getRetrofitObj(context: Application): Api {
       return Retrofit.getInstance().create(Api::class.java)
    }
}