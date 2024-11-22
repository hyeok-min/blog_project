package blog.proj.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass// 이 클래스를 상속받는 엔티티가 createdDate와 ModifiedDate를 필드로 갖게 됨.
@EntityListeners(AuditingEntityListener.class)//엔티티의 생성 및 수정을 감지함 //@CreatedDate,@LastModifiedDate 어노테이션을 사용하면 날짜가 자동설정됨.
public class BaseTimeEntity {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    private LocalDateTime  createDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;


//===========================================================적용 테스트 해봐야함.
    //쉽게 사용하기 위한 메서드
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // LocalDateTime -> String
    public static String toString(LocalDateTime dateTime) {
        return dateTime.format(FORMATTER);
    }

    // String -> LocalDateTime
    public static LocalDateTime toLocalDateTime(String dateString) {
        return LocalDateTime.parse(dateString, FORMATTER);
    }
}
