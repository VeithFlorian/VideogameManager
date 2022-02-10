package com.example.videogames

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.videogames.rest.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterUserFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputFirstname: TextView = view.findViewById(R.id.inputFirstname)
        val inputLastname: TextView = view.findViewById(R.id.inputLastname)
        val inputUsername: TextView = view.findViewById(R.id.inputUsername)
        val inputEmail: TextView = view.findViewById(R.id.inputEmail)
        val inputPassword: TextView = view.findViewById(R.id.inputPassword)

        view.findViewById<Button>(R.id.buttonRegister)
            .setOnClickListener {
                createUser(
                    view,
                    User(
                        0,
                        inputFirstname.text.toString(),
                        inputLastname.text.toString(),
                        inputUsername.text.toString(),
                        inputEmail.text.toString(),
                        inputPassword.text.toString()
                    )
                )

            }
    }

    private fun createUser(view: View, user: User) {
        ApiCaller<User>().call(GameDbApi().gameApiService.createUser(user),
            onFailure = {
                Toast.makeText(context, "Failed to create user", Toast.LENGTH_SHORT).show()
            },
            onSuccess = {
                Toast.makeText(
                    context,
                    "User ${it.body()!!.username} created",
                    Toast.LENGTH_SHORT
                ).show()
                Navigation.findNavController(view).navigate(R.id.action_registerUser_to_login)
            }
        )
    }
}