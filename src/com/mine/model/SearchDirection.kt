package com.mine.model

// 8방향 간선 검색을 위한 enum class
enum class SearchDirection(val rowDelta: Int, val colDelta: Int) {
    E(rowDelta = 0, colDelta = 1), S(rowDelta = 1, colDelta = 0),
    W(rowDelta = 0, colDelta = -1), N(rowDelta = -1, colDelta = 0),
    ES(rowDelta = 1, colDelta = 1), EN(rowDelta = -1, colDelta = 1),
    WS(rowDelta = 1, colDelta = -1), WN(rowDelta = -1, colDelta = -1)
}