package com.txtlabs.openeye.data.post

import com.google.gson.annotations.SerializedName

data class SignUpBody (
    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("username")
    val username: String? = null,
)
