package com.txtlabs.openeye.data.post

import com.google.gson.annotations.SerializedName

data class ArticleBody(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("content")
	val content: String? = null
)
