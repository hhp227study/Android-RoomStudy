package com.hhp227.roomstudy.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memo_list")
data class MemoDto(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var title: String,
    var text: String
)