package hello.hellospring.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import hello.hellospring.domain.Member;

@Repository
public class MemoryMemberRepository implements MemberRepository {
	
	private static Map<Long, Member> store = new HashMap<>();
	private static long sequence = 0L;

	@Override
	public Member save(Member member) {
		// TODO Auto-generated method stub
		member.setId(++sequence);
		store.put(member.getId(), member);
		return member;
	}

	@Override
	public Optional<Member> findById(Long id) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(store.get(id));
		// ofNullable : 값이 null 일수도 아닐수도 있는 경우에 사용
	}

	@Override
	public Optional<Member> findByName(String name) {
		// TODO Auto-generated method stub
		return store.values().stream()
				.filter(member -> member.getName().equals(name))
				.findAny();
		// filter를 쓰기 위해서는 stream 람다를 사용해야함. (filter는 람다식 함수)
		// Stream을 직렬 처리할때 findAny()는 Stream에서 가장 먼저 탐색되는 요소를 리턴함.
		// Stream을 병렬 처리할때는 findAny()가 리턴하는 값이 매번 달라짐.
	}

	@Override
	public List<Member> findAll() {
		// TODO Auto-generated method stub
		return new ArrayList<>(store.values());
	}
	
	public void clearStore() {
		store.clear();
	}

}
