import com.mine.model.Board
import com.mine.model.Cell
import com.mine.model.PlayMode

// main 루프
fun main() {
    var playAgain = false
    do {
        val mode: PlayMode = playModeChoice()
        val board = Board(mode.probability)
        play(board)
        print("다시 플레이하시겠습니까? (y/n) : ")
        if (readln().toCharArray()[0] == 'y')
            playAgain = true
    } while (playAgain)
}

// 기본 동작 제어
fun play(board: Board) {
    var done = false
    var isClear = true
    do {
        board.drawBoard() // 보드상태 출력

        // 입력 받아오기
        val userChoice: Pair<Cell, Boolean> = getUserChoice(board)
        val chosenCell: Cell = userChoice.first
        val isChangeFlagState: Boolean = userChoice.second

        // flag 세우기일때 동작
        if (isChangeFlagState) {
            if (chosenCell.isOpen) {
                continue
            } else {
                val changedState = chosenCell.setFlag()
                board.updateAnswerCount(changedState)
                board.updateRemainingCount(chosenCell.isFlag)
            }
        }
        // open 일때 동작
        else {
            if (chosenCell.hasMine) {
                done = true
                isClear = false
                continue
            } else {
                chosenCell.open()
            }
        }
        done = board.checkDone() // 클리어여부 확인
    } while (!done)

    board.drawBoard(printAll = true) // 보드 출력 (전체)

    if (isClear) println("축하합니다. 모든 지뢰를 발견하셨습니다!")
    else println("이런... 지뢰를 밟았습니다...")
}

// 사용자 입력 받기
fun getUserChoice(board: Board): Pair<Cell, Boolean> {
    var chosenCell: Cell? = null
    var userInput: List<String>
    do {
        // 입력 받기
        print("입력 : ")
        userInput = readln().split(" ")
        // 비정상 입력시 continue
        if ((userInput.size != 2) && (userInput.size != 3)) continue
        if ((userInput.size == 3) && (userInput[2] != "#")) continue

        // 선택한 셀 저장
        val row: Int = userInput[0].toInt() - 1
        val col: Int = userInput[1].toCharArray()[0]-'A'
        chosenCell = board.getCell(row, col)
    } while (chosenCell == null)

    // 선택한 셀 및 flag 명령여부 반환
    val isChangeFlagState = (userInput.size == 3) && (userInput[2] == "#")
    return chosenCell to isChangeFlagState
}

// 모드 선택
fun playModeChoice(): PlayMode {
    do {
        guidePlayMode()
        val rawInput = readln()
        try {
            val userChoice = PlayMode.valueOf(rawInput)
            return userChoice
        } catch (_: IllegalArgumentException) { }
    } while(true)
}

// 모드 선택 가이드 출력
private fun guidePlayMode() {
    println("플레이 난이도를 선택해 주세요")
    val sb: StringBuilder = StringBuilder()
    sb.append("플레이 난이도는 ")
    var first = true
    for (mode in PlayMode.entries) {
        if (first) first = false
        else sb.append(", ")
        sb.append("$mode")
    }
    sb.append("가 있습니다.")
    println(sb)
}
