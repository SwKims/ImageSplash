package com.ksw.imagesplash.util

/**
 * Created by KSW on 2021-08-13
 */

object Constants {
    const val TAG: String = "로그"
}

enum class SEARCH_TYPE {
    PHOTO, USER
}

enum class RESPONSE_STATE {
    OKAY, FAIL
}

object API {
    const val BASE_URL: String = "https://api.unsplash.com/"
    const val CLIENT_ID: String = "YuhA57sKju2U4eeGqN9awfpStsV362tHkmfW4QxxRx8"
    const val SEARCH_PHOTOS: String = "search/photos"
    const val SEARCH_USERS: String = "search/users"
}