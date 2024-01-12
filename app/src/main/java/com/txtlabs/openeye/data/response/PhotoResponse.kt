package com.txtlabs.openeye.data.response

import com.google.gson.annotations.SerializedName

data class PhotoResponse(

	@field:SerializedName("fieldname")
	val fieldname: String? = null,

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("filename")
	val filename: String? = null,

	@field:SerializedName("size")
	val size: Int? = null,

	@field:SerializedName("originalname")
	val originalname: String? = null,

	@field:SerializedName("destination")
	val destination: String? = null,

	@field:SerializedName("mimetype")
	val mimetype: String? = null,

	@field:SerializedName("encoding")
	val encoding: String? = null
)
