package com.txtlabs.openeye.data.response

import java.io.Serializable

data class ArticleData(
    val judul: String,
    val tanggalTerbit: String,
    val author: String,
    val isi: String,
    val img: Int
) : Serializable


