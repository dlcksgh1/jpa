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

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(team);
            em.persist(member1);

            em.flush(); // 영속성 컨텍스트에 이미 들어가있어 1차캐쉬에서 조회하기 때문에 조회쿼리가 안나감
            em.clear(); // 조회쿼리가 보고싶으면 flush ,clear 하고 조회

/*            Member m = em.find(Member.class, member1.getId());

            System.out.println("m = " + m.getTeam().getClass());
            System.out.println("==============================");
            m.getTeam().getName();
            System.out.println("==============================");
            */

            List<Member> members = em.createQuery("select m from Member m join fetch m.team", Member.class)
                    .getResultList();


            tx.commit();



        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
