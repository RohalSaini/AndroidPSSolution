package sanssys.solution.pssolutions.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


sealed class Either <out A,out B>
data class Left<out A,out  B>(val a:A): Either<A,B>()
data class Right<out A,out  B>(val b:B): Either<A,B>()


fun hideKeyboardFrom(context: Context, view: View) {
    val imm: InputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}
