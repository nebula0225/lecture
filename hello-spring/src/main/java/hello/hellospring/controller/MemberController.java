package hello.hellospring.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;

@Controller
public class MemberController {
	
	private final MemberService memberService;

	// 단일 생성자인 경우에는 어노테이션 안붙여도 됨.
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
		System.out.println("memberService = " + memberService.getClass()); // aop 적용시 proxy가 들어옴
	}
	
	@GetMapping("/members/new")
	public String createForm() {
		return "members/createMemberForm";
	}
	
	@PostMapping("/members/new")
	public String create(MemberForm form) {
		Member member = new Member();
		
		member.setName(form.getName());
		memberService.join(member);
		
		return "redirect:/";
	}
	
	@GetMapping("/members")
	public String list(Model model) {
		List<Member> members = memberService.findMembers();
		model.addAttribute("members", members);
		
		return "members/memberList";
	}
	
	
}
