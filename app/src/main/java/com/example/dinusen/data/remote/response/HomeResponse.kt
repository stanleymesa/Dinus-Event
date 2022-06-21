package com.example.dinusen.data.remote.response

import com.google.gson.annotations.SerializedName

data class HomeResponse(

	@field:SerializedName("data")
	val data: HomeData?,
)

data class HomeData(

	@field:SerializedName("on_going")
	val onGoing: List<HomeItem>,

	@field:SerializedName("online_upcoming")
	val onlineUpcoming: List<HomeItem>,

	@field:SerializedName("offline_upcoming")
	val offlineUpcoming: List<HomeItem>
)

data class HomeItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("event_id")
	val eventId: Int,

	@field:SerializedName("from_date")
	val fromDate: String,

	@field:SerializedName("contact")
	val contact: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("link")
	val link: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("until_date")
	val untilDate: String,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("room")
	val room: String
)

