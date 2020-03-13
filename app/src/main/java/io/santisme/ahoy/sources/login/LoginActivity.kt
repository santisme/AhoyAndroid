package io.santisme.ahoy.sources.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.snackbar.Snackbar
import io.santisme.ahoy.R
import io.santisme.ahoy.domain.models.PasswordRecoveryModel
import io.santisme.ahoy.domain.models.SignInModel
import io.santisme.ahoy.domain.models.SignUpModelWrapper
import io.santisme.ahoy.sources.login.passwordrecovery.PASSWORD_RECOVERY_FRAGMENT_TAG
import io.santisme.ahoy.sources.login.passwordrecovery.PasswordRecoveryFragment
import io.santisme.ahoy.sources.login.passwordrecovery.PasswordRecoveryFragmentDelegate
import io.santisme.ahoy.sources.login.signin.SIGN_IN_FRAGMENT_TAG
import io.santisme.ahoy.sources.login.signin.SignInFragment
import io.santisme.ahoy.sources.login.signin.SignInFragmentDelegate
import io.santisme.ahoy.sources.login.signup.SIGN_UP_FRAGMENT_TAG
import io.santisme.ahoy.sources.login.signup.SignUpFragment
import io.santisme.ahoy.sources.login.signup.SignUpFragmentDelegate
import io.santisme.ahoy.sources.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginActivityModelViewProtocol,
    SignInFragmentDelegate, SignUpFragmentDelegate, PasswordRecoveryFragmentDelegate {

    private val delegate: SignInViewControllerDelegate by lazy {
        LoginActivityModelView(
            view = this,
            context = applicationContext
        )
    }

    private val signInFragment: SignInFragment by lazy { SignInFragment.newInstance() }
    private val signUpFragment: SignUpFragment by lazy { SignUpFragment.newInstance() }
    private val passwordRecoveryFragment: PasswordRecoveryFragment by lazy { PasswordRecoveryFragment.newInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (savedInstanceState == null) {
            delegate.onViewCreatedWithNoSavedData()
        }

    }

    override fun launchMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun navigateToSignIn() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, signInFragment, SIGN_IN_FRAGMENT_TAG)
            .commit()
    }

    override fun navigateToSignUp() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, signUpFragment, SIGN_UP_FRAGMENT_TAG)
            .commit()
    }

    override fun showError(message: String) {
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG).show()
    }

    override fun showSuccess(message: String) {
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG).show()
    }

    override fun navigateToPasswordRecovery() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragmentContainer,
                passwordRecoveryFragment,
                PASSWORD_RECOVERY_FRAGMENT_TAG
            )
            .commit()
    }

    // MARK: - SignInFragmentDelegate implementation
    override fun signInClicked(signInModel: SignInModel) {
        delegate.signInClicked(signInModel = signInModel)
    }

    override fun signUpClicked() {
        delegate.navigateToSignUp()
    }

    override fun passwordRecoveryClicked() {
        delegate.navigateToPasswordRecovery()
    }

    override fun usernameEditingDidEnd(sender: AppCompatEditText) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun passwordEditingDidEnd(sender: AppCompatEditText) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // MARK: - SignUpFragmentDelegate implementation
    override fun repeatedPasswordEditingDidEnd(
        passwordTextField: AppCompatEditText,
        sender: AppCompatEditText
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun emailEditingDidEnd(sender: AppCompatEditText) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun signUpClicked(signUpModelWrapper: SignUpModelWrapper) {
        delegate.signUpClicked(signUpModelWrapper = signUpModelWrapper)
    }

    override fun signInClicked() {
        delegate.signInClicked()
    }

    override fun buttonPasswordRecovery(passwordRecoveryModel: PasswordRecoveryModel) {
        delegate.buttonPasswordRecovery(passwordRecoveryModel = passwordRecoveryModel)
    }

    override fun cancelClicked() {
        delegate.cancelClicked()
    }
}

interface SignInViewControllerDelegate {
    fun onViewCreatedWithNoSavedData()
    fun signInClicked(signInModel: SignInModel)
    fun navigateToSignUp()
    fun signInClicked()
    fun signUpClicked(signUpModelWrapper: SignUpModelWrapper)
    fun buttonPasswordRecovery(passwordRecoveryModel: PasswordRecoveryModel)
    fun cancelClicked()
    fun navigateToPasswordRecovery()
}