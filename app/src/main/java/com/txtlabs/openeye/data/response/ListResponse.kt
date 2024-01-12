package com.txtlabs.openeye.data.response

import com.google.gson.annotations.SerializedName

data class ListResponse<T>(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<T>,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)


