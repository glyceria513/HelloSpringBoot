package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);                 //회원저장
    Optional<Member> findById(Long id);         //id로 회원찾기
    Optional<Member> findByName(String name);   //회원이름으로 회원찾기
    List<Member> findAll();                     //저장한 모든 회원리스트를 반환
}
