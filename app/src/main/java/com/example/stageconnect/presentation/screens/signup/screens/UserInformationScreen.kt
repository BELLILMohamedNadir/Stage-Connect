package com.example.stageconnect.presentation.screens.signup.screens

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.stageconnect.R
import com.example.stageconnect.data.dtos.AuthDto
import com.example.stageconnect.data.dtos.EstablishmentsDto
import com.example.stageconnect.data.dtos.UserDto
import com.example.stageconnect.domain.model.enums.ROLE
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.CustomEditText
import com.example.stageconnect.presentation.screens.signup.viewmodels.RegisterViewModel
import com.example.stageconnect.domain.result.Result
import com.example.stageconnect.presentation.components.ObserveResult
import com.example.stageconnect.presentation.screens.profile.viewmodels.ProfileViewModel


@Composable
fun UserInformationScreen(modifier: Modifier = Modifier,
                          viewModel: ProfileViewModel = hiltViewModel(),
                          registerViewModel: RegisterViewModel,
                          onNext: () -> Unit) {


    val inputFields = listOf(
        stringResource(R.string.first_name) to -1,
        stringResource(R.string.middle_or_last_name) to -1,
        stringResource(R.string.date_of_birth) to R.drawable.ic_calender,
        stringResource(R.string.university) to R.drawable.ic_university_bottom_navigation,
        stringResource(R.string.phone_number) to R.drawable.ic_phone,
        stringResource(R.string.gender) to R.drawable.ic_polygon
    )

    val imageUri by viewModel.imageUri

    val errorMessage = stringResource(R.string.add_necessary_data)
    val male = stringResource(R.string.male)
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(false) }
    var firstName by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var establishmentId by remember { mutableLongStateOf(-1L) }
    var phoneNumber by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf(male) }
    val establishments : MutableList<EstablishmentsDto>  = mutableListOf()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { viewModel.onImageSelected(it) }
    }

    val registerResult by registerViewModel.registerResult.observeAsState()
    val getAllEstablishmentResult by registerViewModel.getAllEstablishmentResult.observeAsState()

    ObserveResult(
        result = registerResult,
        onLoading = {
            isLoading.value = true
        },
        onSuccess = {
            isLoading.value = false
            registerViewModel.clearData()
            onNext()
        },
        onError = {
            isLoading.value = false
            Toast.makeText(context, "Error occurred, try again", Toast.LENGTH_SHORT).show()
        }
    )

    ObserveResult(
        result = getAllEstablishmentResult,
        onLoading = {
        },
        onSuccess = {result ->
            establishments.addAll(result)
        },
        onError = {
            Toast.makeText(context, "Error occurred, There is no establishment", Toast.LENGTH_SHORT).show()
        }
    )


    LaunchedEffect(Unit) {
        if (getAllEstablishmentResult == null || getAllEstablishmentResult !is Result.Success) {
            registerViewModel.getAllEstablishment()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            registerViewModel.resetEstablishments()
        }
    }


    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Box(
            modifier = Modifier.width(130.dp),
            contentAlignment = Alignment.Center
        ){
            Image(painter =
                if (imageUri != null)   rememberAsyncImagePainter(imageUri) else painterResource(R.drawable.ic_profile)
                , contentDescription = "ic_profile",
                modifier = Modifier.size(120.dp)
                    .clip(shape = CircleShape),
                contentScale = ContentScale.Crop)
            IconButton(modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(top = 16.dp, start = 16.dp), onClick = {launcher.launch("image/*")}) {
                Icon(painter = painterResource(R.drawable.ic_add)
                    , contentDescription = "ic_add",
                    tint = Color.Unspecified)
            }
        }

        inputFields.forEach { (label, icon) ->
            when(label){
                stringResource(R.string.gender) -> {
                    CustomEditText(label = label, trailingIcon = icon,
                        list = listOf(stringResource(R.string.male), stringResource(R.string.female)),
                        onValueChange = {gender = it})
                }
                stringResource(R.string.date_of_birth) -> {
                    CustomEditText(label = label, trailingIcon = icon, isDate = true, imeAction = ImeAction.Next, onValueChange = {dateOfBirth = it})
                }
                stringResource(R.string.phone_number) -> {
                    CustomEditText(label = label, trailingIcon = icon, imeAction = ImeAction.Next, keyboardType = KeyboardType.Number) { phoneNumber = it}
                }
                stringResource(R.string.university) -> {
                    if (registerViewModel.getRole() == ROLE.STUDENT){
                        CustomEditText(
                            label = label,
                            establishmentList = establishments,
                            hasEstablishmentList = true,
                            trailingIcon = icon,
                            onEstablishmentValueChange = {
                                establishmentId = it
                            }) {
                        }
                    }
                }
                stringResource(R.string.first_name) -> {
                    CustomEditText(label = label, imeAction = ImeAction.Next, trailingIcon = icon) { firstName = it}
                }
                else -> {
                    CustomEditText(label = label, imeAction = ImeAction.Next, trailingIcon = icon) { name = it}
                }
            }
        }

        AppButton(text = stringResource(R.string.continue_), isLoading = isLoading) {
            val authDto = AuthDto(
                name =  name,
                firstName =  firstName,
                dateOfBirth =  dateOfBirth,
                email =  registerViewModel.getEmail()!!,
                password =  registerViewModel.getPassword()!!,
                role = registerViewModel.getRole()!!,
                phone = phoneNumber,
                gender =  gender,
                establishmentId = establishmentId,
                expertises = registerViewModel.getExpertise()!!.toList()
            )
            if (isInfoValid(authDto = authDto, registerViewModel.getRole()!!)){
                registerViewModel.register(authDto, imageUri)
            }else{
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

}
fun isInfoValid(authDto: AuthDto, role: ROLE): Boolean{
    return if (role == ROLE.STUDENT){
        (authDto.establishmentId != -1L
                && (authDto.name?:"").isNotBlank()
                && (authDto.firstName?:"").isNotBlank()
                && (authDto.dateOfBirth?:"").isNotBlank()
                && (authDto.phone?:"").isNotBlank()
                && (authDto.gender?:"").isNotBlank())
    }else{
        ((authDto.name?:"").isNotBlank()
                && (authDto.firstName?:"").isNotBlank()
                && (authDto.dateOfBirth?:"").isNotBlank()
                && (authDto.phone?:"").isNotBlank()
                && (authDto.gender?:"").isNotBlank())
    }
}