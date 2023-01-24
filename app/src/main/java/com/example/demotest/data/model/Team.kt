package com.example.demotest.data.model

import com.google.gson.annotations.SerializedName

data class Team(
    val teamName: String,
    val players: List<Player>
)

data class Player(
    @SerializedName("Batting")
    val batting: Batting,
    @SerializedName("Bowling")
    val bowling: Bowling,
    @SerializedName("Iskeeper")
    val iskeeper: Boolean = false,
    @SerializedName("Name_Full")
    val nameFull: String,
    @SerializedName("Position")
    val position: String,
    val teamName: String,
    @SerializedName("Iscaptain")
    val isCaptain :Boolean = false
)

data class Batting(
    @SerializedName("Average")
    val average: String,
    @SerializedName("Runs")
    val runs: String,
    @SerializedName("Strikerate")
    val strikerate: String,
    @SerializedName("Style")
    val style: String
)

data class Bowling(
    @SerializedName("Average")
    val average: String,
    @SerializedName("Economyrate")
    val economyrate: String,
    @SerializedName("Style")
    val style: String,
    @SerializedName("Wickets")
    val wickets: String
)