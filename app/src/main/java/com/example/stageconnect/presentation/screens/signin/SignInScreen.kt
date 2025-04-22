package com.example.stageconnect.presentation.screens.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.R
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.AppLogo
import com.example.stageconnect.presentation.components.CustomCheckbox
import com.example.stageconnect.presentation.screens.signin.component.CustomEditText
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.BlueFont

@Composable
fun SignInScreen(modifier: Modifier = Modifier,
                 onForgetPassword: () -> Unit,
                 onSignIn: () -> Unit,
                 onSignUp: () -> Unit,
                 onSignUpWithGoogle: () -> Unit,) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    Column(
        modifier = modifier.fillMaxSize().padding(vertical = 24.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        AppLogo()

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            //Edit Texts
            CustomEditText(
                modifier = Modifier.padding(horizontal = 15.dp),
                text = email,
                leadingIcon = R.drawable.ic_email,
                hint = "",
                label = stringResource(R.string.email)
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomEditText(
                modifier = Modifier.padding(horizontal = 15.dp),
                text = password,
                leadingIcon = R.drawable.ic_password,
                isPassword = true,
                trailingIcon = R.drawable.ic_open_eye,
                colorTint = Blue,
                hint = "",
                label = stringResource(R.string.password)
            )
            Spacer(modifier = Modifier.height(20.dp))
            //Checkbox
            CustomCheckbox(
                modifier = Modifier.padding(horizontal = 15.dp),
                label = stringResource(R.string.remember_me)
            ){

            }
            Spacer(modifier = Modifier.height(20.dp))
            //sign in button
            AppButton(
                modifier = Modifier.fillMaxWidth(fraction = 0.9f),
                text = stringResource(R.string.sign_in),
                fontWeight = FontWeight.W700
            ) {
                onSignIn()
            }
            Spacer(modifier = Modifier.height(5.dp))
            //sign up
            Row {
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.don_t_have_an_account))
                        append(" ")
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500
                )
                Text(
                    text = stringResource(R.string.sign_up),
                    fontSize = 16.sp,
                    color = BlueFont,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier.clickable {
                        onSignUp()
                    }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            // forget password
            Text(
                text = stringResource(R.string.forget_password),
                fontSize = 16.sp,
                color = BlueFont,
                fontWeight = FontWeight.W600,
                modifier = Modifier.clickable {
                    onForgetPassword()
                }
            )
        }


        //------ OR ------
        Column(
            modifier = Modifier.padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(Color.Gray)
                )
                Text(
                    text = stringResource(R.string.or),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(Color.Gray)
                )
            }

            //sign up with google
            AppButton(
                modifier = Modifier.fillMaxWidth(fraction = 0.9f).padding(top = 10.dp),
                text = stringResource(R.string.sign_up_with_google),
                containerColor = Color.White,
                contentColor = Color.Black,
                fontWeight = FontWeight.Bold,
                icon = R.drawable.ic_google
            ) {
                onSignUpWithGoogle()
            }
        }
    }
}