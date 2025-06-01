package com.example.stageconnect.presentation.screens.offer.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stageconnect.R
import com.example.stageconnect.data.dtos.OfferDto
import com.example.stageconnect.domain.model.SearchCriterion
import com.example.stageconnect.domain.model.enums.SearchCriteria
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.CustomEditText
import com.example.stageconnect.presentation.components.CustomTextArea
import com.example.stageconnect.presentation.components.CustomMessage
import com.example.stageconnect.presentation.components.ObserveResult
import com.example.stageconnect.presentation.screens.filter.components.CustomFilterCard
import com.example.stageconnect.presentation.screens.filter.components.CustomRadioFilterCard
import com.example.stageconnect.presentation.screens.offer.components.CustomEditTextCard
import com.example.stageconnect.presentation.screens.offer.components.CustomExperienceCard
import com.example.stageconnect.presentation.screens.offer.components.CustomKeySkillsCard
import com.example.stageconnect.presentation.screens.offer.components.CustomSalaryCard
import com.example.stageconnect.presentation.screens.offer.viewmodel.JobDetailsViewModel
import com.example.stageconnect.presentation.screens.profile.viewmodels.ProfileViewModel
import com.example.stageconnect.presentation.viewmodels.OfferViewModel
import com.example.stageconnect.ui.theme.BackgroundGray_
import com.example.stageconnect.ui.theme.GrayFont

@Composable
fun AddOfferScreen(
    modifier: Modifier = Modifier,
    offerViewModel: OfferViewModel = hiltViewModel(),
    jobDetailsViewModel: JobDetailsViewModel,
    profileViewModel: ProfileViewModel,
    onNext: () -> Unit
) {
    val context = LocalContext.current
    val offer = jobDetailsViewModel.offer

    // Compose the ordered list as before
    val combinedOrderedList: List<Any> = listOf(
        stringResource(R.string.position_title),
        stringResource(R.string.website_),
        stringResource(R.string.location),
        stringResource(R.string.salary),
        SearchCriterion.WorkTypeCriterion(),
        SearchCriterion.JobLevelCriterion(),
        SearchCriterion.EmploymentTypeCriterion(),
        stringResource(R.string.experience),
        stringResource(R.string.key_skills_),
        stringResource(R.string.options),
        SearchCriterion.EducationCriterion(),
        SearchCriterion.JobFunctionCriterion(),
        stringResource(R.string.start_date),
        stringResource(R.string.requirements_and_skills_),
        stringResource(R.string.job_description),
        stringResource(R.string.company_description_)
    )

    // --- Centralized state for all selection criteria ---
    val selectionState = remember {
        mutableStateMapOf<String, MutableSet<String>>().apply {
            put(SearchCriteria.WORK_TYPE.label, mutableSetOf())
            put(SearchCriteria.JOB_LEVEL.label, mutableSetOf())
            put(SearchCriteria.EMPLOYMENT_TYPE.label, mutableSetOf())
            put(SearchCriteria.EDUCATION.label, mutableSetOf())
            put(SearchCriteria.JOB_FUNCTION.label, mutableSetOf())
            put("Options", mutableSetOf())
        }
    }

    // --- Input fields ---
    val positionTitle = remember { mutableStateOf("") }
    val website = remember { mutableStateOf("") }
    val location = remember { mutableStateOf("") }
    var salaryStart by remember { mutableLongStateOf(30000L) }
    var salaryEnd by remember { mutableLongStateOf(120000L) }
    val initializeSalaryUnit = stringResource(R.string.per_month)
    var salaryUnit = remember { mutableStateOf(initializeSalaryUnit) }
    val experience = remember { mutableStateOf("") }
    val startDate = remember { mutableStateOf("") }
    val endDate = remember { mutableStateOf("") }
    val keySkills = remember { mutableStateOf(emptyList<String>()) }
    var requirementsAndSkills by remember { mutableStateOf("") }
    var jobDescription by remember { mutableStateOf("") }
    var companyDescription by remember {
        mutableStateOf(profileViewModel.user.value?.summary ?: "")
    }

    // --- Radio state for WorkType ---
    val selectedWorkType = remember { mutableStateOf("") }

    // --- Expansion state for cards ---
    val expandedStates = remember { mutableStateMapOf<Int, Boolean>() }

    // --- Loading & result state ---
    val isLoading = remember { mutableStateOf(false) }
    val createOfferResult by offerViewModel.createOfferResult.observeAsState()
    val updateOfferResult by offerViewModel.updateOfferResult.observeAsState()

    // --- Offer initialization (edit mode) ---
    LaunchedEffect(offer.value) {
        offer.value?.let { dto ->
            positionTitle.value = dto.position
            website.value = dto.website.orEmpty()
            location.value = dto.location
            salaryStart = dto.salaryStart
            salaryEnd = dto.salaryEnd
            salaryUnit.value = dto.salaryUnit
            experience.value = dto.experience
            keySkills.value = dto.keySkills
            requirementsAndSkills = dto.requirementSkills
            jobDescription = dto.jobDescription
            companyDescription = dto.companyDescription ?: profileViewModel.user.value?.summary.orEmpty()
            selectedWorkType.value = dto.workType
            startDate.value = dto.startDate ?: ""
            endDate.value = dto.endDate ?:""
            // Fill selectionState from offer
            selectionState[SearchCriteria.WORK_TYPE.label]?.apply {
                clear(); if (dto.workType.isNotBlank()) add(dto.workType)
            }
            selectionState[SearchCriteria.JOB_LEVEL.label]?.apply {
                clear(); addAll(dto.jobLevels ?: emptyList())
            }
            selectionState[SearchCriteria.EMPLOYMENT_TYPE.label]?.apply {
                clear(); addAll(dto.employmentTypes ?: emptyList())
            }
            selectionState[SearchCriteria.EDUCATION.label]?.apply {
                clear(); addAll(dto.education ?: emptyList())
            }
            selectionState[SearchCriteria.JOB_FUNCTION.label]?.apply {
                clear(); addAll(dto.jobFunction ?: emptyList())
            }
            selectionState["Options"]?.apply {
                clear(); addAll(dto.options ?: emptyList())
            }
        }
    }

    // --- Selection update lambdas ---
    fun onCheckChanged(criterion: String, label: String, checked: Boolean) {
        val set = selectionState.getOrPut(criterion) { mutableSetOf() }
        if (checked) set.add(label) else set.remove(label)
    }
    fun onRadioChanged(criterion: String, label: String) {
        selectionState[criterion]?.apply {
            clear()
            add(label)
        }
        if (criterion == SearchCriteria.WORK_TYPE.label) {
            selectedWorkType.value = label
        }
    }


    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(bottom = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(combinedOrderedList) { index, item ->
                val isExpanded = expandedStates[index] ?: true
                when (item) {
                    is String -> {
                        when (item) {
                            stringResource(R.string.position_title) -> CustomEditTextCard(
                                text = positionTitle,
                                criterion = item,
                                hint = item,
                                onTextChange = { positionTitle.value = it },
                                isExpanded = isExpanded,
                                onExpanded = { expandedStates[index] = !isExpanded }
                            )
                            stringResource(R.string.website_) -> CustomEditTextCard(
                                text = website,
                                criterion = item,
                                hint = item,
                                onTextChange = { website.value = it },
                                isExpanded = isExpanded,
                                onExpanded = { expandedStates[index] = !isExpanded }
                            )
                            stringResource(R.string.location) -> CustomEditTextCard(
                                text = location,
                                criterion = item,
                                hint = item,
                                onTextChange = { location.value = it },
                                isExpanded = isExpanded,
                                onExpanded = { expandedStates[index] = !isExpanded }
                            )
                            stringResource(R.string.salary) -> CustomSalaryCard(
                                salaryUnit = salaryUnit,
                                criterion = item,
                                salaryRange = 30000f..120000f,
                                currentRange = salaryStart.toFloat()..salaryEnd.toFloat(),
                                onSalaryChange = {
                                    salaryStart = it.start.toLong()
                                    salaryEnd = it.endInclusive.toLong()
                                },
                                isExpanded = isExpanded,
                                onExpanded = { expandedStates[index] = !isExpanded }
                            ) { salaryUnit.value = it }
                            stringResource(R.string.experience) -> CustomExperienceCard(
                                text = experience,
                                criterion = item,
                                onTextChange = { experience.value = it },
                                isExpanded = isExpanded,
                                onExpanded = { expandedStates[index] = !isExpanded }
                            )
                            stringResource(R.string.key_skills_) -> CustomKeySkillsCard(
                                skills = keySkills,
                                criterion = item,
                                hint = item,
                                onListChange = { keySkills.value = it },
                                isExpanded = isExpanded,
                                onExpanded = { expandedStates[index] = !isExpanded }
                            )
                            stringResource(R.string.options) -> {
                                val criteria = stringArrayResource(id = R.array.option_list).toList()
                                CustomFilterCard(
                                    width = 1f,
                                    index = index,
                                    criterion = "Options",
                                    criteria = criteria,
                                    isCheckedMap = criteria.associateWith { label ->
                                        mutableStateOf(selectionState["Options"]?.contains(label) == true)
                                    },
                                    isExpended = isExpanded,
                                    onCheck = { _, _, label, checked ->
                                        onCheckChanged("Options", label, checked)
                                    },
                                    onExpandToggle = { expandedStates[index] = !isExpanded }
                                )
                            }
                            stringResource(R.string.start_date) ->{
                                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween) {
                                    Column(
                                        modifier = Modifier.weight(1f).padding(horizontal = 10.dp)
                                    ) {
                                        Text(text = stringResource(R.string.start_date), color = GrayFont, fontSize = 14.sp,
                                            modifier = Modifier.padding(start = 10.dp))
                                        Spacer(modifier = Modifier.height(4.dp))
                                        CustomEditText(
                                            defaultText = startDate.value,
                                            label = stringResource(R.string.start_date),
                                            isDate = true,
                                            trailingIcon = R.drawable.ic_polygon,
                                            onValueChange = { startDate.value = it }
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column (
                                        modifier = Modifier.weight(1f).padding(horizontal = 10.dp)
                                    ){
                                        Text(text = stringResource(R.string.end_date), color = GrayFont, fontSize = 14.sp,
                                            modifier = Modifier.padding(start = 10.dp))
                                        Spacer(modifier = Modifier.height(4.dp))
                                        CustomEditText(
                                            defaultText = endDate.value,
                                            label = stringResource(R.string.end_date),
                                            isDate = true,
                                            trailingIcon = R.drawable.ic_polygon,
                                            onValueChange = { endDate.value = it }
                                        )
                                    }

                                }
                            }
                            stringResource(R.string.requirements_and_skills_) -> Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = item, color = Color.Black, fontSize = 14.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                CustomTextArea(
                                    defaultText = requirementsAndSkills
                                ) { requirementsAndSkills = it }
                            }
                            stringResource(R.string.job_description) -> Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = item, color = Color.Black, fontSize = 14.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                CustomTextArea(
                                    defaultText = jobDescription
                                ) { jobDescription = it }
                            }
                            stringResource(R.string.company_description_) -> Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = item, color = Color.Black, fontSize = 14.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                CustomTextArea(
                                    defaultText = companyDescription
                                ) { companyDescription = it }
                            }
                            else -> Text(text = item, modifier = Modifier.padding(8.dp))
                        }
                    }
                    is SearchCriterion -> {
                        when (item) {
                            is SearchCriterion.WorkTypeCriterion -> CustomRadioFilterCard(
                                width = 1f,
                                criterion = item.name,
                                options = item.criteria,
                                selectedOption = selectedWorkType,
                                onOptionSelected = { selected ->
                                    onRadioChanged(item.name, selected)
                                },
                                isExpanded = isExpanded,
                                onExpanded = { expandedStates[index] = !isExpanded }
                            )
                            else -> CustomFilterCard(
                                width = 1f,
                                index = index,
                                criterion = item.name,
                                criteria = item.criteria,
                                isCheckedMap = item.criteria.associateWith { label ->
                                    mutableStateOf(selectionState[item.name]?.contains(label) == true)
                                },
                                isExpended = isExpanded,
                                onCheck = { _, criterion, label, checked ->
                                    onCheckChanged(criterion, label, checked)
                                },
                                onExpandToggle = { expandedStates[index] = !isExpanded }
                            )
                        }
                    }
                }
            }
        }

        // Bottom Row (Reset/Publish)
        Column {
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(BackgroundGray_)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {

                AppButton(
                    modifier = Modifier.weight(1f),
                    text = if (offer.value != null) stringResource(R.string.update) else stringResource(R.string.publish),
                    fontWeight = FontWeight.Bold,
                    isLoading = isLoading
                ) {
                    // Extract selected lists
                    val selectedJobLevels = selectionState[SearchCriteria.JOB_LEVEL.label]?.toList() ?: emptyList()
                    val selectedEmploymentTypes = selectionState[SearchCriteria.EMPLOYMENT_TYPE.label]?.toList() ?: emptyList()
                    val selectedEducations = selectionState[SearchCriteria.EDUCATION.label]?.toList() ?: emptyList()
                    val selectedJobFunctions = selectionState[SearchCriteria.JOB_FUNCTION.label]?.toList() ?: emptyList()
                    val options = selectionState["Options"]?.toList() ?: emptyList()
                    val selectedWorkType = selectionState[SearchCriteria.WORK_TYPE.label]?.firstOrNull() ?: ""

                    // Validate required fields
                    val missingFields = mutableListOf<String>()
                    if (positionTitle.value.isBlank()) missingFields.add("Position Title")
                    if (website.value.isBlank()) missingFields.add("Website")
                    if (location.value.isBlank()) missingFields.add("Location")
                    if (salaryStart <= 0L || salaryEnd <= 0L || salaryStart > salaryEnd) missingFields.add("Valid Salary Range")
                    if (experience.value.isBlank()) missingFields.add("Experience")
                    if (keySkills.value.isEmpty()) missingFields.add("Key Skills")
                    if (startDate.value.isEmpty()) missingFields.add("Date de début")
                    if (endDate.value.isEmpty()) missingFields.add("Date de fin")
                    if (requirementsAndSkills.isBlank()) missingFields.add("Requirements and Skills")
                    if (jobDescription.isBlank()) missingFields.add("Job Description")
                    if (companyDescription.isBlank()) missingFields.add("Company Description")
                    if (selectedWorkType.isBlank()) missingFields.add("Work Type")
                    if (salaryUnit.value.isBlank()) missingFields.add("Salary Unit")
                    if (selectedJobLevels.isEmpty()) missingFields.add("Job Level")
                    if (selectedEmploymentTypes.isEmpty()) missingFields.add("Employment Type")
                    if (selectedEducations.isEmpty()) missingFields.add("Education")
                    if (selectedJobFunctions.isEmpty()) missingFields.add("Job Function")
                    if (options.isEmpty()) missingFields.add("Options")

                    if (missingFields.isNotEmpty()) {
                        Toast.makeText(
                            context,
                            "Please fill: ${missingFields.joinToString(", ")}",
                            Toast.LENGTH_LONG
                        ).show()
                        return@AppButton
                    }

                    val offerDto = OfferDto(
                        position = positionTitle.value,
                        website = website.value,
                        location = location.value,
                        salaryStart = salaryStart,
                        salaryEnd = salaryEnd,
                        startDate = startDate.value,
                        endDate = endDate.value,
                        salaryUnit = salaryUnit.value,
                        options = options,
                        experience = experience.value,
                        keySkills = keySkills.value,
                        requirementSkills = requirementsAndSkills.trim(),
                        jobDescription = jobDescription.trim(),
                        companyDescription = companyDescription.trim(),
                        workType = selectedWorkType,
                        jobLevels = selectedJobLevels,
                        employmentTypes = selectedEmploymentTypes,
                        education = selectedEducations,
                        jobFunction = selectedJobFunctions,
                        recruiterId = -1
                    )

                     if (offer.value != null)
                         offerViewModel.updateOffer(offerDto = offerDto, offerId = offer.value?.id!!)
                     else
                         offerViewModel.createOffer(offerDto)
                }
            }
        }
    }

    ObserveResult(
        result = createOfferResult,
        onLoading = { isLoading.value = true },
        onSuccess = {
            isLoading.value = false
            jobDetailsViewModel.setOffer(null)
            onNext()
        },
        onError = {
            isLoading.value = false
            CustomMessage.Show(stringResource(R.string.error_occurred))
        }
    )

    ObserveResult(
        result = updateOfferResult,
        onLoading = { isLoading.value = true },
        onSuccess = {
            isLoading.value = false
            jobDetailsViewModel.setOffer(null)
            onNext()
        },
        onError = {
            isLoading.value = false
            CustomMessage.Show(stringResource(R.string.error_occurred))
        }
    )
}