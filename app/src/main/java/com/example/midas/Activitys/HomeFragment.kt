package com.example.midas.Activitys

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.midas.Utils.PrefferenceStorage
import com.example.midas.ViewModels.HomeFragmentViewModel
import com.example.midas.databinding.FragmentFirstBinding
import com.example.midas.room.UserInfo

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    lateinit var homeFragmentViewModel : HomeFragmentViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeFragmentViewModel = ViewModelProvider(this)[HomeFragmentViewModel::class.java]

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeFragmentViewModel.liveUser.observe(viewLifecycleOwner, Observer<UserInfo?> { newName ->
            updateDataSet(newName)
        })

        val id : Int? = PrefferenceStorage.getInstance(requireContext()).getCurrentUser()
        if(id!=null)
            homeFragmentViewModel.getUser(id)

    }





    fun updateDataSet(userInfo: UserInfo?){
        if(userInfo!=null){
            binding?.dataset?.visibility = View.VISIBLE
            binding?.progressBar?.visibility = View.GONE
            binding?.email?.setText(userInfo.userEmail)
            binding?.mobile?.setText(userInfo.userMobileNo)
            binding?.name?.setText(userInfo.userName)
            binding?.userId?.setText(userInfo.userId.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}