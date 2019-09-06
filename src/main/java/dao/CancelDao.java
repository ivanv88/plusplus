package dao;

import domain.CancelEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;


public class CancelDao {
    private static CancelDao cancelDaoInstance = null;


    private CancelDao() {
    }

    public synchronized static CancelDao getInstance() {
        if (cancelDaoInstance == null)
            cancelDaoInstance = new CancelDao();

        return cancelDaoInstance;
    }

    public synchronized void saveCancel(CancelEntity cancel) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(cancel);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Package not saved to the database!");
        }
    }

}
