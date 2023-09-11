package dreamdiary.nineoclock.quiz.application.service

import dreamdiary.nineoclock.quiz.application.port.inbound.ChoiceQueryResult
import dreamdiary.nineoclock.quiz.application.port.inbound.QuizFindUseCase
import dreamdiary.nineoclock.quiz.application.port.inbound.QuizQueryResult
import dreamdiary.nineoclock.quiz.application.port.outbound.QuizOutPort
import dreamdiary.nineoclock.shard.identifier.QuizPublicId
import org.springframework.stereotype.Service

@Service
internal class QuizFindService(
    private val quizOutPort: QuizOutPort
) : QuizFindUseCase {
    override fun findQuiz(publicId: String): QuizQueryResult? {
        return quizOutPort.findByPublicId(QuizPublicId(publicId))?.let {
            QuizQueryResult(
                publicId = it.id.publicId.value,
                title = it.title.value,
                content = it.content.value,
                choices = it.choiceGroup.values.map { choice ->
                    ChoiceQueryResult(
                        publicId = choice.id.publicId.value,
                        text = choice.value,
                        isAnswer = choice.isAnswer
                    )
                },
                releaseAt = it.releaseAt,
                answerReleaseAt = it.answerReleaseAt,
            )
        }
    }
}
