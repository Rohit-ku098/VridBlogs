package com.example.vridblogs.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Content(
    val protected: Boolean,
    val rendered: String
)