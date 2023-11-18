package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional // 테스트 데이터 롤백 해줌
class MemberServiceIntegrationTest {

	@Autowired
	MemberService service;

	@Autowired
	MemberRepository memberRepository;

	@Test
	void 회원가입() { // Test 코드는 한글로해도 상관없음
		
		// given 데이터 받는거
		Member member = new Member();
		member.setName("test_spring");
		
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
		// 2번째 인자의 로직을 실행할건데, 1번째 인자의 이 예외가 터져야 한다.
		IllegalStateException e = assertThrows(IllegalStateException.class, () -> service.join(member2));
		assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

	}
	
}
