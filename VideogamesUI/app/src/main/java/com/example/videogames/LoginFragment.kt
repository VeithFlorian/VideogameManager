package com.example.videogames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.videogames.rest.ApiCaller
import com.example.videogames.rest.GameDbApi
import com.example.videogames.rest.User

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

        view.findViewById<Button>(R.id.buttonLogin).setOnClickListener {
            loginUser(
                view,
                inputUsername.text.toString(),
                inputPassword.text.toString(),
            )
        }


        view.findViewById<Button>(R.id.buttonRegister).setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.action_login_to_registerUser
            )
        )
    }

    private fun loginUser(view: View, username: String, password: String) {
        ApiCaller<User>().call(GameDbApi().gameApiService.loginUser(username, password),
            onFailure = {
                Toast.makeText(context, "Failed to login", Toast.LENGTH_SHORT).show()
            },
            onSuccess = {
                if (it.body() != null) {
                    val bundle = Bundle()
                    bundle.putInt("userId", it.body()!!.id)

                    Navigation.findNavController(view)
                        .navigate(R.id.action_loginFragment_to_videogameFragment, bundle)
                } else {
                    Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}