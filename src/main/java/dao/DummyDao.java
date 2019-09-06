package dao;

import domain.DummyEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import java.util.List;

public class DummyDao {

    private static DummyDao dummyDaoInstance = null;


    private DummyDao() {
    }

    public synchronized static DummyDao getInstance() {
        if (dummyDaoInstance == null)
            dummyDaoInstance = new DummyDao();

        return dummyDaoInstance;
    }

    public synchronized void saveDummy(DummyEntity dummy) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(dummy);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Package not saved to the database!");
        }
    }

    public synchronized void updateDummy(DummyEntity dummy) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(dummy);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Package not updated!");
        }
    }

    public List< DummyEntity > getExpiredDummies() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session
                    .createQuery("Select d FROM DummyEntity d where d.expired=:exp and d.seen=:seen", DummyEntity.class)
                    .setParameter("exp", true)
                    .setParameter("seen", false)
                    .list();
        }
    }

}
