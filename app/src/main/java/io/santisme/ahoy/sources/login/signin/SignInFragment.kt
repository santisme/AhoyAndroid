package io.santisme.ahoy.sources.login.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.santisme.ahoy.R

const val SIGN_IN_FRAGMENT_TAG = "SIGN_IN_FRAGMENT"

class SignInFragment : Fragment() {

    companion object {
        fun newInstance() =
            SignInFragment()
    }

    private lateinit var modelView: SignInModelView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        modelView = SignInModelView()


    }

}
