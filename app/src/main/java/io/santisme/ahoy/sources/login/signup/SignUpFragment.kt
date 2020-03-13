package io.santisme.ahoy.sources.login.signup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import io.santisme.ahoy.R
import io.santisme.ahoy.domain.models.local.SignUpModel
import io.santisme.ahoy.domain.models.local.SignUpModelWrapper
import kotlinx.android.synthetic.main.fragment_sign_up.*

const val SIGN_UP_FRAGMENT_TAG = "SIGN_UP_FRAGMENT"

class SignUpFragment : Fragment() {

    private lateinit var delegate: SignUpFragmentDelegate
    private val signUpModelWrapper: SignUpModelWrapper
        get() = SignUpModelWrapper(
            SignUpModel(
                name = inputName?.text?.toString() ?: "",
                username = inputUsername?.text?.toString() ?: "",
                email = inputEmail?.text?.toString() ?: "",
                password = inputPassword?.text?.toString() ?: ""
            ),
            repeatedPassword = inputRepeatedPassword?.text?.toString() ?: ""
        )

    companion object {
        fun newInstance() =
            SignUpFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SignUpFragmentDelegate) {
            delegate = context
        } else {
            throw RuntimeException("$context must implement ${SignUpFragmentDelegate::class.java.simpleName}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSignUp.setOnClickListener {
            delegate.signUpClicked(signUpModelWrapper = signUpModelWrapper)
        }

        labelSignIn.setOnClickListener {
            delegate.signInClicked()
        }

        labelPasswordRecovery.setOnClickListener {
            delegate.passwordRecoveryClicked()
        }
    }

}

interface SignUpFragmentDelegate {
    fun usernameEditingDidEnd(sender: AppCompatEditText)
    fun passwordEditingDidEnd(sender: AppCompatEditText)
    fun repeatedPasswordEditingDidEnd(
        passwordTextField: AppCompatEditText,
        sender: AppCompatEditText
    )

    fun emailEditingDidEnd(sender: AppCompatEditText)
    fun signUpClicked(signUpModelWrapper: SignUpModelWrapper)
    fun signInClicked()
    fun passwordRecoveryClicked()
}