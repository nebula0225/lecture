package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em; // JPA는 EntityManager로 모든게 동작함.

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); // persist는 영속하다는 뜻. 즉, 영구저장한다는 뜻.
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member memeber = em.find(Member.class, id); // find는 조회한다는 뜻.
        return Optional.ofNullable(memeber); // null이어도 감싸서 반환해줌
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class) // JPQL이라는 객체지향 쿼리 언어임.
                .setParameter("name", name) // JPQL은 SQL로 번역됨.
                .getResultList(); // JPQL이라는 객체지향 쿼리 언어임.
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class) // JPQL이라는 객체지향 쿼리 언어임.
                .getResultList(); // JPQL은 SQL로 번역됨.
    }
}
