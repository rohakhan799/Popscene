package com.example.movieapp.ui.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentAccountBinding
import com.example.movieapp.model.UserManager
import com.example.movieapp.ui.activity.LoginActivity

class AccountFragment : Fragment() {
    lateinit var bindingData: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_account, container, false)
        bindingData = FragmentAccountBinding.inflate(inflater, container, false)

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity!!.finish()
                }
            })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.username_logout).text =
            UserManager.userObj?.userName.toString()
        view.findViewById<TextView>(R.id.email_logout).text =
            UserManager.userObj?.userEmail.toString()
        view.findViewById<TextView>(R.id.gender_logout).text =
            UserManager.userObj?.gender.toString()
        view.findViewById<TextView>(R.id.dob_logout).text =
            UserManager.userObj?.dateOfBirth.toString()

        if ((UserManager.userObj?.gender == "Female") || ((UserManager.userObj?.gender == "female"))) {
            view.findViewById<ImageView>(R.id.avatar).setImageResource(R.drawable.avatargirlavatar)
        } else {
            view.findViewById<ImageView>(R.id.avatar).setImageResource(R.drawable.avatarboyavatar)
        }

        view.findViewById<ImageView>(R.id.logout).setOnClickListener {
            showPopup()
        }
    }

    fun showPopup() {
        val builder = AlertDialog.Builder(requireContext())
        lateinit var dialog: AlertDialog
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.logout_popup, null)
        dialogView?.visibility = View.VISIBLE
        builder.setView(dialogView)

        dialogView?.findViewById<Button>(R.id.yes_logout)?.setOnClickListener {
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            builder.create().dismiss()
        }

        dialogView?.findViewById<Button>(R.id.no_logout)?.setOnClickListener {
            dialog.dismiss()
        }
        dialog = builder.create()
        dialog.show()
    }
}