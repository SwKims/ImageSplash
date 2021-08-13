package com.ksw.imagesplash.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * Created by KSW on 2021-08-13
 */

fun String?.isJsonObject(): Boolean {
    /*if (this?.startsWith("{") == true && this.endsWith("}")) {
        return true
    } else {
        return false
    }*/

    return this?.startsWith("{") == true && this.endsWith("}")
}

fun String?.isJsonArray(): Boolean {
    return this?.startsWith("[") == true && this.endsWith("]")
}

fun EditText.onMyTextChanged(completion: (Editable?) -> Unit) {
    this.addTextChangedListener(object: TextWatcher{

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(editable: Editable?) {
            completion(editable)
        }

    })
}