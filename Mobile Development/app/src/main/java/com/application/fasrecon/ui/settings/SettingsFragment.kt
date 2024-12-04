package com.application.fasrecon.ui.settings

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.application.fasrecon.R
import com.application.fasrecon.databinding.FragmentSettingsBinding
import com.application.fasrecon.ui.languagesettings.LanguageSettingsActivity
import com.application.fasrecon.ui.login.LoginActivity
import com.application.fasrecon.ui.profile.ProfileSettingsActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBar()
        setUserProfile()

        binding.profileSettings.setOnClickListener {
            val intent = Intent(requireContext(), ProfileSettingsActivity::class.java)
            startActivity(intent)
        }

        binding.languageSettings.setOnClickListener {
            val intent = Intent(requireContext(), LanguageSettingsActivity::class.java)
            startActivity(intent)
        }

        binding.LogoutMenu.setOnClickListener {
            showAlertDialog()
        }

        binding.logoutIcon.setOnClickListener {
            showAlertDialog()
        }
    }

    private fun setActionBar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.topAppBar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.profile)
    }

    private fun setUserProfile() {
        binding.UserName.text = auth.currentUser?.displayName
        binding.userEmail.text = auth.currentUser?.email
        Glide.with(this)
            .load(auth.currentUser?.photoUrl)
            .into(binding.UserProfile)
    }

    private fun showAlertDialog() {

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.logout_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.btn_logout).setOnClickListener {
            auth.signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

        dialog.show()
    }
}