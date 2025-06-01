package com.example.stageconnect.data.remote.api

import com.example.stageconnect.data.dtos.ApplicationDto
import com.example.stageconnect.data.dtos.AuthenticationRequest
import com.example.stageconnect.data.dtos.AuthenticationResponse
import com.example.stageconnect.data.dtos.CertificationDto
import com.example.stageconnect.data.dtos.EducationDto
import com.example.stageconnect.data.dtos.EstablishmentDto
import com.example.stageconnect.data.dtos.EstablishmentsDto
import com.example.stageconnect.data.dtos.InternshipDto
import com.example.stageconnect.data.dtos.LanguageDto
import com.example.stageconnect.data.dtos.MessageDto
import com.example.stageconnect.data.dtos.OfferDto
import com.example.stageconnect.data.dtos.ProjectDto
import com.example.stageconnect.data.dtos.RecruiterDto
import com.example.stageconnect.data.dtos.RoomDto
import com.example.stageconnect.data.dtos.StudentDto
import com.example.stageconnect.data.dtos.WorkExperienceDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Streaming
import java.util.UUID

interface ApiService {

    @GET("api/establishment/all")
    suspend fun getAllEstablishment(): List<EstablishmentsDto>

    @GET("api/offer/{id}")
    suspend fun getOffer(
        @Path("id") id: Long,
    ): OfferDto

    @GET("api/offer/student/{id}")
    suspend fun getAllOffers(@Path("id") id :Long): List<OfferDto>

    @GET("api/offer/recruiter/{id}")
    suspend fun getAllRecruiterOffers(@Path("id") id :Long): List<OfferDto>

    @PUT("api/offer/{id}")
    suspend fun updateOffer(@Path("id") id :Long, @Body offerDto: OfferDto): OfferDto

    @GET("api/offer/student/saved/{id}")
    suspend fun getAllSavedOffers(@Path("id") id :Long): List<OfferDto>

    @GET("api/application/student/{id}")
    suspend fun getStudentApplications(
        @Path("id") id: Long,
    ): List<ApplicationDto>

    @GET("api/application/recruiter/{id}")
    suspend fun getRecruiterApplications(
        @Path("id") id: Long,
    ): List<ApplicationDto>

    @GET("api/establishment/students/{id}")
    suspend fun getEstablishmentStudents(
        @Path("id") id: Long,
    ): List<StudentDto>

    @GET("api/application/establishment/{id}")
    suspend fun getEstablishmentApplications(
        @Path("id") id: Long,
    ): List<ApplicationDto>

    @DELETE("api/application/{applicationId}/{studentId}")
    suspend fun deleteApplication(
        @Path("applicationId") applicationId: Long,
        @Path("studentId") studentId: Long
    ): Response<Unit>

    @PUT("api/application/{id}")
    suspend fun updateApplication(
        @Path("id") id: Long,
        @Body applicationDto: ApplicationDto
    ): ApplicationDto

    @POST("api/offer/save/{offerId}/{studentId}")
    suspend fun saveOffer(@Path("offerId") offerId :Long, @Path("studentId") studentId: Long): Response<Unit>

    @POST("api/offer/un-save/{offerId}/{studentId}")
    suspend fun unSaveOffer(@Path("offerId") offerId :Long, @Path("studentId") studentId: Long): Response<Unit>

    @Multipart
    @POST("api/auth/register")
    suspend fun register(
        @Part("authDto") userDtoJson: RequestBody,
        @Part file: MultipartBody.Part
    ): String

    @POST("api/auth/authentication")
    suspend fun authenticate(
        @Body request: AuthenticationRequest
    ): AuthenticationResponse

    @POST("api/certification")
    suspend fun createCertification(
        @Body request: CertificationDto
    ): CertificationDto

    @POST("api/education")
    suspend fun createEducation(
        @Body request: EducationDto
    ): EducationDto

    @POST("api/internship")
    suspend fun createInternship(
        @Body request: InternshipDto
    ): InternshipDto

    @POST("api/language")
    suspend fun createLanguage(
        @Body request: LanguageDto
    ): LanguageDto

    @POST("api/offer")
    suspend fun createOffer(
        @Body request: OfferDto
    ): OfferDto

    @POST("api/project")
    suspend fun createProject(
        @Body request: ProjectDto
    ): ProjectDto

    @POST("api/work-experiences")
    suspend fun createWorkExperience(
        @Body request: WorkExperienceDto
    ): WorkExperienceDto

    @GET("api/room/{userId}")
    suspend fun getAllRooms(
        @Path("userId") userId: Long
    ): List<RoomDto>

    @GET("api/room/{roomId}/messages")
    suspend fun getMessages(
        @Path("roomId") roomId: UUID
    ): List<MessageDto>

    @Multipart
    @POST("api/application")
    suspend fun createApplication(
        @Part("applicationDto") userDtoJson: RequestBody,
        @Part file: MultipartBody.Part
    ): ApplicationDto

    @Multipart
    @POST("api/student")
    suspend fun updateStudent(
        @Part("studentDto") studentDtoJson: RequestBody,
        @Part file: MultipartBody.Part
    ): StudentDto

    @Multipart
    @POST("api/recruiter")
    suspend fun updateRecruiter(
        @Part("recruiterDto") recruiterDtoJson: RequestBody,
        @Part file: MultipartBody.Part
    ): RecruiterDto

    @Multipart
    @POST("api/establishment")
    suspend fun updateEstablishment(
        @Part("establishmentDto") establishmentDtoJson: RequestBody,
        @Part file: MultipartBody.Part
    ): EstablishmentDto


    @POST("api/student/skills/{studentId}")
    suspend fun addSkills(
        @Path("studentId") id :Long,
        @Body skills: List<String>
    ): List<String>

    @Streaming
    @GET("api/files/{fileName}")
    suspend fun downloadFile(@Path("fileName") fileName: String): Response<ResponseBody>

    @Multipart
    @POST("api/files/upload/{userId}")
    suspend fun uploadFile(
        @Path("userId") userId: Long,
        @Part file: MultipartBody.Part
    ): String

    @Multipart
    @PUT("api/student/photo/{userId}")
    suspend fun uploadPhoto(
        @Path("userId") userId: Long,
        @Part file: MultipartBody.Part
    ): String
}
