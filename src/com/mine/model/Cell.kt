package com.mine.model

class Cell {
    private var surroundingMines: Int = 0 // 주변 지뢰 갯수
    private var neighborCell: MutableList<Cell> = mutableListOf() // 간선 저장 리스트

    // 기초정보
    var hasMine: Boolean = false
    var isOpen: Boolean = false
    var isFlag: Boolean = false

    // 간선 추가
    fun addNeighbor(neighbor: Cell) {
        neighborCell.add(neighbor)
    }

    // 간선 노드의 주변 지뢰 갯수 + 1
    fun increaseSurroundingMines() {
        neighborCell.forEach { neighbor ->
            neighbor.surroundingMines += 1
        }
    }

    // 본인의 상태를 반환
    fun drawChar(showAnswer: Boolean): Char {
        if (isFlag) return '*' // flag 세우면 # 반환
        if (!showAnswer && !isOpen) return ' ' // open 된 상태가 아니면 공백 반환
        if (hasMine) return '#' // 지뢰면 # 반환
        if (surroundingMines == 0) return '.' // 주변 지뢰가 없다면 0(또는 특정 문자) 반환
        return surroundingMines.digitToChar() // 주변 지뢰 갯수 반환
    }

    // cell 상태를 open
    fun open() {
        isOpen = true
        // 본인이 0일경우 주변 cell 도 함께 open
        if (surroundingMines == 0) {
            neighborCell.forEach { nextCell ->
                if (!nextCell.isOpen) nextCell.open()
            }
        }
    }

    // flag 세우기 (반환값은 남은 정답 수량에 반영)
    fun setFlag(): Int {
        isFlag = !isFlag
        if (isFlag && hasMine) return -1 // 지뢰인데 flag 세울시 -1 반환
        if (!isFlag && hasMine) return 1 // 지뢰인데 flag 치울시 1 반환
        return 0 // 이외의 경우 0 반환 (변화 X)
    }
}