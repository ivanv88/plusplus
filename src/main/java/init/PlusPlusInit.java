package init;

import connector.PlusPlusConnector;
import connector.SyncInput;
import dao.DummyDao;
import domain.DummyEntityFactory;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import connector.SyncOutput;

import java.io.IOException;

public class PlusPlusInit {

    private static PlusPlusInit plusPlusInitInstance = null;

    private PlusPlusConnector connector;
    private SessionFactory sessionFactory;
    private DummyEntityFactory dummyEntityFactory;
    private DummyDao dummyDao;
    private SyncOutput output;
    private SyncInput input;

    private PlusPlusInit() {
    }

    public static PlusPlusInit getInstance() {
        if (plusPlusInitInstance == null)
            plusPlusInitInstance = new PlusPlusInit();

        return plusPlusInitInstance;
    }

    public void createSessionFactory() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void appInit(String url, int port) throws IOException {
        this.connector = connector.getInstance();
        connector.createConection(url, port);
        this.dummyEntityFactory = DummyEntityFactory.getFactory();
        this.dummyDao = DummyDao.getInstance();
        this.output = SyncOutput.getInstance();
        output.setOut(connector.getConnectorOutputStream());
        this.input = SyncInput.getInstance();
        input.setOut(connector.getConnectorInputStream());
    }

}
