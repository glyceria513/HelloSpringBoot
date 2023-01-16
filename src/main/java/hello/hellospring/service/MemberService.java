package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

    //저장소생성
    private final MemberRepository memberRepository;

    //인스턴스를 외부에서 받기위해 생성자 사용
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    /*회원가입*/
    public Long join(Member member) {
        long start = System.currentTimeMillis();

        try{
            //같은 이름의 중복회원검증
            validationDuplicateMember(member);
            memberRepository.save(member);
            return member.getId();

        } finally {
            //로직이 종료될 때 함수호출시간출력
            long finish = System.currentTimeMillis();
            long timesMs = finish - start;
            System.out.println("join = " + timesMs + "ms");
        }
    }

    private void validationDuplicateMember(Member member) {
//        Optional<Member> result = memberRepository.findByName(member.getName());
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });

        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });

    }

    /*전체회원조회*/
    public List<Member> findMembers() {
        long start = System.currentTimeMillis();
        try {
            return memberRepository.findAll();

        } finally {
            //로직이 종료될 때 함수호출시간출력
            long finish = System.currentTimeMillis();
            long timesMs = finish - start;
            System.out.println("findMembers = " + timesMs + "ms");
        }
     }

    /*ID를통한 회원조회*/
    public Optional<Member> findOne(Long memberId) {
        long start = System.currentTimeMillis();
        try {
            return memberRepository.findById(memberId);

        } finally {
            //로직이 종료될 때 함수호출시간출력
            long finish = System.currentTimeMillis();
            long timesMs = finish - start;
            System.out.println("findOne = " + timesMs + "ms");
        }
    }
}
