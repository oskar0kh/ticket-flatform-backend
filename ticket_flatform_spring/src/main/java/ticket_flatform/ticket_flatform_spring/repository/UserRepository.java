package ticket_flatform.ticket_flatform_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ticket_flatform.ticket_flatform_spring.domain.User; // domain의 User 클래스 가져오기
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //////////////////////////////////////////////////////////////////
    // Spring Data JPA가 자동으로 만들어주는 메서드들 //
    //////////////////////////////////////////////////////////////////
    
    // 1. username이 일치하는 User 찾는 메서드
    //  1) 자동 생성되는 SQL : SELECT * FROM users WHERE username = ?
    //  2) 반환 타입 : Optional<User>
    //      -> 만약 데이터가 없으면, null 대신 Optional<User> 반환
    //      -> Optional<User> 받아서, 'orElseThrow()' 같은 예외처리 용도로 사용 가능!
    Optional<User> findByUsername(String username);

    // 2. username이 이미 존재하는지 확인하는 메서드 (아이디 중복 검사)
    //  1) 자동 생성되는 SQL : SELECT COUNT(*) FROM users WHERE username = ? LIMIT 1
    //  2) 용도 : 회원가입 시, 이미 존재하는 아이디인지 확인
    boolean existsByUsername(String username);

    //////////////////////////////////////////////////////////////////
    // JPQL 메서드 (사용자가 직접 만드는 메서드) //
    //////////////////////////////////////////////////////////////////

    // 1. userId랑 username이 일치하는 데이터 가져오는 메서드
    // @Param : SQL 쿼리의 "userId" 부분에, 메서드 파라미터 "Long userId" 넣어줌
    @Query("SELECT u FROM User u WHERE u.id = :userId AND u.username = :username")
    Optional<User> findUserCustom(@Param("userId") Long userId, @Param("username") String username);
}
