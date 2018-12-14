package com.tanyayuferova.muteme.viewmodel

import io.reactivex.Flowable

/**
 * Author: Tanya Yuferova
 * Date: 11/27/2018
 */
inline fun <T, R> Flowable<out Iterable<T>>.mapList(
    noinline mapper: (T) -> R
): Flowable<List<R>> = map { it.map(mapper::invoke) }
