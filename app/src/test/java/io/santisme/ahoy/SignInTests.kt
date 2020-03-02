package io.santisme.ahoy

import io.santisme.ahoy.domain.models.SignInModel
import io.santisme.ahoy.sources.login.LoginActivityModelView
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SignInTests {

    private lateinit var dummySignInModel: SignInModel
    private lateinit var wrongSignInModel: SignInModel
    private lateinit var loginActivityModelView: LoginActivityModelView

    @Before
    fun setUp() {
        dummySignInModel = SignInModel(username = "dummyUsername", password = "1234567890")
        wrongSignInModel = SignInModel(username = "", password = "123")
        loginActivityModelView = LoginActivityModelView(view = null)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testIfSignInModel_isNotNull() {
        assertNotNull(dummySignInModel)
        assertNotNull(wrongSignInModel)
    }

    @Test
    fun testIfSignInModelFields_AreValid() {
        assertNull(loginActivityModelView.signInModelIsValid(signInModel = dummySignInModel))
    }

    @Test
    fun testIfSignInModelFields_AreInValid() {
        assertNotNull(loginActivityModelView.signInModelIsValid(signInModel = wrongSignInModel))
    }

    @Test
    fun testIfSignInModelUsername_IsValid() {
        assertNull(loginActivityModelView.usernameIsValid(username = dummySignInModel.username))
    }

    @Test
    fun testIfSignInModelUsername_IsInValid() {
        assertNotNull(loginActivityModelView.usernameIsValid(username = wrongSignInModel.username))
    }

    @Test
    fun testIfSignInModelPassword_IsValid() {
        assertNull(loginActivityModelView.passwordIsValid(password = dummySignInModel.password))
    }

    @Test
    fun testIfSignInModelPassword_IsInValid() {
        assertNotNull(loginActivityModelView.passwordIsValid(password = wrongSignInModel.password))
    }
}
