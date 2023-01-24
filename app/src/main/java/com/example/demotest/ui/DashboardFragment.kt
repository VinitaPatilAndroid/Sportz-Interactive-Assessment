package com.example.demotest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.demotest.data.model.MatchDetailsResponse
import com.example.demotest.databinding.FragmentDashboardBinding
import com.example.demotest.ui.adapter.HighlightAdapter
import com.example.demotest.ui.viewmodel.MatchDetailsViewModel
import com.example.demotest.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val matchDetailsViewModel: MatchDetailsViewModel by activityViewModels()

    private lateinit var highlightAdapter: HighlightAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        binding.apply {
            buttonViewPlayerDetails.setOnClickListener {
                val action = DashboardFragmentDirections.actionDashboardFragmentToTeamDetailsFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun setupObservers() {
        matchDetailsViewModel.matchDetails.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.nestedScrollView.visibility = View.VISIBLE
                    loadMatchDetails(it.data)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.nestedScrollView.visibility = View.GONE
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.nestedScrollView.visibility = View.GONE
                }
            }
        }
    }

    private fun loadMatchDetails(matchDetails: MatchDetailsResponse?) {
        matchDetails?.let {
            binding.apply {
                textViewSeriesName.text = it.matchdetail.series.name

                textViewVenue.append(it.matchdetail.venue.name)

                textViewWeather.append(it.matchdetail.weather)

                textViewResult.text = it.matchdetail.result

                highlightAdapter = HighlightAdapter(list = it.nuggets)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}