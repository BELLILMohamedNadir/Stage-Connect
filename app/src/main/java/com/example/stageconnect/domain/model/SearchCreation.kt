package com.example.stageconnect.domain.model

import com.example.stageconnect.domain.model.enums.Education
import com.example.stageconnect.domain.model.enums.EmploymentType
import com.example.stageconnect.domain.model.enums.Experience
import com.example.stageconnect.domain.model.enums.JobFunction
import com.example.stageconnect.domain.model.enums.JobLevel
import com.example.stageconnect.domain.model.enums.LocationAndSalary
import com.example.stageconnect.domain.model.enums.SearchCriteria
import com.example.stageconnect.domain.model.enums.WorkType

sealed class SearchCriterion() {
    abstract val name: String
    abstract val criteria: List<String>

    data class EducationCriterion(
        override val name: String = SearchCriteria.EDUCATION.label,
        override val criteria: List<String> = Education.entries.map { it.label }
    ) : SearchCriterion()

    data class EmploymentTypeCriterion(
        override val name: String = SearchCriteria.EMPLOYMENT_TYPE.label,
        override val criteria: List<String> = EmploymentType.entries.map { it.label }
    ) : SearchCriterion()

    data class ExperienceCriterion(
        override val name: String = SearchCriteria.EXPERIENCE.label,
        override val criteria: List<String> = Experience.entries.map { it.label }
    ) : SearchCriterion()

    data class JobFunctionCriterion(
        override val name: String = SearchCriteria.JOB_FUNCTION.label,
        override val criteria: List<String> = JobFunction.entries.map { it.label }
    ) : SearchCriterion()

    data class JobLevelCriterion(
        override val name: String = SearchCriteria.JOB_LEVEL.label,
        override val criteria: List<String> = JobLevel.entries.map { it.label }
    ) : SearchCriterion()

    data class LocationAndSalaryCriterion(
        override val name: String = SearchCriteria.LOCATION_SALARY.label,
        override val criteria: List<String> = LocationAndSalary.entries.map { it.label }
    ) : SearchCriterion()

    data class WorkTypeCriterion(
        override val name: String = SearchCriteria.WORK_TYPE.label,
        override val criteria: List<String> = WorkType.entries.map { it.label }
    ) : SearchCriterion()
}
