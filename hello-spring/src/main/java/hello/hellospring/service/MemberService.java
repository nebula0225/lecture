package hello.hellospring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.transaction.annotation.Transactional;

//@Service
@Transactional // JPA를 사용할때는 항상 트랜잭션 안에서 데이터를 변경해야함.
public class MemberService {
	
	private final MemberRepository memberRepository;
	
//	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	/*
	 * 회원 가입
	 */
	public Long join(Member member) {
		
		// 같은 이름이 있는 중복 회원은 안된다.
		validateDuplicateMember(member); // 중복 회원 검증하기
		memberRepository.save(member);
		return member.getId();
	}
	
	// 중복 회원 검증 메소드
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
	
	public Optional<Member> findOne(Long memberId) { // null일 가능성 있으면 옵셔널
		return memberRepository.findById(memberId);
	}
}
