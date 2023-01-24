package com.example.demotest.data.model


import Matchdetail
import com.google.gson.annotations.SerializedName


data class MatchDetailsResponse(
    @SerializedName("Matchdetail")
    val matchdetail: Matchdetail,
    @SerializedName("Nuggets")
    val nuggets: List<String>,
    @SerializedName("Teams")
    val teams: Any,
)