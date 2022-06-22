package com.example.dinusen.data.remote.response

import com.google.gson.annotations.SerializedName

data class EventResponse(

	@field:SerializedName("data")
	val data: EventData?,
)

data class EventData(

	@field:SerializedName("events")
	val events: EventItem
)

data class EventItem(

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
