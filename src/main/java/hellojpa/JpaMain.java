package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        //code
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            Member member = new Member();
            member.setUsername("hello");
            member.setAge(10);
            em.persist(member);

            TypedQuery<Member> query1 = em.createQuery("SELECT m FROM Member m", Member.class);
            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
            Query query3 = em.createQuery("select m.username, m.age from Member m");

            List<Member> resultList = query1.getResultList();
            Member singleResult = query1.getSingleResult();


            //페이징 쿼리
            String jpql = "select m from Member m order by m.username desc";
            List<Member> resultList2 = em.createQuery(jpql, Member.class)
                    .setFirstResult(10)
                    .setMaxResults(20)
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
