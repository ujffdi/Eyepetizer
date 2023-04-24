package com.tongsr.base.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

/**
 * @author Tongsr
 * @version 1.0
 * @date 2023/2/24
 * @email ujffdtfivkg@gmail.com
 * @description BaseViewModel
 */
abstract class BaseViewModel : ViewModel() {

//    private val _apiException = MutableLiveData<ApiException>()
//
//    val apiException: LiveData<ApiException> = _apiException

}

/**
 * 请求扩展函数。考虑到不是每个请求都需要 error/completion回调
 *
 * @param request request
 * @param onStart request start
 * @param onSuccess request success
 * @param onCompletion request completion
 * @param onError request error
 */
//inline fun <T> ViewModel.executeRequest(
//    request: Flow<ApiResult<T>>,
//    crossinline onSuccess: (T) -> Unit,
//    crossinline onStart: () -> Unit = {},
//    crossinline onCompletion: () -> Unit = {},
//    crossinline onError: (Throwable) -> Unit = {}
//) {
//    request.onStart { onStart() }
//        .onCompletion { onCompletion() }
//        .catch { onError(it) }
//        .onEach { result ->
//            if (result.code == 0) {
//                onSuccess(result.data)
//            } else {
//                onError(ApiException(result.code, result.message))
//            }
//        }.launchIn(viewModelScope)
//
//}






