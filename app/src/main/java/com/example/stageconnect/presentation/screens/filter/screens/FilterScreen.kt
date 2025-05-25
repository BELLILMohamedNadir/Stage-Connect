package com.example.stageconnect.presentation.screens.filter.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.stageconnect.R
import com.example.stageconnect.domain.model.SearchCriterion
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.CustomCircularProgressIndicator
import com.example.stageconnect.presentation.screens.filter.components.CustomFilterCard
import com.example.stageconnect.presentation.screens.filter.components.CustomRadioFilterCard
import com.example.stageconnect.presentation.screens.filter.components.LocationSalaryFilterCard
import com.example.stageconnect.ui.theme.BackgroundGray_
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.SoftBlue
import kotlinx.coroutines.delay

@Composable
fun FilterScreen(modifier: Modifier = Modifier) {
    // Define a list of search criteria that will be used to filter items in the UI.
    val criterion: List<SearchCriterion> = listOf(
        SearchCriterion.LocationAndSalaryCriterion(),
        SearchCriterion.WorkTypeCriterion(),
        SearchCriterion.JobLevelCriterion(),
        SearchCriterion.EmploymentTypeCriterion(),
        SearchCriterion.ExperienceCriterion(),
        SearchCriterion.EducationCriterion(),
        SearchCriterion.JobFunctionCriterion()
    )

    // Map to track the expanded states of each filter section.
    val expandedStates = remember { mutableStateMapOf<Int, Boolean>() }

    // A state to control when the screen is ready to be displayed after a short delay.
    var ready by remember { mutableStateOf(false) }

    // A map to hold selected checkbox items for each criterion.
    val checkedItems = remember { mutableStateMapOf<String, MutableList<String>>() }

    // A map to hold the state of checkboxes for each criterion.
    val checkboxStates = remember {
        mutableStateMapOf<Int, MutableMap<String, MutableState<Boolean>>>()
    }

    // State to track the selected radio button option.
    var selectedRadioButtonOption = remember { mutableStateOf("") }

    // Default value for the salary filter, which is per month.
    val defaultValue = stringResource(R.string.per_month)
    val salary by remember { mutableStateOf(defaultValue) }

    // State to store and reset search text input.
    val resetSearchText = remember { mutableStateOf("") }

    // Variable to store the location input value.
    var location = ""

    // Initial salary range for filtering, from 30,000 to 120,000.
    val initialRange = 30000f..120000f

    // State to store the current salary range selected by the user.
    var salaryRange by remember { mutableStateOf(initialRange) }

    // Get the context for showing Toast messages.
    val context = LocalContext.current


    // Conditionally show the content only when the screen is ready.
    // Main UI column that contains filters and buttons.
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Spacer to separate filter list from the top of the screen.
        Spacer(modifier = Modifier.height(1.dp).fillMaxWidth(0.9f).background(BackgroundGray_))
        Spacer(modifier = Modifier.height(20.dp))

        // LazyColumn to display each criterion filter as a list.
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 10.dp),
            contentPadding = PaddingValues(bottom = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Iterate over each search criterion and display the corresponding filter UI.
            itemsIndexed(criterion) { index, criteria ->
                // Determine if the current filter section is expanded or collapsed.
                val isExpanded = expandedStates[index] ?: true

                // Initialize checkbox state for each criterion if not already done.
                checkboxStates.getOrPut(index) {
                    initializeCheckboxSubMap(criteria)
                }

                // Display appropriate filter UI based on the criterion type.
                when(criteria) {
                    // Handle Location and Salary filter.
                    is SearchCriterion.LocationAndSalaryCriterion -> {
                        LocationSalaryFilterCard(
                            criterion = criteria.name,
                            resetSearchText = resetSearchText,
                            onLocationChange = { location = it },
                            salaryRange = initialRange,
                            currentRange = salaryRange,
                            onSalaryChange = {
                                salaryRange = it
                                // Update selected salary range in checkedItems.
                                checkedItems[criteria.name] = mutableListOf("${it.start.toInt()} - ${it.endInclusive.toInt()} €")
                            },
                            isExpanded = isExpanded,
                            onExpanded = { expandedStates[index] = !isExpanded }
                        )
                    }
                    // Handle Work Type filter with radio buttons.
                    is SearchCriterion.WorkTypeCriterion -> {
                        if (selectedRadioButtonOption.value.isNotEmpty()) {
                            checkedItems[criteria.name] = mutableListOf(selectedRadioButtonOption.value)
                        }

                        CustomRadioFilterCard(
                            criterion = criteria.name,
                            options = criteria.criteria,
                            selectedOption = selectedRadioButtonOption,
                            onOptionSelected = { selected ->
                                selectedRadioButtonOption.value = selected
                                checkedItems[criteria.name] = mutableListOf(selected)
                            },
                            isExpanded = isExpanded,
                            onExpanded = { expandedStates[index] = !isExpanded }
                        )
                    }
                    // Handle other custom filters with checkboxes.
                    else -> {
                        InitializeCustomFilterCard(
                            index = index,
                            criterion = criteria.name,
                            criteria = criteria.criteria,
                            isCheckedMap = checkboxStates[index] ?: emptyMap(),
                            isExpanded = isExpanded,
                            onCheck = { i, criterionName, selectedCriteria, checked ->
                                handleCheckState(
                                    index = i,
                                    criterion = criterionName,
                                    criteria = selectedCriteria,
                                    checked = checked,
                                    checkboxStates = checkboxStates,
                                    checkedItems = checkedItems
                                )
                            }
                        ) {
                            expandedStates[index] = !isExpanded
                        }
                    }
                }
            }
        }

        // Footer containing Reset and Apply buttons.
        Column {
            Spacer(Modifier.fillMaxWidth().height(1.dp).background(BackgroundGray_))
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp)) {
                // Reset button: Clears all selected filters.
                AppButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.reset),
                    contentColor = Blue,
                    containerColor = SoftBlue,
                    fontWeight = FontWeight.Bold
                ) {
                    // Reset all filter states and clear selected items.
                    checkboxStates.forEach { (_, innerMap) ->
                        innerMap.forEach { (_, state) -> state.value = false }
                    }
                    resetSearchText.value = ""
                    location = ""
                    selectedRadioButtonOption.value = ""
                    salaryRange = initialRange
                    checkedItems.clear()
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Apply button: Shows the number of selected filters.
                AppButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.apply),
                    fontWeight = FontWeight.Bold
                ) {
                    // Flatten the selected items and show a Toast with the count.
                    val selectedItems = checkedItems.values.flatten().toMutableList()
                    if (salary.isNotEmpty()) selectedItems.add(salary)
                    if (location.isNotEmpty()) selectedItems.add(location)
                    Toast.makeText(context, "${selectedItems.size} items selected", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


@Composable
fun InitializeCustomFilterCard(
    index: Int,
    criterion: String,
    criteria: List<String>,
    isCheckedMap: Map<String, MutableState<Boolean>>,
    isExpanded: Boolean,
    onCheck: (Int, String, String, Boolean) -> Unit,
    onExpanded: () -> Unit
) {
    CustomFilterCard(
        index = index,
        criterion = criterion,
        criteria = criteria,
        isCheckedMap = isCheckedMap,
        isExpended = isExpanded,
        onCheck = onCheck,
        onExpandToggle = onExpanded
    )
}

// Extracted Checkbox State Initialization
private fun initializeCheckboxSubMap(criteria: SearchCriterion): MutableMap<String, MutableState<Boolean>> {
    val subMap = mutableMapOf<String, MutableState<Boolean>>()
    criteria.criteria.forEach { label ->
        subMap[label] = mutableStateOf(false)
    }
    return subMap
}

// Extracted Check Logic
private fun handleCheckState(
    index: Int,
    criterion: String,
    criteria: String,
    checked: Boolean,
    checkboxStates: MutableMap<Int, MutableMap<String, MutableState<Boolean>>>,
    checkedItems: MutableMap<String, MutableList<String>>
) {
    checkboxStates[index]?.get(criteria)?.value = checked

    if (checked) {
        val updatedList = checkedItems[criterion] ?: mutableListOf()
        if (!updatedList.contains(criteria)) updatedList.add(criteria)
        checkedItems[criterion] = updatedList
    } else {
        checkedItems[criterion]?.remove(criteria)
        if (checkedItems[criterion].isNullOrEmpty()) {
            checkedItems.remove(criterion)
        }
    }
}

