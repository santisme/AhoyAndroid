package io.santisme.ahoy.sources.login.signin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import io.santisme.ahoy.R
import io.santisme.ahoy.domain.models.SignInModel
import kotlinx.android.synthetic.main.fragment_sign_in.*

const val SIGN_IN_FRAGMENT_TAG = "SIGN_IN_FRAGMENT"

class SignInFragment : Fragment() {

    private lateinit var delegate: SignInFragmentDelegate
    private val signInModel: SignInModel
        get() = SignInModel(
            username = inputUsername?.text?.toString() ?: "",
            password = inputPassword?.text?.toString() ?: ""
        )

    companion object {
        fun newInstance() =
            SignInFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SignInFragmentDelegate) {
            delegate = context
        } else {
            throw RuntimeException("$context must implement ${SignInFragmentDelegate::class.java.simpleName}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSignIn.setOnClickListener {
            delegate.signInClicked(signInModel = signInModel)
        }

        labelSignUp.setOnClickListener {
            delegate.signUpClicked()
        }

        labelPasswordRecovery.setOnClickListener {
            delegate.passwordRecoveryClicked()
        }

    }

}

interface SignInFragmentDelegate {
    fun signInClicked(signInModel: SignInModel)
    fun signUpClicked()
    fun passwordRecoveryClicked()
    fun usernameEditingDidEnd(sender: AppCompatEditText)
    fun passwordEditingDidEnd(sender: AppCompatEditText)
}