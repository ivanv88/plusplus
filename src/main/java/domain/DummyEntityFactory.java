package domain;

public class DummyEntityFactory {

    private static DummyEntityFactory factoryInstance = null;

    private DummyEntityFactory() {
    }

    public synchronized static DummyEntityFactory getFactory() {
        if (factoryInstance == null) {
            factoryInstance = new DummyEntityFactory();
        }

        return factoryInstance;
    }

    public synchronized DummyEntity createDummyEntity() {
        DummyEntity dummyEntity = new DummyEntity();
        return dummyEntity;
    }
}
