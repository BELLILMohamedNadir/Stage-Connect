package com.example.stageconnect.data.dummy

import com.example.stageconnect.R
import com.example.stageconnect.domain.model.Application
import com.example.stageconnect.domain.model.Offer
import com.example.stageconnect.domain.model.User

object DummyDataProvider {
//
//    val options = listOf("Full Time", "Remote", "Contract")
//
//    val offers = listOf(
//        Offer(
//            position = "UI/UX Designer",
//            location = "San Francisco, CA",
//            salary = "$8,000 - $20,000 / month",
//            postedDate = "Posted 5 days ago, ends in 10 days",
//            options = options,
//            jobDescription = "Design and implement intuitive user interfaces for web and mobile applications.",
//            requirementSkills = listOf("Figma", "Adobe XD", "Wireframing", "User Research"),
//            education = "Bachelor's degree in Design or related field",
//            keySkills = listOf("Creativity", "UX Research", "Prototyping"),
//            ),
//        Offer(
//            position = "Sales & Marketing",
//            company = "Paypal",
//            logo = R.drawable.ic_google,
//            location = "New York, NY",
//            salary = "$7,000 - $18,000 / month",
//            postedDate = "Posted 5 days ago, ends in 10 days",
//            options = options,
//            jobDescription = "Develop strategies to increase sales and strengthen customer relationships.",
//            requirementSkills = listOf("CRM Tools", "SEO", "Google Ads", "Market Analysis"),
//            education = "Bachelor's in Business, Marketing, or related",
//            keySkills = listOf("Negotiation", "Communication", "Digital Marketing"),
//            jobSummary = JobSummary(
//                role = "Marketing Specialist",
//                industryType = "FinTech",
//                functionalArea = "Marketing",
//                employmentType = "Full Time",
//                roleCategory = "Sales & Marketing",
//                vacancy = "3",
//                website = "https://www.paypal.com/jobs",
//                companyDescription = "PayPal is a leading digital payment platform that allows payments and money transfers over the internet."
//            ),
//            companyDescription = "PayPal focuses on simplifying digital transactions globally."
//        ),
//        Offer(
//            position = "Backend Developer",
//            company = "Amazon",
//            logo = R.drawable.ic_google,
//            location = "Seattle, WA",
//            salary = "$9,000 - $22,000 / month",
//            postedDate = "Posted 5 days ago, ends in 10 days",
//            options = options,
//            jobDescription = "Build scalable backend systems and RESTful APIs for e-commerce applications.",
//            requirementSkills = listOf("Java", "Spring Boot", "AWS", "Microservices"),
//            education = "Bachelor's in Computer Science or related field",
//            keySkills = listOf("Problem Solving", "Clean Code", "CI/CD"),
//            jobSummary = JobSummary(
//                role = "Backend Developer",
//                industryType = "E-commerce",
//                functionalArea = "Engineering",
//                employmentType = "Full Time",
//                roleCategory = "Software Development",
//                vacancy = "5",
//                website = "https://www.amazon.jobs",
//                companyDescription = "Amazon is the world’s largest online retailer and cloud service provider."
//            ),
//            companyDescription = "Amazon is dedicated to building customer-centric scalable solutions."
//        ),
//        Offer(
//            position = "DevOps Engineer",
//            company = "Microsoft",
//            logo = R.drawable.ic_google,
//            location = "Redmond, WA",
//            salary = "$10,000 - $25,000 / month",
//            postedDate = "Posted 5 days ago, ends in 10 days",
//            options = options,
//            jobDescription = "Ensure continuous integration and deployment pipelines are smooth and reliable.",
//            requirementSkills = listOf("Azure", "Kubernetes", "Docker", "CI/CD"),
//            education = "Bachelor's in IT or related",
//            keySkills = listOf("Automation", "Monitoring", "Cloud Infrastructure"),
//            jobSummary = JobSummary(
//                role = "DevOps Engineer",
//                industryType = "Software",
//                functionalArea = "Operations",
//                employmentType = "Full Time",
//                roleCategory = "DevOps",
//                vacancy = "4",
//                website = "https://careers.microsoft.com",
//                companyDescription = "Microsoft is a global technology company known for its software and cloud services."
//            ),
//            companyDescription = "Microsoft fosters innovation and embraces DevOps culture."
//        ),
//        Offer(
//            position = "iOS Developer",
//            company = "Apple",
//            logo = R.drawable.ic_google,
//            location = "Cupertino, CA",
//            salary = "$8,500 - $20,000 / month",
//            postedDate = "Posted 5 days ago, ends in 10 days",
//            options = options,
//            jobDescription = "Create stunning iOS apps using Swift and UIKit/SwiftUI.",
//            requirementSkills = listOf("Swift", "Xcode", "iOS SDK", "MVVM"),
//            education = "Bachelor’s in Computer Science or equivalent",
//            keySkills = listOf("UI Development", "Animation", "App Store Guidelines"),
//            jobSummary = JobSummary(
//                role = "iOS Developer",
//                industryType = "Mobile",
//                functionalArea = "Development",
//                employmentType = "Full Time",
//                roleCategory = "Mobile Developer",
//                vacancy = "2",
//                website = "https://jobs.apple.com",
//                companyDescription = "Apple is a leader in consumer electronics and mobile application development."
//            ),
//            companyDescription = "Join Apple to create world-class iOS applications."
//        ),
//        Offer(
//            position = "Product Manager",
//            company = "Netflix",
//            logo = R.drawable.ic_google,
//            location = "Los Gatos, CA",
//            salary = "$10,000 - $30,000 / month",
//            postedDate = "Posted 5 days ago, ends in 10 days",
//            options = options,
//            jobDescription = "Define product strategy and work cross-functionally to deliver engaging experiences.",
//            requirementSkills = listOf("Agile", "Product Roadmap", "Market Research", "User Stories"),
//            education = "MBA or equivalent experience",
//            keySkills = listOf("Leadership", "Strategic Thinking", "Collaboration"),
//            jobSummary = JobSummary(
//                role = "Product Manager",
//                industryType = "Entertainment",
//                functionalArea = "Product",
//                employmentType = "Full Time",
//                roleCategory = "Product",
//                vacancy = "1",
//                website = "https://jobs.netflix.com",
//                companyDescription = "Netflix is a leading streaming service provider known for innovation and storytelling."
//            ),
//            companyDescription = "Drive product excellence at Netflix."
//        ),
//        Offer(
//            position = "Frontend Developer",
//            company = "Facebook",
//            logo = R.drawable.ic_google,
//            location = "Menlo Park, CA",
//            salary = "$7,500 - $20,000 / month",
//            postedDate = "Posted 5 days ago, ends in 10 days",
//            options = options,
//            jobDescription = "Develop responsive and performant UIs using React and modern JavaScript.",
//            requirementSkills = listOf("React", "TypeScript", "HTML/CSS", "GraphQL"),
//            education = "Bachelor’s in CS or related field",
//            keySkills = listOf("Design Systems", "State Management", "Accessibility"),
//            jobSummary = JobSummary(
//                role = "Frontend Developer",
//                industryType = "Social Media",
//                functionalArea = "Web Development",
//                employmentType = "Full Time",
//                roleCategory = "Frontend",
//                vacancy = "3",
//                website = "https://www.facebookcareers.com",
//                companyDescription = "Meta (Facebook) is pushing the boundaries of social platforms and virtual spaces."
//            ),
//            companyDescription = "Shape the future of UI at Meta."
//        ),
//        Offer(
//            position = "Machine Learning Engineer",
//            company = "Tesla",
//            logo = R.drawable.ic_google,
//            location = "Palo Alto, CA",
//            salary = "$9,500 - $23,000 / month",
//            postedDate = "Posted 5 days ago, ends in 10 days",
//            options = options,
//            jobDescription = "Design and train machine learning models for self-driving technology.",
//            requirementSkills = listOf("Python", "TensorFlow", "Computer Vision", "Data Analysis"),
//            education = "Master’s in AI/ML or related",
//            keySkills = listOf("Model Optimization", "Data Engineering", "Research"),
//            jobSummary = JobSummary(
//                role = "ML Engineer",
//                industryType = "Automotive",
//                functionalArea = "AI/ML",
//                employmentType = "Full Time",
//                roleCategory = "AI",
//                vacancy = "2",
//                website = "https://www.tesla.com/careers",
//                companyDescription = "Tesla designs and manufactures electric vehicles and renewable energy solutions."
//            ),
//            companyDescription = "Accelerate the future of AI with Tesla."
//        ),
//        Offer(
//            position = "Data Analyst",
//            company = "Spotify",
//            logo = R.drawable.ic_google,
//            location = "Stockholm, Sweden",
//            salary = "$6,000 - $18,000 / month",
//            postedDate = "Posted 5 days ago, ends in 10 days",
//            options = options,
//            jobDescription = "Analyze user behavior and help drive product decisions with data.",
//            requirementSkills = listOf("SQL", "Python", "Data Visualization", "Analytics"),
//            education = "Bachelor’s in Data Science or Statistics",
//            keySkills = listOf("Storytelling with Data", "ETL", "Insight Generation"),
//            jobSummary = JobSummary(
//                role = "Data Analyst",
//                industryType = "Music Tech",
//                functionalArea = "Analytics",
//                employmentType = "Full Time",
//                roleCategory = "Analysis",
//                vacancy = "2",
//                website = "https://www.spotifyjobs.com",
//                companyDescription = "Spotify is a digital music service that gives access to millions of songs and podcasts."
//            ),
//            companyDescription = "Join Spotify to turn data into sound decisions."
//        ),
//        Offer(
//            position = "Cloud Architect",
//            company = "Adobe",
//            logo = R.drawable.ic_google,
//            location = "San Jose, CA",
//            salary = "$10,000 - $22,000 / month",
//            postedDate = "Posted 5 days ago, ends in 10 days",
//            options = options,
//            jobDescription = "Design cloud-native infrastructure for scalable creative applications.",
//            requirementSkills = listOf("AWS", "GCP", "Terraform", "Kubernetes"),
//            education = "Bachelor’s or Master’s in IT/CS",
//            keySkills = listOf("Architecture", "Security", "DevOps Practices"),
//            jobSummary = JobSummary(
//                role = "Cloud Architect",
//                industryType = "Creative Tech",
//                functionalArea = "Infrastructure",
//                employmentType = "Full Time",
//                roleCategory = "Architecture",
//                vacancy = "1",
//                website = "https://adobe.wd5.myworkdayjobs.com",
//                companyDescription = "Adobe builds software for creatives, including Photoshop, Illustrator, and more."
//            ),
//            companyDescription = "Architect the future of cloud creativity with Adobe."
//        )
//    )
//
    val jobs = listOf("All", "Design", "Technology", "Finance", "Commerce")
//
//    val applications = offers.map {offer ->
//        Application(
//            offer = offer,
//            status = ApplicationStatus.entries.random()
//        )
//    }
//
//    val user = User(
//        name = "BELLIL",
//        firstName = "Mohamed Nadir",
//        dateOfBirth = "18/07/2002",
//        gender = "Male",
//        currentPosition = "Software Engineer",
//        middleName = "",
//        address = "570 route de ganges montpellier",
//        phone = "00000000000",
//        email = "bellil.mohamedndir@gmail.com",
//        summary = "",
//        workExperiences = emptyList(),
//        educations = emptyList(),
//        projects = emptyList(),
//        certifications = emptyList(),
//        internships = emptyList(),
//        languages = emptyList(),
//        skills = emptyList(),
//        resumesPath = emptyList()
//    )
}