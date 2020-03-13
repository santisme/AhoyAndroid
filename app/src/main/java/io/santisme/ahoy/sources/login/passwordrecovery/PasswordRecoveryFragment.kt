package io.santisme.ahoy.sources.login.passwordrecovery

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.santisme.ahoy.R
import io.santisme.ahoy.domain.models.local.PasswordRecoveryModel
import kotlinx.android.synthetic.main.fragment_password_recovery.*

const val PASSWORD_RECOVERY_FRAGMENT_TAG = "PASSWORD_RECOVERY_FRAGMENT_TAG"

class PasswordRecoveryFragment : Fragment() {
    private lateinit var delegate: PasswordRecoveryFragmentDelegate
    private val passwordRecoveryModel: PasswordRecoveryModel
        get() = PasswordRecoveryModel(
            login = inputLogin?.text?.toString() ?: ""
        )

    companion object {
        fun newInstance() =
            PasswordRecoveryFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is PasswordRecoveryFragmentDelegate) {
            delegate = context
        } else {
            throw RuntimeException("$context must implement ${PasswordRecoveryFragmentDelegate::class.java.simpleName}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_password_recovery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonPasswordRecovery.setOnClickListener {
            delegate.buttonPasswordRecovery(passwordRecoveryModel = passwordRecoveryModel)
        }

        buttonCancel.setOnClickListener {
            delegate.cancelClicked()
        }

    }

}

interface PasswordRecoveryFragmentDelegate {
    fun buttonPasswordRecovery(passwordRecoveryModel: PasswordRecoveryModel)
    fun cancelClicked()
}