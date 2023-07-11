package com.example.loginandsignupapp.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.loginandsignupapp.R
import com.example.loginandsignupapp.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    lateinit var welcomeBind: FragmentWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        welcomeBind = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false)
        welcomeBind.loginBtn.setOnClickListener { view : View -> view.findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment) }
        welcomeBind.signupBtn.setOnClickListener { view : View -> view.findNavController().navigate(
            R.id.action_welcomeFragment_to_signUpFragment
        ) }
        return welcomeBind.root
    }
}