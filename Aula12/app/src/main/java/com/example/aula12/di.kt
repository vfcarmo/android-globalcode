package com.example.aula12

class Twitter {
    fun tweet(okHttp: OkHttp) {
        val twitterApi = TwitterApi("Vítor Franco", okHttp)
        twitterApi.tweet("Texto para twittar")
    }
}

class TwitterApi(val user: String, val okHttp: OkHttp) {
    fun tweet(text: String) {
        okHttp.post("realizar tweet")
    }

    fun timeline(user: String) {
        okHttp.post("obter timeline")
    }
}

class OkHttp {
    fun post(action: String) {

    }

}
