package com.txtlabs.openeye.data.response

import com.google.gson.annotations.SerializedName


data class RetinopathyResponse(

	@field:SerializedName("kumilcintabh")
	val kumilcintabh: Kumilcintabh? = null,

	@field:SerializedName("prediction")
	val prediction: Int? = null,

	@field:SerializedName("label")
	val label: String? = null
)

data class Kumilcintabh(

	@field:SerializedName("general_recommendation")
	val generalRecommendation: List<String?>? = null,

	@field:SerializedName("specific_recomendation")
	val specificRecomendation: List<String?>? = null,

	@field:SerializedName("title")
	val title: String? = null
)
