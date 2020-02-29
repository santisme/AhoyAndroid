package io.santisme.ahoy.sources.login.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.santisme.ahoy.R
import io.santisme.ahoy.domain.models.SignUpModel

const val SIGN_UP_FRAGMENT_TAG = "SIGN_UP_FRAGMENT"

class SignUpFragment: Fragment() {

    companion object {
        fun newInstance() =
            SignUpFragment()
    }

    private lateinit var modelView: SignUpModelView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        modelView = SignUpModelView()


    }
}
