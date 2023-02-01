/*
package com.example.movieapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.movieapp.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {
    private lateinit var fragmentBinding: FragmentOnboardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        fragmentBinding=binding
        val view = binding.root;
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding.buttonNextWel.setOnClickListener{
            val fragment = Movie1()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.nav_container, fragment)?.commit()
        }
    }
}*/
