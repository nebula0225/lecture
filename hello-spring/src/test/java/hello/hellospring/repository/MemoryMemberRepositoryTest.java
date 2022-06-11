package hello.hellospring.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import hello.hellospring.domain.Member;

class MemoryMemberRepositoryTest { // 굳이 퍼블릭 안해도 됨 다른데서 가져다 쓸거 아니니까
	
	MemoryMemberRepository repository = new MemoryMemberRepository();
	
	// @AfterEach = 메서드가 끝날때마다 동작하게 함.
	@AfterEach
	public void afterEach() {
		// 메소드 별로 테스트하지 않고 전체를 테스트 했을경우 순서는 랜덤으로 잡히기 때문에
		// 현재 findAll() 메소드가 가장 처음에 시작이 된다. 그래서 findByName() 메소드에 오류가 나온다.
		// Member 객체에 이미 같은 이름으로 사용하고 있기 때문에 공용 데이터도 포함 되어 있음.
		// 그래서 각 메소드가 실행되고 난 후 store를 clear 해줘야한다.
		// Test는 서로 의존관계 없이 설계 되어야 한다.
		repository.clearStore();
	}
	
	@Test
	public void save() {
		Member member = new Member();
		member.setName("spring");
		repository.save(member);
		
		Member result = repository.findById(member.getId()).get();
		System.out.println("result = " + (result == member));
		
		// 글자로 확인하기보다 실제로 잘 실행 됐는지 확인하는 방법
		// 초록불, 빨간불에 따라 다름
		// junit jupiter 에서 제공하는 라이브러리
		// Assertions.assertEquals(member, result);
		
		// assertj.core 에서 제공하는 라이브러리
		// 같은 기능임
		Assertions.assertThat(member).isEqualTo(result);
		
	}
	
	@Test
	public void findByName() {
		Member member1 = new Member();
		member1.setName("Spring1");
		repository.save(member1);
		
		Member member2 = new Member();
		member2.setName("Spring1");
		repository.save(member2);
		
		// Optional<Member> result = repository.findByName("Spring1");
		
		// .get() 을 이용하면 Optional을 빼도 됨. 
		Member result2 = repository.findByName("Spring1").get();
		
		assertThat(result2).isEqualTo(member1);
		
	}
	
	@Test
	public void findAll() {
		Member member1 = new Member();
		member1.setName("Spring1");
		repository.save(member1);
		
		Member member2 = new Member();
		member2.setName("Spring2");
		repository.save(member2);
		
		List<Member> result = repository.findAll();
		assertThat(result.size()).isEqualTo(2);
		
	}
	
	
}
