package com.example.videogames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputUsername: EditText = view.findViewById(R.id.inputUsername)
        val inputPassword: EditText = view.findViewById(R.id.inputPassword)

        val loginBundle = Bundle()
        loginBundle.putString("Username", inputUsername.text.toString())

        view.findViewById<Button>(R.id.buttonLogin).setOnClickListener (
            Navigation.createNavigateOnClickListener(
                R.id.action_loginFragment_to_videogameFragment,
                loginBundle
            )
        )

        view.findViewById<Button>(R.id.buttonRegister).setOnClickListener (
            Navigation.createNavigateOnClickListener(
                R.id.action_login_to_registerUser,
                loginBundle
            )
        )
    }
}