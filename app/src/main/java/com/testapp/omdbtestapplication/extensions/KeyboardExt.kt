package com.testapp.omdbtestapplication.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

internal const val NO_FLAGS = 0


fun Activity.hideKeyboard(view: View? = null) = (this as Context).hideKeyboard(
    view
        ?: currentFocus
)

fun Context.hideKeyboard(view: View? = null) = view?.let {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.apply {
        hideSoftInputFromWindow(it.windowToken, NO_FLAGS)
    }
}

fun Fragment.hideKeyboard(view: View? = null) = activity?.hideKeyboard(view)