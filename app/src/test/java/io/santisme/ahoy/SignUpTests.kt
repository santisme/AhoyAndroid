package io.santisme.ahoy

import io.santisme.ahoy.domain.models.local.SignUpModel
import io.santisme.ahoy.domain.models.local.SignUpModelWrapper
import io.santisme.ahoy.sources.data.repositories.local.LocalRepository
import io.santisme.ahoy.sources.login.LoginActivityModelView
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertNull
import org.junit.After
import org.junit.Before
import org.junit.Test

class SignUpTests {

    private lateinit var dummySignUpModel: SignUpModelWrapper
    private lateinit var wrongSignUpModel: SignUpModelWrapper
    private lateinit var loginActivityModelView: LoginActivityModelView

    @Before
    fun setUp() {
        dummySignUpModel =
            SignUpModelWrapper(
                signUpModel = SignUpModel(
                    name = "dummyName",
                    email = "dummyEmail@email.com",
                    password = "1234567890",
                    username = "dummyUsername"
                ), repeatedPassword = "1234567890"
            )
        wrongSignUpModel =
            SignUpModelWrapper(
                signUpModel = SignUpModel(
                    name = "dummyName",
                    email = "dummyEmail.com",
                    password = "123",
                    username = ""
                ), repeatedPassword = "12"
            )
        val signUpRepository = LocalRepository ()
        loginActivityModelView = LoginActivityModelView(view = null)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testIfSignUpModel_isNotNull() {
        assertNotNull(dummySignUpModel)
        assertNotNull(wrongSignUpModel)
    }

    @Test
    fun testIfSignUpModelFields_AreValid() {
        assertNull(loginActivityModelView.signUpModelIsValid(signUpModelWrapper = dummySignUpModel))
    }

    @Test
    fun testIfSignUpModelFields_AreInValid() {
        assertNotNull(loginActivityModelView.signUpModelIsValid(signUpModelWrapper = wrongSignUpModel))
    }

    @Test
    fun testIfSignUpModelUsername_IsValid() {
        assertNull(loginActivityModelView.usernameIsValid(username = dummySignUpModel.signUpModel.username))
    }

    @Test
    fun testIfSignUpModelUsername_IsInValid() {
        assertNotNull(loginActivityModelView.usernameIsValid(username = wrongSignUpModel.signUpModel.username))
    }

    @Test
    fun testIfSignUpModelEmail_IsValid() {
        assertNull(loginActivityModelView.emailIsValid(email = dummySignUpModel.signUpModel.email))
    }

    @Test
    fun testIfSignUpModelEmail_IsInValid() {
        assertNotNull(loginActivityModelView.emailIsValid(email = wrongSignUpModel.signUpModel.email))
    }

    @Test
    fun testIfSignUpModelPassword_IsValid() {
        assertNull(loginActivityModelView.passwordIsValid(password = dummySignUpModel.signUpModel.password))
    }

    @Test
    fun testIfSignUpModelPassword_IsInValid() {
        assertNotNull(loginActivityModelView.passwordIsValid(password = wrongSignUpModel.signUpModel.password))
    }

    @Test
    fun testIfSignUpModelRepeatedPassword_IsValid() {
        assertNull(loginActivityModelView.repeatPasswordIsValid(password = dummySignUpModel.signUpModel.password, repeatedPassword = dummySignUpModel.repeatedPassword))
    }

    @Test
    fun testIfSignUpModelRepeatedPassword_IsInValid() {
        assertNotNull(loginActivityModelView.repeatPasswordIsValid(password = wrongSignUpModel.signUpModel.password, repeatedPassword = wrongSignUpModel.repeatedPassword))
    }
}
