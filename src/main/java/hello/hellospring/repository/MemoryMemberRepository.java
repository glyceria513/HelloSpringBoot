package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.util.*;

public class MemoryMemberRepository implements MemberRepository{    //구현체

    //메모리 저장을 위해 선언
    private static Map<Long, Member> store = new HashMap<>();   //저장소(DB를 사용하지 않기때문에 DB를 대신해 사용할 것이다.)
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    /*저장소 지우기 :여기서는 저장소를 사용하기에 저장소 데이터를 지우지만
                   , DB를 사용할 때는 DB에 저장된 데이터를 지워주고, TEST코드에서 객체도 지워줘야 한다. */
    public void clearStore() {
        store.clear();
    }
}
















