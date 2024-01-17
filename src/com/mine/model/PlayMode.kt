package com.mine.model

// PlayMode 난이도별 지뢰 생성 확률
enum class PlayMode(val probability: Int) {
    Easy(5), Hard(10), Expert(15)
}