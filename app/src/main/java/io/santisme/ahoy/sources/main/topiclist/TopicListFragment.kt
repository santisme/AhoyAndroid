package io.santisme.ahoy.sources.main.topiclist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.santisme.ahoy.R

class TopicListFragment : Fragment() {

    private lateinit var delegate: TopicListFragmentDelegate
//    private val signInModel: SignInModel
//        get() = SignInModel(
//            username = inputUsername?.text?.toString() ?: "",
//            password = inputPassword?.text?.toString() ?: ""
//        )

    companion object {
        fun newInstance() =
            TopicListFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TopicListFragmentDelegate) {
            delegate = context
        } else {
            throw RuntimeException("$context must implement ${TopicListFragmentDelegate::class.java.simpleName}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_topic_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}

interface TopicListFragmentDelegate {

}