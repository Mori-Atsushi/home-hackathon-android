package com.example.home_hackathon.ui.user

import com.airbnb.epoxy.TypedEpoxyController
import com.example.home_hackathon.model.User
import com.example.home_hackathon.user

class UserController : TypedEpoxyController<List<User>>() {
    override fun buildModels(data: List<User>) {
        data.forEachIndexed { index, it ->
            user {
                id(it.id)
                number(index + 1)
                isDown(it.sounds.isNotEmpty())
            }
        }
    }
}
