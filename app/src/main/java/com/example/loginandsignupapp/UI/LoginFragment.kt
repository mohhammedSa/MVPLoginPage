package com.example.loginandsignupapp.UI

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.loginandsignupapp.Database.SQLiteDatabase
import com.example.loginandsignupapp.Model.LoginData
import com.example.loginandsignupapp.R
import com.example.loginandsignupapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    lateinit var loginBind: FragmentLoginBinding
    lateinit var db: SQLiteDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginBind = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        db = SQLiteDatabase(requireContext())
        val context = requireContext()
        loginBind.loginBtn.setOnClickListener { view: View ->
            val email = loginBind.loginEmailET.text.toString()
            val password = loginBind.loginPassEt.text.toString()
            val userInfo = LoginData(email, password)
            val result = db.loginValidation(userInfo)
            if (result) {
                Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show()
                view.findNavController().navigate(R.id.action_loginFragment_to_userPage)
            } else {
                Toast.makeText(context, "Email or password is wrong!", Toast.LENGTH_SHORT).show()
            }
        }
        return loginBind.root
    }
}