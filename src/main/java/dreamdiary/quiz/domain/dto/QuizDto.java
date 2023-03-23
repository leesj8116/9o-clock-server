package dreamdiary.quiz.domain.dto;


import dreamdiary.quiz.domain.entity.QuizEntity;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class QuizDto {
  private String title;
  private String description;
  private int answer;

  //문항 1,2,3,4
  private String optNumOne;
  private String optNumTwo;
  private String optNumThree;
  private String optNumFour;

  // TODO 미래 날짜만 가능, 형식 지정
  private LocalDateTime releasedDate;

  public QuizEntity toEntity() {
    return QuizEntity.builder().title(this.title)
        .description(this.description)
        .answer(this.answer)
        .optNumOne(this.optNumOne)
        .optNumTwo(this.optNumTwo)
        .optNumThree(this.optNumThree)
        .optNumFour(this.optNumFour)
        .releasedDate(this.releasedDate)
        .build();
  }
}
