package com.example.home_hackathon.ui.user

import com.airbnb.epoxy.TypedEpoxyController
import com.example.home_hackathon.user

class UserController : TypedEpoxyController<List<String>>() {
    override fun buildModels(data: List<String>) {
        data.forEachIndexed { index, s ->
            user {
                id(s)
                number(index + 1)
            }
        }
    }
}