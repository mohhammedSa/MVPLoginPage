package com.example.loginandsignupapp.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.loginandsignupapp.R
import com.example.loginandsignupapp.databinding.FragmentUserPageBinding


class UserPage : Fragment() {
    lateinit var userPageBinding: FragmentUserPageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userPageBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_page, container, false)
        return userPageBinding.root
    }
}