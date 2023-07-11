package com.example.loginandsignupapp.UI

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.loginandsignupapp.Database.SQLiteDatabase
import com.example.loginandsignupapp.Model.SignUpData
import com.example.loginandsignupapp.R
import com.example.loginandsignupapp.databinding.FragmentSignUpBinding
import java.util.regex.Pattern

class SignUpFragment : Fragment() {
    lateinit var signUpBind: FragmentSignUpBinding
    lateinit var db: SQLiteDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        signUpBind = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        val context = requireContext()
        db = SQLiteDatabase(context)

        signUpBind.signupNameEt.addTextChangedListener {
            val name = signUpBind.signupNameEt.text.toString()
            val result = db.verifyName(name)
            if (result) signUpBind.signupNameEt.error = "Choose another name"
        }
        signUpBind.signupPassET.addTextChangedListener {
            val password = signUpBind.signupPassET.text.toString()
            if (isPasswordComplex(password))
                Toast.makeText(context, "Strong Password!", Toast.LENGTH_SHORT).show()
            else signUpBind.signupPassET.error = "Weak Password!"
        }
        signUpBind.confirmPassET.addTextChangedListener {
            val password = signUpBind.signupPassET.text.toString()
            val confirmation = signUpBind.confirmPassET.text.toString()
            if (password == confirmation) {
                Toast.makeText(context, "Password matches!", Toast.LENGTH_SHORT).show()
            } else signUpBind.confirmPassET.error = "Password doesn't match"
        }
        signUpBind.signupEmailET.addTextChangedListener {
            val email = signUpBind.signupEmailET.text.toString()
            if (!isValidEmail(email)) signUpBind.signupEmailET.error = "Write an Email!"
        }

        signUpBind.signUpSubmitBtn.setOnClickListener { view: View ->
            if (signUpBind.signupNameEt.text.isNotEmpty() &&
                signUpBind.signupEmailET.text.isNotEmpty() &&
                signUpBind.signupPassET.text.isNotEmpty() &&
                signUpBind.confirmPassET.text.isNotEmpty()
            ) {
                val name: String = signUpBind.signupNameEt.text.toString()
                val email: String = signUpBind.signupEmailET.text.toString()
                val password: String = signUpBind.signupPassET.text.toString()
                val confirmPassword: String = signUpBind.confirmPassET.text.toString()
                if (isValidEmail(email)) {
                    val userInfo = SignUpData(0, name, email, password, confirmPassword)
                    db.addUSer(userInfo)
                    view.findNavController().navigate(R.id.action_signUpFragment_to_userPage)
                }
            } else {
                if (signUpBind.signupNameEt.text.isEmpty()) signUpBind.signupNameEt.error =
                    "Required Field"
                else if (signUpBind.signupEmailET.text.isEmpty()) signUpBind.signupEmailET.error =
                    "Required Field"
                else if (signUpBind.signupPassET.text.isEmpty()) signUpBind.signupPassET.error =
                    "Required Field"
                else if (signUpBind.confirmPassET.text.isEmpty()) signUpBind.confirmPassET.error =
                    "Required Field"
            }
        }
        return signUpBind.root
    }

    private fun isPasswordComplex(password: String): Boolean {
        val passwordPattern =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$".toRegex()
        return passwordPattern.matches(password)
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern: Pattern = Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        )
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
}