package qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class UserTest {
    public static final User JAVAJIGI = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
    public static final User SANJIGI = new User(2L, "sanjigi", "password", "name", "sanjigi@slipp.net");

    @Autowired
    private UserRepository users;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("회원 정보 테이블 정상 저장 테스트")
    void save() {
        User expected = UserTest.JAVAJIGI;
        User actual = users.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail())
        );
    }

    @Test
    @DisplayName("유저 아이디 기준 조회 테스트")
    void findById() {
        User expected = users.save(UserTest.SANJIGI);
        Optional<User> actual = users.findById(expected.getId());
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getId()).isEqualTo(expected.getId());
        assertThat(actual.get() == expected).isTrue();
    }

    @Test
    @DisplayName("회원 정보 테이블 정상 수정 테스트")
    void update() {
        String name = "mwkwon";
        User expected = users.save(UserTest.JAVAJIGI);
        expected.name(name);
        entityManager.flush();
        entityManager.clear();
        Optional<User> actual = users.findById(expected.getId());
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("회원 정보 테이블 정상 삭제 테스트")
    void delete() {
        User expected = users.save(UserTest.SANJIGI);
        users.delete(expected);
        entityManager.flush();
        entityManager.clear();
        Optional<User> actual = users.findById(expected.getId());
        assertThat(actual.isPresent()).isFalse();
    }
}
