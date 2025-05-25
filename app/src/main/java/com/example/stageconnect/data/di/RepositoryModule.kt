package com.example.stageconnect.data.di

import com.example.stageconnect.data.remote.repository.ApplicationRepository
import com.example.stageconnect.data.remote.repository.ApplicationRepositoryImpl
import com.example.stageconnect.data.remote.repository.AuthenticationRepository
import com.example.stageconnect.data.remote.repository.AuthenticationRepositoryImpl
import com.example.stageconnect.data.remote.repository.CertificationRepository
import com.example.stageconnect.data.remote.repository.CertificationRepositoryImpl
import com.example.stageconnect.data.remote.repository.EducationRepository
import com.example.stageconnect.data.remote.repository.EducationRepositoryImpl
import com.example.stageconnect.data.remote.repository.EstablishmentRepository
import com.example.stageconnect.data.remote.repository.EstablishmentRepositoryImpl
import com.example.stageconnect.data.remote.repository.FileRepository
import com.example.stageconnect.data.remote.repository.FileRepositoryImpl
import com.example.stageconnect.data.remote.repository.InternshipRepository
import com.example.stageconnect.data.remote.repository.InternshipRepositoryImpl
import com.example.stageconnect.data.remote.repository.LanguageRepository
import com.example.stageconnect.data.remote.repository.LanguageRepositoryImpl
import com.example.stageconnect.data.remote.repository.OfferRepository
import com.example.stageconnect.data.remote.repository.OfferRepositoryImpl
import com.example.stageconnect.data.remote.repository.ProjectRepository
import com.example.stageconnect.data.remote.repository.ProjectRepositoryImpl
import com.example.stageconnect.data.remote.repository.RegisterRepository
import com.example.stageconnect.data.remote.repository.RegisterRepositoryImpl
import com.example.stageconnect.data.remote.repository.RoomRepository
import com.example.stageconnect.data.remote.repository.RoomRepositoryImpl
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.data.remote.repository.StudentRepository
import com.example.stageconnect.data.remote.repository.StudentRepositoryImpl
import com.example.stageconnect.data.remote.repository.WorkExperienceRepository
import com.example.stageconnect.data.remote.repository.WorkExperienceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideUserRepository(
        userRepositoryImpl: RegisterRepositoryImpl
    ): RegisterRepository {
        return userRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideAuthenticationRepository(
        authenticationRepositoryImpl: AuthenticationRepositoryImpl
    ): AuthenticationRepository {
        return authenticationRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideEstablishmentRepository(
        establishmentRepositoryImpl: EstablishmentRepositoryImpl
    ): EstablishmentRepository {
        return establishmentRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideApplicationRepository(
        applicationRepositoryImpl: ApplicationRepositoryImpl
    ): ApplicationRepository {
        return applicationRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideCertificationRepository(
        certificationRepositoryImpl: CertificationRepositoryImpl
    ): CertificationRepository {
        return certificationRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideEducationRepository(
        educationRepositoryImpl: EducationRepositoryImpl
    ): EducationRepository {
        return educationRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideInternshipRepository(
        internshipRepositoryImpl: InternshipRepositoryImpl
    ): InternshipRepository {
        return internshipRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideLanguageRepository(
        languageRepositoryImpl: LanguageRepositoryImpl
    ): LanguageRepository {
        return languageRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideOfferRepository(
        offerRepositoryImpl: OfferRepositoryImpl
    ): OfferRepository {
        return offerRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideProjectRepository(
        projectRepositoryImpl: ProjectRepositoryImpl
    ): ProjectRepository {
        return projectRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideWorkExperienceRepository(
        workExperienceRepositoryImpl: WorkExperienceRepositoryImpl
    ): WorkExperienceRepository {
        return workExperienceRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideStudentRepository(
        studentRepositoryImpl: StudentRepositoryImpl
    ): StudentRepository {
        return studentRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideRoomRepository(
        roomRepositoryImpl: RoomRepositoryImpl
    ): RoomRepository {
        return roomRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideFileRepository(
        fileRepositoryImpl: FileRepositoryImpl
    ): FileRepository {
        return fileRepositoryImpl
    }

}