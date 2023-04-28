package com.example.task_sqb.utils

import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener
import androidx.viewbinding.ViewBinding

fun EditText.myAddTextChangedListener(block: (String) -> Unit) {
    this.addTextChangedListener {
        it?.let {
            block.invoke(it.toString())
        }
    }
}

fun EditText.amount() : String = this.text.toString().trim()
fun <T : ViewBinding> T.myApply(block: T.() -> Unit) {
    block(this)
}
fun AppCompatEditText.myAddTextChangedListener(block: (String) -> Unit) {
    this.addTextChangedListener {
        it?.let {
            block.invoke(it.toString())
        }
    }
}