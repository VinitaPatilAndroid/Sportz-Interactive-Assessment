package com.example.demotest.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demotest.R
import com.example.demotest.data.model.Player
import com.example.demotest.databinding.FragmentTeamDetailsBinding
import com.example.demotest.ui.adapter.PlayerAdapter
import com.example.demotest.ui.viewmodel.MatchDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamDetailsFragment : Fragment() {
    private var _binding: FragmentTeamDetailsBinding? = null
    private val binding get() = _binding!!

    private val matchDetailsViewModel: MatchDetailsViewModel by activityViewModels()

    private lateinit var nzplayerAdapter: PlayerAdapter
    private lateinit var indPlayerAdapter:PlayerAdapter
    val playersNez = mutableListOf<Player>()
    val playersInd = mutableListOf<Player>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            nzplayerAdapter =PlayerAdapter(playersNez,
                onPlayerSelection = {
                    customDialog(it)
                }
            )
            indPlayerAdapter = PlayerAdapter(playersInd,
                onPlayerSelection = {
                    customDialog(it)
            })

            matchDetailsViewModel.teams.forEach { team ->
                if (team.teamName == "India") {
                    playersInd.addAll(team.players)
                    tvTeamName1.text = team.teamName

                } else if (team.teamName == "New Zealand") {
                    playersNez.addAll(team.players)
                    tvTeamName2.text = team.teamName
                }
            }
            val layoutManager = LinearLayoutManager(activity)
            rvNez.layoutManager = layoutManager
            rvNez.adapter = nzplayerAdapter
            nzplayerAdapter.notifyDataSetChanged()

            val layoutManagerInd = LinearLayoutManager(activity)
            rvIndia.layoutManager = layoutManagerInd
            rvIndia.adapter = indPlayerAdapter
            indPlayerAdapter.notifyDataSetChanged()

        }
    }

    private fun customDialog(it: Player) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.player_detail_dialog);
        dialog?.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        var tvBattingStyle = dialog.findViewById<TextView>(R.id.tv_batting_style)
        var tvBowlingStyle = dialog.findViewById<TextView>(R.id.tv_bowling_style)
        var btnok = dialog.findViewById<Button>(R.id.btn_dismiss)
        tvBattingStyle.text = "Batting Style : ${it.batting.style}"
        tvBowlingStyle.text = "Bowling Style :" + it.bowling.style
        btnok.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}