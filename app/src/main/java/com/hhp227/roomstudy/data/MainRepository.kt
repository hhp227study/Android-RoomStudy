package com.hhp227.roomstudy.data

import com.hhp227.roomstudy.model.MemoDto

class MainRepository(private val memoDao: MemoDao) {
    fun getMemoList() = memoDao.getMemoList()

    fun getMemo(id: Int) = memoDao.getMemo(id)

    suspend fun addMemo(memoDto: MemoDto) = memoDao.addMemo(memoDto)

    suspend fun setMemo(memoDto: MemoDto) = memoDao.setMemo(memoDto)

    suspend fun removeMemo(memoDto: MemoDto) = memoDao.removeMemo(memoDto)

    companion object {
        @Volatile private var instance: MainRepository? = null

        fun getInstance(memoDao: MemoDao) =
            instance ?: synchronized(this) {
                instance ?: MainRepository(memoDao).also { instance = it }
            }
    }
}