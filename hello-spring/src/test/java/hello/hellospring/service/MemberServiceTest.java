package hello.hellospring.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;


class MemberServiceTest {
	
	MemberService service;
	MemoryMemberRepository memberRepository;
	
	@BeforeEach
	public void beforeEach() {
		memberRepository = new MemoryMemberRepository();
		service = new MemberService(memberRepository);
	}
	
	@AfterEach
	public void afterEach()	{
		memberRepository.clearStore();
	}
	
	@Test
	void 회원가입() { // Test 코드는 한글로해도 상관없음
		
		// given 데이터 받는거
		Member member = new Member();
		member.setName("hello");
		
		// when 검증
		Long saveId = service.join(member);
		
		// then 결과
		Member findMember = service.findOne(saveId).get();
		assertThat(member.getName()).isEqualTo(findMember.getName());
		
	}
	
	@Test
	public void 중복_회원_예외() {
		// given
		Member member1 = new Member();
		member1.setName("spring");
		
		Member member2 = new Member();
		member2.setName("spring");
		
		// when
		service.join(member1);
		
		// 단순하게 람다 로직 실행했을때 IllegalStateException 오류가 나오는지만 체크
		assertThrows(IllegalStateException.class, () -> service.join(member2));
		
//		assertThat 사용 방식 (메세지가 같은지 검증함.)
//		// 2번째 인자의 로직을 실행할건데, 1번째 인자의 이 예외가 터져야 한다.
//		IllegalStateException e = assertThrows(IllegalStateException.class, () -> service.join(member2));
//		assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
		
//		try catch 의 방식
//		try {
//			service.join(member2);
//			fail(); // 더 넘어가면 안되는거임 예외가 떠줘야 정상인거임 안떴으니까 fail 처리
//			
//		} catch (IllegalStateException e) {
//			// 정상적으로 성공한 경우에는 예외로 오는게 맞음
//			
//			// e.get 메세지가 MemberService 파일의 throw new IllegalStateException("이미 존재하는 회원입니다.");과 같아야함 
//			assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//		}
		
		
		// then
		
	}
	
	@Test
	void findMembers() {
		
	}
	
	@Test
	void findOne() {
		
	}

}
