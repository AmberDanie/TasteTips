package pet.project.tastetips.data.states

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)