package com.fcemtopall.patika_yemeksepeti_final.ui.order

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fcemtopall.patika_yemeksepeti_final.R
import com.fcemtopall.patika_yemeksepeti_final.databinding.FragmentOrderBinding
import com.fcemtopall.patika_yemeksepeti_final.utils.Resource
import com.fcemtopall.patika_yemeksepeti_final.utils.gone
import com.fcemtopall.patika_yemeksepeti_final.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment() {
    private var binding: FragmentOrderBinding? = null
    private val viewModel: OrderFragmentViewModel by viewModels()
    val adapter = OrderRecyclerViewAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getOrders()
    }

    private fun getOrders() {
        viewModel.getOrders().observe(viewLifecycleOwner, { response ->

            when (response.status) {
                Resource.Status.LOADING -> {
                    setLoading(true)
                }
                Resource.Status.SUCCESS -> {
                    response.data?.orderList?.let {
                        binding?.orderRecyclerView?.layoutManager = LinearLayoutManager(context)
                        binding?.orderRecyclerView?.adapter = adapter
                        adapter.setOrderList(it)
                        setLoading(false)
                    }

                }

                Resource.Status.ERROR -> {
                    println("${response.message}")
                    Log.v("order",response.toString())
                    setLoading(false)
                }
            }
        })
    }

    private fun setLoading(isLoading: Boolean) {
        if(isLoading)
        {
            binding?.orderProgressBar?.show()
            binding?.orderRecyclerView?.gone()
        }
        else
        {
            binding?.orderProgressBar?.gone()
            binding?.orderRecyclerView?.show()
        }
    }
}