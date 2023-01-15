package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");  //data에 hello!! 데이터 추가
        return "hello"; //hello 페이지에 반환
    }

    @GetMapping("hello-mvc")    //get맵핑
    public String helloMvc(@RequestParam(name="name") String name, Model model) {
        //외부에서 파라미터 받기(RequestParam 어노테이션 사용)
        //Model에 담으면 뷰에서 렌더링할 떄 쓴다.
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        //Hello 객체 생성
        Hello hello = new Hello();
        hello.setName(name);
        return hello; //문자열이 아닌 객체전달

    }

    static class Hello{ //Static을 써주면 클래스 안에 클래스를 선언할 수 있다. (HelloController.Hello가 되며, 자바에서 정식으로 지원하는 문법이다.)
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
