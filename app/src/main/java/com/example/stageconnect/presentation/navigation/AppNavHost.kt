package com.example.stageconnect.presentation.navigation

import android.app.Activity
import android.content.Context
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.stageconnect.R
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT
import com.example.stageconnect.domain.CONSTANT.USER_ROLE
import com.example.stageconnect.domain.model.enums.ROLE
import com.example.stageconnect.presentation.components.StudentBottomNavigationBar
import com.example.stageconnect.presentation.components.CustomTopAppBar
import com.example.stageconnect.presentation.components.EstablishmentBottomNavigationBar
import com.example.stageconnect.presentation.components.RecruiterBottomNavigationBar
import com.example.stageconnect.presentation.screens.applications.screens.RecruiterApplicationsScreen
import com.example.stageconnect.presentation.screens.applications.screens.StudentApplicationsScreen
import com.example.stageconnect.presentation.screens.applications.viewmodels.ApplicationViewModel
import com.example.stageconnect.presentation.screens.applicationstatus.screens.EstablishmentApplicationStatus
import com.example.stageconnect.presentation.screens.applicationstatus.screens.RecruiterApplicationStatus
import com.example.stageconnect.presentation.screens.applicationstatus.screens.StudentApplicationStatus
import com.example.stageconnect.presentation.screens.appstart.AppStartScreen
import com.example.stageconnect.presentation.screens.establishmentstudent.screen.EstablishmentStudentScreen
import com.example.stageconnect.presentation.screens.filter.screens.FilterScreen
import com.example.stageconnect.presentation.screens.filter.viewmodel.FilterViewModel
import com.example.stageconnect.presentation.screens.home.screens.EstablishmentHomeScreen
import com.example.stageconnect.presentation.screens.home.screens.RecruiterHomeScreen
import com.example.stageconnect.presentation.screens.home.screens.StudentHomeScreen
import com.example.stageconnect.presentation.screens.messaging.screens.MessagingScreen
import com.example.stageconnect.presentation.screens.messaging.screens.RoomScreen
import com.example.stageconnect.presentation.screens.messaging.viewmodel.RoomViewModel
import com.example.stageconnect.presentation.screens.offer.viewmodel.JobDetailsViewModel
import com.example.stageconnect.presentation.screens.offer.screens.AddOfferScreen
import com.example.stageconnect.presentation.screens.offer.screens.JobDetailsScreen
import com.example.stageconnect.presentation.screens.offer.screens.OfferApplyScreen
import com.example.stageconnect.presentation.screens.onboarding.screen.OnboardingScreen
import com.example.stageconnect.presentation.screens.profile.screens.CertificationDetailsScreen
import com.example.stageconnect.presentation.screens.profile.screens.CertificationScreen
import com.example.stageconnect.presentation.screens.profile.screens.ContactInformationScreen
import com.example.stageconnect.presentation.screens.profile.screens.CvResumeScreen
import com.example.stageconnect.presentation.screens.profile.screens.EducationDetailsScreen
import com.example.stageconnect.presentation.screens.profile.screens.EducationScreen
import com.example.stageconnect.presentation.screens.profile.screens.InternshipDetailsScreen
import com.example.stageconnect.presentation.screens.profile.screens.InternshipScreen
import com.example.stageconnect.presentation.screens.profile.screens.LanguageDetailsScreen
import com.example.stageconnect.presentation.screens.profile.screens.LanguageScreen
import com.example.stageconnect.presentation.screens.profile.screens.OrganizationInfoScreen
import com.example.stageconnect.presentation.screens.profile.screens.OrganizationProfileScreen
import com.example.stageconnect.presentation.screens.profile.screens.ProfileDetailsScreen
import com.example.stageconnect.presentation.screens.profile.screens.ProfileScreen
import com.example.stageconnect.presentation.screens.profile.screens.ProjectDetailsScreen
import com.example.stageconnect.presentation.screens.profile.screens.ProjectScreen
import com.example.stageconnect.presentation.screens.profile.screens.SkillScreen
import com.example.stageconnect.presentation.screens.profile.screens.SummaryScreen
import com.example.stageconnect.presentation.screens.profile.screens.WorkExperienceDetailsScreen
import com.example.stageconnect.presentation.screens.profile.screens.WorkExperienceScreen
import com.example.stageconnect.presentation.screens.profile.viewmodels.CertificationViewModel
import com.example.stageconnect.presentation.screens.profile.viewmodels.EducationViewModel
import com.example.stageconnect.presentation.screens.profile.viewmodels.InternshipViewModel
import com.example.stageconnect.presentation.screens.profile.viewmodels.LanguageViewModel
import com.example.stageconnect.presentation.screens.profile.viewmodels.ProfileViewModel
import com.example.stageconnect.presentation.screens.profile.viewmodels.ProjectViewModel
import com.example.stageconnect.presentation.screens.profile.viewmodels.WorkExperienceViewModel
import com.example.stageconnect.presentation.screens.savedjobs.screens.SavedJobsScreen
import com.example.stageconnect.presentation.screens.search.screens.SearchScreen
import com.example.stageconnect.presentation.screens.signin.SignInScreen
import com.example.stageconnect.presentation.screens.signup.screens.ExpertiseSelectionScreen
import com.example.stageconnect.presentation.screens.signup.screens.ProfileSelectionScreen
import com.example.stageconnect.presentation.screens.signup.screens.UserInformationScreen
import com.example.stageconnect.presentation.screens.signup.viewmodels.RegisterViewModel
import com.example.stageconnect.presentation.viewmodels.OfferViewModel
import com.example.stageconnect.ui.theme.Blue

@Composable
fun AppNavHost(modifier: Modifier = Modifier,
               context: Context,
               storageRepository: StorageRepository) {
    val navController = rememberNavController()
    val jobDetailsViewModel: JobDetailsViewModel = hiltViewModel()
    val applicationViewModel: ApplicationViewModel = hiltViewModel()
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val offerViewModel: OfferViewModel = hiltViewModel()
    val registerViewModel: RegisterViewModel = hiltViewModel()
    val roomViewModel: RoomViewModel = hiltViewModel()
    val filterViewModel: FilterViewModel = hiltViewModel()
    val certificationViewModel: CertificationViewModel = hiltViewModel()
    val educationViewModel: EducationViewModel = hiltViewModel()
    val internshipViewModel: InternshipViewModel = hiltViewModel()
    val languageViewModel: LanguageViewModel = hiltViewModel()
    val projectViewModel: ProjectViewModel = hiltViewModel()
    val workExperienceViewModel: WorkExperienceViewModel = hiltViewModel()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if (storageRepository.getBoolean(CONSTANT.ONBOARDING)) Screen.SignIn.route else Screen.AppStart.route
    ) {

        composable(
            route = Screen.AppStart.route,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) }) {
            Scaffold { innerPadding ->
                AppStartScreen(
                    modifier = Modifier.padding(innerPadding),
                    onClick = {
                        navController.navigate(Screen.Onboarding.route) {
                            popUpTo(Screen.AppStart.route) { inclusive = true }
                        }
                    }
                )
            }
        }

        composable(
            route = Screen.Onboarding.route,
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) }) {
            Scaffold { innerPadding ->
                OnboardingScreen(
                    modifier = Modifier.padding(innerPadding),
                    onFinish = {
                        navController.navigate(Screen.SignIn.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    }
                )
            }
        }

        composable(route = Screen.SignIn.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = {
                            if (context is Activity) {
                                context.finish()
                            }
                        },
                        navigationIcon = R.drawable.ic_arrow_back
                    )
                }
            ) { innerPadding ->
                SignInScreen(
                    modifier = Modifier.padding(innerPadding),
                    registerViewModel = registerViewModel,
                    profileViewModel = profileViewModel,
                    onForgetPassword = {},
                    onStudentSignIn = {
                        navController.navigate(Screen.StudentHome.route) {
                            popUpTo(Screen.SignIn.route) { inclusive = true }
                        }
                    },
                    onSignUp = { navController.navigate(Screen.ProfileSelection.route) },
                    onRecruiterSignIn = {
                        navController.navigate(Screen.RecruiterHome.route) {
                            popUpTo(Screen.SignIn.route) { inclusive = true }
                        }
                    },
                    onEstablishmentSignIn = {
                        navController.navigate(Screen.EstablishmentHome.route) {
                            popUpTo(Screen.SignIn.route) { inclusive = true }
                        }
                    },
                    onSignUpWithGoogle = {})
            }
        }

        composable(route = Screen.ProfileSelection.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back
                    )
                }
            ) { innerPadding ->
                ProfileSelectionScreen(
                    modifier = Modifier.padding(innerPadding),
                    registerViewModel = registerViewModel,
                    onExpertiseScreenNavigate = {
                        navController.navigate(Screen.ExpertiseSelection.route)
                    },
                    onUserInformationScreenNavigate = {
                        navController.navigate(Screen.UserInformation.route)
                    }
                )
            }
        }

        composable(route = Screen.ExpertiseSelection.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back
                    )
                }
            ) { innerPadding ->
                ExpertiseSelectionScreen(
                    modifier = Modifier.padding(innerPadding),
                    registerViewModel = registerViewModel,
                    onNext = {
                        navController.navigate(Screen.UserInformation.route)
                    }
                )
            }
        }

        composable(route = Screen.UserInformation.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back
                    )
                }
            ) { innerPadding ->
                UserInformationScreen(
                    modifier = Modifier.padding(innerPadding),
                    registerViewModel = registerViewModel,
                    onNext = {
                        navController.navigate(Screen.SignIn.route) {
                            popUpTo(Screen.UserInformation.route) { inclusive = true }
                        }
                    }
                )
            }
        }

        composable(route = Screen.StudentHome.route) {
            Scaffold(
                bottomBar = { StudentBottomNavigationBar(navController = navController) }
            ) { innerPadding ->
                StudentHomeScreen(
                    modifier = Modifier.padding(innerPadding),
                    jobDetailsViewModel = jobDetailsViewModel,
                    profileViewModel = profileViewModel,
                    onFilterClick = {
                        navController.navigate(Screen.Filter.route)
                    },
                    onSeeAll = {
                        navController.navigate(Screen.Search.route)
                    },
                    onOfferCardClick = {
                        navController.navigate(Screen.JobDetails.route)
                    }) {
                    navController.navigate(Screen.Search.route)
                }
            }
        }

        composable(route = Screen.RecruiterHome.route) {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            jobDetailsViewModel.setOffer(null)
                            navController.navigate(Screen.AddOffer.route)
                        },
                        containerColor = Blue,
                        contentColor = Color.White
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                },
                floatingActionButtonPosition = FabPosition.End,
                bottomBar = { RecruiterBottomNavigationBar(navController = navController) }
            ) { innerPadding ->
                RecruiterHomeScreen(
                    modifier = Modifier.padding(innerPadding),
                    jobDetailsViewModel = jobDetailsViewModel,
                    profileViewModel = profileViewModel,
                    onFilterClick = {
                        navController.navigate(Screen.Filter.route)
                    },
                    onSeeAll = {navController.navigate(Screen.Search.route)},
                    onOfferCardClick = {
                        navController.navigate(Screen.AddOffer.route)
                    }) {
                    navController.navigate(Screen.Search.route)
                }
            }
        }

        composable(route = Screen.EstablishmentStudents.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.students)
                    )
                },
                bottomBar = { EstablishmentBottomNavigationBar(navController = navController) }
            ) { innerPadding ->
                EstablishmentStudentScreen (
                    modifier = Modifier.padding(innerPadding),
                    profileViewModel = profileViewModel,
                    onFilterClick = {
                        navController.navigate(Screen.Filter.route)
                    }) {
                }
            }
        }


        composable(route = Screen.Search.route) {
            Scaffold(
            ) {innerPadding ->
                SearchScreen(
                    modifier = Modifier.padding(innerPadding),
                    jobDetailsViewModel = jobDetailsViewModel,
                    onFilterClick = {
                        navController.navigate(Screen.Filter.route)
                    },
                    filterViewModel = filterViewModel,
                    profileViewModel = profileViewModel,
                    onOfferCardClick = {
                        navController.navigate(Screen.JobDetails.route)
                    }) {
                    navController.popBackStack()
                }
            }
        }

        composable(route = Screen.Filter.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_close,
                        title = stringResource(R.string.filter_options)
                    )
                },
            ) { innerPadding ->
                FilterScreen(modifier = Modifier.padding(innerPadding),
                    filterViewModel = filterViewModel){
                    navController.popBackStack()
                }
            }
        }

        composable(route = Screen.JobDetails.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = {
                            jobDetailsViewModel.setSource("")
                            navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back
                    )
                },
            ) { innerPadding ->
                JobDetailsScreen(
                    modifier = Modifier.padding(innerPadding),
                    jobDetailsViewModel = jobDetailsViewModel,
                    profileViewModel = profileViewModel
                ) { selectedOffer ->
                    offerViewModel.selectOffer(selectedOffer)
                    navController.navigate(Screen.OfferApply.route)
                }
            }
        }

        composable(route = Screen.OfferApply.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_close,
                        title = stringResource(R.string.apply)
                    )
                },
            ) { innerPadding ->
                OfferApplyScreen(
                    modifier = Modifier.padding(innerPadding),
                    offer = offerViewModel.selectedOffer.value
                ) {
                    navController.popBackStack()
                    navController.popBackStack()
                }
            }
        }


        composable(route = Screen.SavedJobs.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.saved_jobs)
                    )
                },
                bottomBar = { StudentBottomNavigationBar(navController = navController) }
            ) { innerPadding ->
                SavedJobsScreen(
                    modifier = Modifier.padding(innerPadding),
                    jobDetailsViewModel = jobDetailsViewModel,
                    onFilterClick = {
                        navController.navigate(Screen.Filter.route)
                    },
                    filterViewModel = filterViewModel,
                    onOfferCardClick = {
                        navController.navigate(Screen.JobDetails.route)
                    },
                ) {
                    navController.navigate(Screen.StudentHome.route) {
                        popUpTo(Screen.Search.route) { inclusive = true }
                    }
                }
            }

        }

        composable(route = Screen.StudentApplications.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.applications)
                    )
                },
                bottomBar = { StudentBottomNavigationBar(navController = navController) }
            ) { innerPadding ->
                StudentApplicationsScreen(
                    modifier = Modifier.padding(innerPadding),
                    applicationViewModel = applicationViewModel,
                    onFilterClick = {
                        navController.navigate(Screen.Filter.route)
                    },
                    onNavigate = {
                        navController.navigate(Screen.StudentApplicationStatus.route) {
                        }
                    }
                )
            }
        }

        composable(route = Screen.RecruiterApplications.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.applications)
                    )
                },
                bottomBar = { RecruiterBottomNavigationBar(navController = navController) }
            ) { innerPadding ->
                RecruiterApplicationsScreen(
                    modifier = Modifier.padding(innerPadding),
                    applicationViewModel = applicationViewModel,
                    onFilterClick = {
                        navController.navigate(Screen.Filter.route)
                    },
                    onNavigate = {
                        navController.navigate(Screen.RecruiterApplicationStatus.route)
                    }
                )
            }
        }

        composable(route = Screen.EstablishmentHome.route) {
            Scaffold(
                bottomBar = { EstablishmentBottomNavigationBar(navController = navController) }
            ) { innerPadding ->
                EstablishmentHomeScreen(
                    modifier = Modifier.padding(innerPadding),
                    applicationViewModel = applicationViewModel,
                    onFilterClick = {
                        navController.navigate(Screen.Filter.route)
                    },
                    profileViewModel = profileViewModel,
                    onNavigate = {
                        navController.navigate(Screen.EstablishmentApplicationStatus.route)
                    }
                )
            }
        }

        composable(route = Screen.Profile.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.profile)
                    )
                },
                bottomBar = { StudentBottomNavigationBar(navController = navController) }
            ) { innerPadding ->
                ProfileScreen(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController,
                    profileViewModel = profileViewModel
                )
            }
        }

        composable(route = Screen.Room.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.message)
                    )
                },
                bottomBar = {
                    getBottomBar(storageRepository.get(USER_ROLE)!!, navController)?.invoke()
                }

            ) { innerPadding ->
                RoomScreen(
                    modifier = Modifier.padding(innerPadding),
                    roomViewModel = roomViewModel
                ) {
                    navController.navigate(Screen.Messaging.route)
                }
            }
        }

        composable(route = Screen.Messaging.route) {
            MessagingScreen(
                roomViewModel = roomViewModel
            ) {
                navController.popBackStack()
            }
        }

        composable(route = Screen.StudentApplicationStatus.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.application_status)
                    )
                }
            ) { innerPadding ->
                StudentApplicationStatus(
                    modifier = Modifier.padding(innerPadding),
                    applicationViewModel = applicationViewModel,
                    profileViewModel = profileViewModel
                ) {
                    navController.popBackStack()
                }
            }
        }

        composable(route = Screen.RecruiterApplicationStatus.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.application_status)
                    )
                }
            ) { innerPadding ->
                RecruiterApplicationStatus(
                    modifier = Modifier.padding(innerPadding),
                    applicationViewModel = applicationViewModel,
                    profileViewModel = profileViewModel,
                    onMessageNavigation = {
                        roomViewModel.setRoom(it)
                        navController.navigate(Screen.Messaging.route)
                    },
                    onNavigate = {
                        navController.navigate(Screen.OrganizationInfo.route)
                    }
                ) {
                    navController.popBackStack()
                }
            }
        }

        composable(route = Screen.EstablishmentApplicationStatus.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.application_status)
                    )
                }
            ) { innerPadding ->
                EstablishmentApplicationStatus(
                    modifier = Modifier.padding(innerPadding),
                    applicationViewModel = applicationViewModel,
                    profileViewModel = profileViewModel,
                    onMessageNavigation = {
                        roomViewModel.setRoom(it)
                        navController.navigate(Screen.Messaging.route)
                    },
                    onOfferDetailsClick = {
                        jobDetailsViewModel.setOffer(it)
                        jobDetailsViewModel.setSource(Screen.EstablishmentApplicationStatus.route)
                        navController.navigate(Screen.JobDetails.route)
                    },
                    onCompanyDetailsClick = {
                        navController.navigate(Screen.OrganizationInfo.route)
                    },
                    onNavigate = {
                        navController.navigate(Screen.OrganizationInfo.route)
                    }
                ) {
                    navController.popBackStack()
                }
            }
        }

        composable(route = Screen.ProfileDetails.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.profile_details)
                    )
                }
            ) { innerPadding ->
                ProfileDetailsScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = profileViewModel
                ) {
                    navController.popBackStack()
                }
            }
        }

        composable(route = Screen.OrganizationProfile.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = {
                            navController.popBackStack()
                        },
                        title = stringResource(R.string.profile)
                    )
                },
                bottomBar = {
                    getBottomBar(storageRepository.get(USER_ROLE)!!, navController)?.invoke()
                }
            ) { innerPadding ->
                OrganizationProfileScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = profileViewModel
                )
            }
        }

        composable(route = Screen.ContactInformation.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.contact_information)
                    )
                }
            ) { innerPadding ->
                ContactInformationScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = profileViewModel
                ) {
                    navController.popBackStack()
                }
            }
        }

        composable(route = Screen.Summary.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.summary)
                    )
                }
            ) { innerPadding ->
                SummaryScreen(
                    modifier = Modifier.padding(innerPadding),
                    profileViewModel = profileViewModel
                ) {
                    navController.popBackStack()
                }
            }
        }

        composable(route = Screen.WorkExperience.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.work_experience),
                        trailingIcon = if (workExperienceViewModel.getWorkExperience() != null) R.drawable.ic_delete else -1,
                        onTrailingIconClick = {
                            workExperienceViewModel.setDeleteWorkExperience()
                        }
                    )
                }
            ) { innerPadding ->
                WorkExperienceScreen(
                    modifier = Modifier.padding(innerPadding),
                    workExperienceViewModel = workExperienceViewModel
                ) {
                    navController.popBackStack()
                }
            }
        }

        composable(route = Screen.Education.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.education_),
                        trailingIcon = if (educationViewModel.getEducation() != null) R.drawable.ic_delete else -1,
                        onTrailingIconClick = {
                            educationViewModel.setDeleteEducation()
                        }
                    )
                }
            ) { innerPadding ->
                EducationScreen(
                    modifier = Modifier.padding(innerPadding),
                    educationViewModel = educationViewModel
                ) {
                    navController.popBackStack()
                }
            }
        }

        composable(route = Screen.Project.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.projects),
                        trailingIcon = if (projectViewModel.getProject() != null) R.drawable.ic_delete else -1,
                        onTrailingIconClick = {
                            projectViewModel.setDeleteProject()
                        }
                    )
                }
            ) { innerPadding ->
                ProjectScreen(
                    modifier = Modifier.padding(innerPadding),
                    projectViewModel = projectViewModel
                ) {
                    navController.popBackStack()
                }
            }
        }

        composable(route = Screen.Certification.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.certifications),
                        trailingIcon = if (certificationViewModel.getCertification() != null) R.drawable.ic_delete else -1,
                        onTrailingIconClick = {
                            certificationViewModel.setDeleteCertification()
                        }
                    )
                }
            ) { innerPadding ->
                CertificationScreen(
                    modifier = Modifier.padding(innerPadding),
                    certificationViewModel = certificationViewModel
                ) {
                    navController.popBackStack()
                }
            }
        }

        composable(route = Screen.Internship.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.internships),
                        trailingIcon = if (internshipViewModel.getInternship() != null) R.drawable.ic_delete else -1,
                        onTrailingIconClick = {
                            internshipViewModel.setDeleteInternship()
                        }
                    )
                }
            ) { innerPadding ->
                InternshipScreen(
                    modifier = Modifier.padding(innerPadding),
                    internshipViewModel = internshipViewModel
                ) {
                    navController.popBackStack()
                }
            }
        }

        composable(route = Screen.Language.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.languages),
                        trailingIcon = if (languageViewModel.getLanguage() != null) R.drawable.ic_delete else -1,
                        onTrailingIconClick = {
                            languageViewModel.setDeleteLanguage()
                        }
                    )
                }
            ) { innerPadding ->
                LanguageScreen(
                    modifier = Modifier.padding(innerPadding),
                    languageViewModel = languageViewModel
                ) {
                    navController.popBackStack()
                }
            }
        }

        composable(route = Screen.Skill.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.skills)
                    )
                }
            ) { innerPadding ->
                SkillScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = profileViewModel
                ) {
                    navController.popBackStack()
                }
            }
        }

        composable(route = Screen.CvResume.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.cv_resume)
                    )
                }
            ) { innerPadding ->
                CvResumeScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = profileViewModel
                ) {
                    navController.popBackStack()
                }
            }
        }

        composable(route = Screen.AddOffer.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_close,
                        title = stringResource(R.string.add_a_job_offer)
                    )
                },
            ) { innerPadding ->
                AddOfferScreen(modifier = Modifier.padding(innerPadding),
                    profileViewModel = profileViewModel,
                    jobDetailsViewModel = jobDetailsViewModel) {
                    navController.popBackStack()
                }
            }
        }

        composable(route = Screen.OrganizationInfo.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = if (storageRepository.get(USER_ROLE) == ROLE.RECRUITER.name) stringResource(R.string.company) else stringResource(R.string.establishment)
                    )
                },
                bottomBar = {
                    if (!profileViewModel.showRecruiterData.value!!){
                        getBottomBar(storageRepository.get(USER_ROLE)!!, navController)?.invoke()
                    }
                }
            ) { innerPadding ->
                OrganizationInfoScreen(modifier = Modifier.padding(innerPadding),
                    viewModel = profileViewModel)
            }
        }

        composable(route = Screen.CertificationDetails.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.certifications)
                    )
                }
            ) {innerPadding ->
                CertificationDetailsScreen(
                    modifier = Modifier.padding(innerPadding),
                    certificationViewModel = certificationViewModel,
                    profileViewModel = profileViewModel
                ) {
                    navController.navigate(Screen.Certification.route)
                }
            }
        }

        composable(route = Screen.EducationDetails.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.education_)
                    )
                }
            ) { innerPadding ->
                EducationDetailsScreen(
                    modifier = Modifier.padding(innerPadding),
                    educationViewModel = educationViewModel,
                    profileViewModel = profileViewModel
                ) {
                    navController.navigate(Screen.Education.route)
                }
            }
        }

        composable(route = Screen.InternshipDetails.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.internships)
                    )
                }
            ) { innerPadding ->
                InternshipDetailsScreen(
                    modifier = Modifier.padding(innerPadding),
                    internshipViewModel = internshipViewModel,
                    profileViewModel = profileViewModel
                ) {
                    navController.navigate(Screen.Internship.route)
                }
            }
        }

        composable(route = Screen.LanguageDetails.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.languages)
                    )
                }
            ) { innerPadding ->
                LanguageDetailsScreen(
                    modifier = Modifier.padding(innerPadding),
                    languageViewModel = languageViewModel,
                    profileViewModel = profileViewModel
                ) {
                    navController.navigate(Screen.Language.route)
                }
            }
        }

        composable(route = Screen.ProjectDetails.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.projects)
                    )
                }
            ) { innerPadding ->
                ProjectDetailsScreen(
                    modifier = Modifier.padding(innerPadding),
                    projectViewModel = projectViewModel,
                    profileViewModel = profileViewModel
                ) {
                    navController.navigate(Screen.Project.route)
                }
            }
        }

        composable(route = Screen.WorkExperienceDetails.route) {
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = { navController.popBackStack() },
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = stringResource(R.string.work_experience)
                    )
                }
            ) { innerPadding ->
                WorkExperienceDetailsScreen(
                    modifier = Modifier.padding(innerPadding),
                    workExperienceViewModel = workExperienceViewModel,
                    profileViewModel = profileViewModel
                ) {
                    navController.navigate(Screen.WorkExperience.route)
                }
            }
        }
    }

}
@Composable
fun getBottomBar(role: String, navController: NavController): (@Composable () -> Unit)? = when (role) {
    ROLE.STUDENT.name -> { { StudentBottomNavigationBar(navController = navController) } }
    ROLE.RECRUITER.name -> { { RecruiterBottomNavigationBar(navController = navController) } }
    ROLE.ESTABLISHMENT.name ->  { { EstablishmentBottomNavigationBar(navController = navController) }}
    else -> null
}
