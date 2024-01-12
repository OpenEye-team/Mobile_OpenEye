package com.txtlabs.openeye.data.response

import com.google.gson.annotations.SerializedName

data class GlucoseResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("bloodGlucoseAverage")
	val bloodGlucoseAverage: Double?,

	@field:SerializedName("bloodGlucoseLevel")
	val bloodGlucoseLevel: String? = null
)

data class DataItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("meal")
	val meal: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("value")
	val value: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
