package io.santisme.ahoy.sources.main.userdetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.santisme.ahoy.R
import kotlinx.android.synthetic.main.fragment_user_detail.*

class UserDetailFragment() : Fragment() {

    private lateinit var delegate: UserDetailFragmentDelegate

    companion object {
        fun newInstance() =
            UserDetailFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is UserDetailFragmentDelegate) {
            delegate = context
        } else {
            throw RuntimeException("$context must implement ${UserDetailFragmentDelegate::class.java.simpleName}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        delegate.requestLoggedUser()

        buttonLogOut.setOnClickListener{ onLogOutClicked() }
    }

    private fun onLogOutClicked() {
        delegate.onLogOutClicked()
    }

}

interface UserDetailFragmentDelegate {
    fun requestLoggedUser()
    fun onLogOutClicked()
}