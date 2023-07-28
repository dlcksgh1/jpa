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
