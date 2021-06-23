package com.hhp227.roomstudy.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.*
import com.hhp227.roomstudy.model.MemoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface MemoDao {
    @Query("Select * from memo_list")
    fun getMemoList(): Flow<List<MemoDto>>

    @Query("Select * from memo_list where id = :id")
    fun getMemo(id: Int): MemoDto

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMemo(memoDto: MemoDto)

    @WorkerThread
    @Update
    suspend fun setMemo(memoDto: MemoDto)

    @WorkerThread
    @Delete
    suspend fun removeMemo(memoDto: MemoDto)
}