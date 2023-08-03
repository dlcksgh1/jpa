package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        //code
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            // 양방향 연관관계에서 가장많이 하는 실수
            /*
            Member member = new Member();
            member.setName("member1");
            em.persist(member);

            Team team = new Team();
            team.setName("TeamA");
            team.getMembers().add(member);
            em.persist(team);
            */

            //저장

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.changeTeam(team); // 연관관계 편의 메소드
            em.persist(member);

            Member m1 = em.getReference(Member.class, member.getId());
            Member m2 = em.getReference(Member.class, member.getId());
            Member m3 = em.find(Member.class, member.getId());

            System.out.println("m1 ==  m2" + (m1.getClass() == m2.getClass())); //true
            System.out.println("m1 ==  m3" + (m1.getClass() == m3.getClass())); // false Instance of 로 비교해라

            System.out.println("a == a" + (m1 == m3)); //true
            //team.getMembers().add(member); // changeTeam() 에 연관관계 편의 메소드를 생성



            em.flush(); // 영속성 컨텍스트에 이미 들어가있어 1차캐쉬에서 조회하기 때문에 조회쿼리가 안나감
            em.clear(); // 조회쿼리가 보고싶으면 flush ,clear 하고 조회

            //조회
            /*
            Member findMember = em.find(Member.class, member.getId());
            List<Member> members = findMember.getTeam().getMembers();

            for (Member m : members) {
                System.out.println("m = " + m.getName());
            }*/

            //수정
            /*
            Team newTeam = em.find(Team.class, 100L);
            findMember.setTeam(newTeam);
            */

            /*      
            생성
            Member member = new Member();
            member.setId(1L);
            member.setName("HelloA");

            em.persist(member);
            */

            /*
            조회
            Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember.id" + findMember.getId());
            System.out.println("findMember.name" + findMember.getName());

            */

            /*
            수정
            Member findMember = em.find(Member.class, 1L);
            findMember.setName("helloJPA");
            
            */

            // JPQL
/*            List<Member> resultList = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            for (Member member : resultList) {
                System.out.println("member.Name = " + member.getUsername());
            }*/

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
