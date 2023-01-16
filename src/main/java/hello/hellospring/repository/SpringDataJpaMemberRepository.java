package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    //jpql : select m from Member m where m.name = ? 로 자동생성된다.
    @Override
    Optional<Member> findByName(String name);
}
