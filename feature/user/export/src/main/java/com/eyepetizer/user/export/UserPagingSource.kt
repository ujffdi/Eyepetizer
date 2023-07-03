package com.eyepetizer.user.export

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tongsr.core.util.ThreadUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okio.IOException
import retrofit2.HttpException

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/6/3
 * @email ujffdtfivkg@gmail.com
 * @description UserPagingSource
 */
class UserPagingSource : PagingSource<Int, TextModel>() {

    override fun getRefreshKey(state: PagingState<Int, TextModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TextModel> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val data = runBlocking {
                if (page > 1) {
                    delay(2000)
                }
                FakeDataManager.getTextData(page, pageSize)
            }
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (data.isNotEmpty()) page + 1 else null
            LoadResult.Page(data = data, prevKey = prevKey, nextKey = nextKey)
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

}