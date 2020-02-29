package io.santisme.ahoy.sources.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.santisme.ahoy.R
import io.santisme.ahoy.sources.login.signin.SIGN_IN_FRAGMENT_TAG
import io.santisme.ahoy.sources.login.signin.SignInFragment
import io.santisme.ahoy.sources.login.signup.SIGN_UP_FRAGMENT_TAG
import io.santisme.ahoy.sources.login.signup.SignUpFragment

class LoginActivity : AppCompatActivity(), LoginActivityModelViewProtocol {

    private val delegate: SignInViewControllerDelegate by lazy { LoginActivityModelView(view = this) }
    private val signInFragment: SignInFragment by lazy { SignInFragment.newInstance() }
    private val signUpFragment: SignUpFragment by lazy { SignUpFragment.newInstance() }
//    private val passwordRecoveryFragment: PasswordRecoveryFragment by lazy { PasswordRecoveryFragment.newInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (savedInstanceState == null) {
            delegate.onViewCreatedWithNoSavedData()
        }

    }

    override fun launchTopicsActivity() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun navigateToSignIn() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, signInFragment, SIGN_IN_FRAGMENT_TAG)
            .commit()
    }
}

interface SignInViewControllerDelegate {
    fun onViewCreatedWithNoSavedData()
}