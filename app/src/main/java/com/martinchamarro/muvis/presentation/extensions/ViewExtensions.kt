/*
 * Copyright 2017 Martin Chamarro (@martinchamarro)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.martinchamarro.muvis.presentation.extensions

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.annotation.IntegerRes
import android.view.Menu
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso

val View.ctx: Context
    get() = context

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.integer(@IntegerRes integerRes: Int) = ctx.integer(integerRes)

fun ImageView.load(url: String?, @DrawableRes placeholderRes: Int = -1) {
    if (url.isNullOrBlank()) return
    val request = Picasso.with(context).load(url)
    if (placeholderRes != -1) {
        request.placeholder(placeholderRes)
        request.error(placeholderRes)
    }
    request.into(this)
}

fun Menu.setVisible(visible: Boolean) {
    for (i in 0..size() - 1) getItem(i).isVisible = visible
}
