package com.example.swiftlylist.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.swiftlylist.BR
import com.example.swiftlylist.R
import com.example.swiftlylist.data.ManagerSpecial
import com.example.swiftlylist.databinding.SpecialsFragmentBinding
import com.example.swiftlylist.ui.main.util.BaseFragment
import com.example.swiftlylist.ui.main.util.RecyclerViewAdapter
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SpecialsFragment : BaseFragment() {

    private val viewModel: SpecialsViewModel by viewModels()
    private lateinit var binding : SpecialsFragmentBinding

    companion object {
        fun newInstance() = SpecialsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = SpecialsFragmentBinding.inflate(inflater, container, false)
        setupObservers()
        binding.productList.layoutManager = FlexboxLayoutManager(context).also {
            it.justifyContent = JustifyContent.SPACE_EVENLY
        }
        binding.productList.adapter = RecyclerViewAdapter<ManagerSpecial>(
            {R.layout.specials_item},
            BR.item,
            BR.canvasUnit)
        return binding.root
    }

    private fun setupObservers() {
        viewModel.getProducts.observe(viewLifecycleOwner, Observer {
            if(it.isSuccessful){
                binding.specials = it.body()
            }else{
                Toast.makeText(this.context,"Failed to get specials", Toast.LENGTH_LONG).show()
            }
        })
    }
}