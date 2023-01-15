package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class MemoryMemberRepositoryTest
{
    MemoryMemberRepository respository = new MemoryMemberRepository();

    //@AfterEach : 함수 실행 종료시마다 실행된다. (일종의 콜백메소드)
    @AfterEach
    public void afterEach() {
        respository.clearStore();
    }

    @Test
    public void save() {
        //객체 생성해 name저장
        Member member = new Member();
        member.setName("spring");

        //회원정보저장기능 정상동작확인 = member객체로 회원정보 저장 실행
        respository.save(member);

        //테스트 수행 : 기대값과 결과값의 일치성 확인
        Member result = respository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        //일치하는 name정보를 찾기위해 저장수행1
        Member member1 = new Member();
        member1.setName("Haru");
        respository.save(member1);

        //일치하는 name정보를 찾기위해 저장수행2
        Member member2 = new Member();
        member2.setName("Jimin");
        respository.save(member2);

        //회원정보찾기기능 정상동작확인 = name값을 이용해 회원정보 찾기기능 수행
        Member result = respository.findByName("Haru").get();

        //테스트 수행
        assertThat(member1).isEqualTo(result);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("Haru");
        respository.save(member1);

        Member member2 = new Member();
        member2.setName("Jimin");
        respository.save(member2);

        List<Member> result = respository.findAll();

        //기대값은2(2명의 회원정보를 저장했으므로.)
        assertThat(result.size()).isEqualTo(2);
    }

}
