package com.eyepetizer.home.export

import com.airbnb.mvrx.MavericksViewModel

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/5/13
 * @email ujffdtfivkg@gmail.com
 * @description
 */
class HomeViewModel(initialState: CounterState): MavericksViewModel<CounterState>(initialState) {

    fun incrementCount() = setState { copy(count = count + 1) }

}