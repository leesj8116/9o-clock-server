package dreamdiary.nineoclock.quiz.application.service

import dreamdiary.nineoclock.quiz.application.port.inbound.QuizCreateCommand
import dreamdiary.nineoclock.quiz.application.port.inbound.QuizCreateUseCase
import dreamdiary.nineoclock.quiz.application.port.outbound.QuizOutPort
import dreamdiary.nineoclock.quiz.domain.Choice
import dreamdiary.nineoclock.quiz.domain.ChoiceGroup
import dreamdiary.nineoclock.quiz.domain.Quiz
import dreamdiary.nineoclock.quiz.domain.QuizActionAuthorizer
import dreamdiary.nineoclock.quiz.domain.QuizContent
import dreamdiary.nineoclock.quiz.domain.QuizTitle
import dreamdiary.nineoclock.shard.inline.isNotEmptyToThrow
import jakarta.validation.Validator
import org.springframework.stereotype.Service

@Service
internal class QuizCreateService(
    private val validator: Validator,
    private val quizActionAuthorizer: QuizActionAuthorizer,
    private val quizOutPort: QuizOutPort
) : QuizCreateUseCase {

    override fun createQuiz(command: QuizCreateCommand): String {
        validator.validate(command).isNotEmptyToThrow { RuntimeException() }
        if (!quizActionAuthorizer.authorizeQuizCreateAction(command.creatorId)) throw RuntimeException()

        val quiz = Quiz(
            title = QuizTitle(command.title),
            content = QuizContent(command.content),
            choiceGroup = ChoiceGroup(command.choices.stream().map { choice -> Choice(choice.value, choice.isAnswer) }
                .toList()),
            releaseAt = command.releaseAt,
            answerReleaseAt = command.answerReleaseAt
        )

        quizOutPort.save(quiz)
        return quiz.id.publicId.value
    }
}
