package com.eyepetizer.user.export

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.airbnb.mvrx.ExperimentalMavericksApi
import com.airbnb.mvrx.MavericksRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/6/3
 * @email ujffdtfivkg@gmail.com
 * @description
 */
@OptIn(ExperimentalMavericksApi::class)
class UserRepository constructor(scope: CoroutineScope) :
    MavericksRepository<UserState>(
        initialState = UserState(),
        coroutineScope = scope,
        BuildConfig.DEBUG
    ) {

    fun getTextPagingData(): Flow<PagingData<TextModel>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = { UserPagingSource() }
        ).flow
    }

    companion object {

        private const val PAGE_SIZE = 10
    }

}