package com.example.stageconnect.domain.model

import com.example.stageconnect.R

data class User(
    var name:String,
    var firstName:String,
    var middleName:String,
    var dateOfBirth:String,
    var gender:String,
    var photo: Int = R.drawable.img_user,
    var currentPosition:String,
    var address:String,
    var phone:String,
    var email:String,
    var summary:String,
    var workExperiences: List<WorkExperience>,
    var educations: List<Education>,
    var projects: List<Project>,
    var certifications: List<Certification>,
    var internships: List<Internship>,
    var languages: List<Language>,
    var skills : List<Skill>,
    var resumesPath: List<String>
)
