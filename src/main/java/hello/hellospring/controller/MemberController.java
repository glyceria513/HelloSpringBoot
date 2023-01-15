package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //회원 등록 페이지 이동
    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    //회원 등록
    @PostMapping("/members/new")
    public  String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        //form태그로부터 데이터 잘 받는지 확인
        System.out.println("member name : " + member.getName());

        memberService.join(member);

        //redirect : 회원가입이 되면 home화면으로 되돌려보낸다.
         return "redirect:/";
    }

    //회원조회 및 조회페이지로 이동
    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
