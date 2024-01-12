package com.txtlabs.openeye.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("token")
	val loginToken: String? = null
)

