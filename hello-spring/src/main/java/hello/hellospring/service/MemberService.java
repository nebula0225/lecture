package hello.hellospring.service;

import java.util.List;
import java.util.Optional;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

public class MemberService {
	private final MemberRepository memberRepository = new MemoryMemberRepository();
	
	/*
	 * 회원 가입
	 */
	public Long join(Member member) {
		
		// 같은 이름이 있는 중복 회원은 안된다.
		validateDuplicateMember(member); // 중복 회원 검증하기
		memberRepository.save(member);
		return member.getId();
	}
	
	private void validateDuplicateMember(Member member) {
		
		// null 일 가능성이 있으면 옵셔널로 감쌈
//		Optional<Member> result = memberRepository.findByName(member.getName());
//		result.ifPresent(m -> {
//			throw new IllegalStateException("이미 존재하는 회원입니다.");
//		});
		
		// 어차피 result를 반환하기 때문에 앞에 Optional 부분은 생략이 가능하고 바로 ifPresent() 사용 가능
		memberRepository.findByName(member.getName())
			.ifPresent(m -> {
				throw new IllegalStateException("이미 존재하는 회원입니다.");
			});
	}
	
	/*
	 * 전체 회원 조회
	 */
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	
	public Optional<Member> findOne(Long memberId) {
		return memberRepository.findById(memberId);
	}
}
