package com.txtlabs.openeye.data.post

import com.google.gson.annotations.SerializedName

data class GlucoseBody(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("value")
	val value: Int? = null,

	@field:SerializedName("meal")
	val meal: String? = null
)
