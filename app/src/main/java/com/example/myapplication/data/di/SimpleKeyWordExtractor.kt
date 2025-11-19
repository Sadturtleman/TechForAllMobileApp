package com.example.myapplication.data.di

object SimpleKeywordExtractor {

    private val particles = listOf(
        "은","는","이","가","을","를","에","에서","까지","에게","한테",
        "으로","로","과","와","도","만"
    )

    fun extract(text: String): List<String> {
        if (text.isBlank()) return emptyList()

        // 특수문자 제거
        val cleaned = text.replace(Regex("[^가-힣0-9\\s]"), " ")

        // 조사 제거
        var noParticle = cleaned
        for (p in particles) {
            noParticle = noParticle.replace(" $p ", " ")
            noParticle = noParticle.replace("$p ", " ")
        }

        // 명사처럼 보이는 단어 필터링
        return noParticle.split(" ")
            .filter { it.length >= 2 }  // 길이 2 이상
            .filter { it.any { c -> c.isLetter() } }
    }
}
