package hello.hellospring.service;

import hello.hellospring.domain.Member;
import static org.assertj.core.api.Assertions.*;

import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    //메소드 실행전 객체생성
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    //메소드 종료 후 저장소clear
    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given : 테스트할 데이터
        Member member = new Member();
        member.setName("해리");

        //when : 회원가입실행
        Long saveId = memberService.join(member);

        //then : 저장소에 저장된 name과 생성한 member객체의 name이 같은지 검증
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 회원가입_중복예외검증() {
        Member member1 = new Member();
        member1.setName("해리");
        Member member2 = new Member();
        member2.setName("해리");

        memberService.join(member1);
        // memberService.join(member2)를 수행한 결과가 IllegalStateException 이면 테스트는 성공한다.
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        
        //에러메시지 검증
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        
//        //member1, member2의 name이 같으므로 예외가 발생해야한다. 예외가 발생하면 테스트는 성공한다.
//        //but, 문제가 생겼는지 아닌지 알 수 없어 애매하므로 assert문을 통해 검증을 수행해준다.
//        try {
//            memberService.join(member2);
//            fail();
//        } catch (IllegalStateException e) {
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}