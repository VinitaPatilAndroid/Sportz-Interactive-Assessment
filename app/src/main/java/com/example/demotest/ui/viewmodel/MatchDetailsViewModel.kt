package com.example.demotest.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demotest.data.SiApi
import com.example.demotest.data.model.MatchDetailsResponse
import com.example.demotest.data.model.Player
import com.example.demotest.data.model.Team
import com.example.demotest.utils.Resource
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MatchDetailsViewModel @Inject constructor(
    private val siApi: SiApi
) : ViewModel() {

    private val _matchDetails = MutableLiveData<Resource<MatchDetailsResponse>>()
    val matchDetails: LiveData<Resource<MatchDetailsResponse>>
        get() = _matchDetails

    val teams = mutableListOf<Team>()

    init {
        getMatchDetails()
    }

    private fun getMatchDetails() = viewModelScope.launch {
        _matchDetails.postValue(Resource.Loading())
        try {
            val response = siApi.getMatchDetails()
            if (response.isSuccessful) {
                response.body()?.let { matchDetails ->
                    parseMatchDetails(matchDetails)
                }
            } else {
                _matchDetails.postValue(Resource.Error("Something went wrong"))
            }
        } catch (exception: Exception) {
            if (exception is IOException) {
                _matchDetails.postValue(Resource.Error("No internet connection"))
            } else {
                _matchDetails.postValue(Resource.Error("Something went wrong"))
            }
        }
    }

    private fun parseMatchDetails(matchDetails: MatchDetailsResponse) {
        val teamListJSONObject = JSONObject(Gson().toJson(matchDetails.teams))

        val teamKeys: Iterator<String> = teamListJSONObject.keys()
        while (teamKeys.hasNext()) {
            val teamKey = teamKeys.next()
            val teamJsonObject =
                JSONObject(teamListJSONObject.getJSONObject(teamKey).toString())
            val players = mutableListOf<Player>()
            val playersListJsonObject = teamJsonObject.getJSONObject("Players")
            val playerKeys: Iterator<String> = playersListJsonObject.keys()
            while (playerKeys.hasNext()) {
                val playerKey = playerKeys.next()
                val player = Gson().fromJson(
                    playersListJsonObject.getJSONObject(playerKey).toString(),
                    Player::class.java
                )
                players.add(player.copy(teamName = teamJsonObject.getString("Name_Full")))
            }
            teams.add(
                Team(
                    teamName = teamJsonObject.getString("Name_Full"),
                    players = players
                )
            )
        }
        Log.d(TAG, "parseMatchDetails: Teams => ${Gson().toJson(teams)}")
        _matchDetails.postValue(Resource.Success(matchDetails))
    }

    companion object {
        private const val TAG = "MatchDetailsViewModel"
    }
}