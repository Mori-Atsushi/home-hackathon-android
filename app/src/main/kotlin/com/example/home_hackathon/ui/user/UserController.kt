package com.example.home_hackathon.ui.user

import com.airbnb.epoxy.TypedEpoxyController
import com.example.home_hackathon.user

class UserController : TypedEpoxyController<List<UserViewData>>() {
    override fun buildModels(data: List<UserViewData>) {
        data.forEachIndexed { index, it ->
            user {
                id(it.id)
                number(index + 1)
                isDown(it.sounds.isNotEmpty())
            }
        }
    }
}