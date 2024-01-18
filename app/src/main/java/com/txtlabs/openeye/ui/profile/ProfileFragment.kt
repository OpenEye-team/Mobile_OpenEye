package com.txtlabs.openeye.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.hawk.Hawk
import com.txtlabs.openeye.data.ResultState
import com.txtlabs.openeye.databinding.FragmentProfileBinding
import com.txtlabs.openeye.ui.auth.UserViewModel
import com.txtlabs.openeye.ui.auth.login.LoginActivity
import com.txtlabs.openeye.utils.ConfirmDialog

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = UserViewModel()

        logout()
        setData()
    }

    private fun setData(){
        userViewModel.getUser()
        userViewModel.userState.observe(viewLifecycleOwner) {
            when (it) {
                is ResultState.Success -> {
                    it.value.email?.let { it1 -> Log.d("user", it1) }
                    binding.tvProfileName.text = it.value.name
                    binding.tvEmail.text = it.value.email
                }
                is ResultState.Failure -> {
                    Log.d("user", it.throwable.toString())
                    binding.tvProfileName.text = "User"
                    binding.tvEmail.text = "User@"
                }
                is ResultState.Loading -> {
                    Log.d("user", "loading")
                    binding.tvProfileName.text = "Please wait..."
                    binding.tvEmail.text = "Please wait..."
                }
                else -> {}
            }
        }

        if (userViewModel.getUsername().toString() == "" || userViewModel.getUsername().toString() == "null" || userViewModel.getUsername().toString() == " ") {
            if (userViewModel.isUserSignedIn()) {
                val email = userViewModel.getEmail().toString()
                val index = email.indexOf("@")
                val name = email.substring(0, index)
                binding.tvProfileName.text = name
                binding.tvEmail.text = email
            } else {
                binding.tvProfileName.text = "User"
                binding.tvEmail.text = "User@"
            }
        } else {
            binding.tvProfileName.text = "User"
            binding.tvEmail.text = "User@"
        }
    }

    private fun logout(){
        binding.btnLogout.setOnClickListener {
            Log.d("user", "logout")
            val confirmDialog = ConfirmDialog()
            confirmDialog.title = "Keluar"
            confirmDialog.description = "Apakah anda yakin ingin keluar?"
            confirmDialog.positiveText = "Keluar"
            confirmDialog.negativeText = "Batal"
            confirmDialog.positiveCallback = {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                Hawk.deleteAll()
                Hawk.destroy()
                startActivity(intent)
            }
            confirmDialog.negativeCallback = {
                confirmDialog.dismiss()
            }
            confirmDialog.show((requireActivity()).supportFragmentManager, "confirm")
        }
    }
}