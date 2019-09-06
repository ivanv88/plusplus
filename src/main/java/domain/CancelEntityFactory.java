package domain;

public class CancelEntityFactory {

    private static CancelEntityFactory factoryInstance = null;

    private CancelEntityFactory() {
    }

    public synchronized static CancelEntityFactory getFactory() {
        if (factoryInstance == null) {
            factoryInstance = new CancelEntityFactory();
        }

        return factoryInstance;
    }

    public synchronized CancelEntity createCancelEntity() {
        CancelEntity cancelEntity = new CancelEntity();
        return cancelEntity;
    }

}
