package service;

import dao.CancelDao;
import dao.DummyDao;
import domain.CancelEntity;
import domain.CancelEntityFactory;
import domain.DummyEntity;
import domain.DummyEntityFactory;
import connector.SyncOutput;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PackageThread implements Runnable {

    public final byte[] data;
    public DummyEntity dummyEntity;
    public CancelEntity cancelEntity;
    public DummyDao dummyDao;
    public CancelDao cancelDao;
    public SyncOutput syncOutput;
    public int idPaketa;

    public PackageThread(byte[] bytes) {
        this.data = bytes;
        this.dummyEntity = DummyEntityFactory.getFactory().createDummyEntity();
        this.cancelEntity = CancelEntityFactory.getFactory().createCancelEntity();
        this.dummyDao = DummyDao.getInstance();
        this.cancelDao = CancelDao.getInstance();
        this.syncOutput = SyncOutput.getInstance();

        byte[] arr1 = {data[0], data[1], data[2], data[3]};
        ByteBuffer bb1 = ByteBuffer.wrap(arr1);
        bb1.order(ByteOrder.LITTLE_ENDIAN);
        this.idPaketa = bb1.getInt();
    }

    @Override
    public void run() {
        try {

        if(idPaketa == 1) {
            dummyEntity.setIdPaketa(idPaketa);
            byte[] arr2 = {data[4], data[5], data[6], data[7]};
            ByteBuffer bb2 = ByteBuffer.wrap(arr2);
            bb2.order(ByteOrder.LITTLE_ENDIAN);
            dummyEntity.setLen(bb2.getInt());

            byte[] arr3 = {data[8], data[9], data[10], data[11]};
            ByteBuffer bb3 = ByteBuffer.wrap(arr3);
            bb3.order(ByteOrder.BIG_ENDIAN);
            int id = bb3.getInt();
            dummyEntity.setId(id);

            byte[] arr4 = {data[12], data[13], data[14], data[15]};
            ByteBuffer bb4 = ByteBuffer.wrap(arr4);
            bb4.order(ByteOrder.LITTLE_ENDIAN);
            int delay = bb4.getInt();
            dummyEntity.setDelay(delay);

            System.out.println("Waiting for " + delay + " seconds ...");
            dummyEntity.setExpired(true);
            Thread.sleep(delay*1000);
            syncOutput.write(data);
            dummyEntity.setExpired(false);
            dummyDao.saveDummy(dummyEntity);
            System.out.println("Done saving the DUMMY package with id: " + id);

        } else if (idPaketa == 2) {
            byte[] arr2 = {data[4], data[5], data[6], data[7]};
            ByteBuffer bb2 = ByteBuffer.wrap(arr2);
            bb2.order(ByteOrder.LITTLE_ENDIAN);
            cancelEntity.setLen(bb2.getInt());

            byte[] arr3 = {data[8], data[9], data[10], data[11]};
            ByteBuffer bb3 = ByteBuffer.wrap(arr3);
            bb3.order(ByteOrder.BIG_ENDIAN);
            int id = bb3.getInt();
            cancelEntity.setId(id);
            syncOutput.write(data);
            cancelDao.saveCancel(cancelEntity);
            System.out.println("Done saving the CANCEL package with id: " + id);
        }
        } catch (IOException | InterruptedException e) {
            if(idPaketa == 1) {
                dummyDao.saveDummy(dummyEntity);
                System.out.println("Unable to send the DUMMY package, saved to database as expired!");
            } else if(idPaketa == 2) {
                cancelDao.saveCancel(cancelEntity);
            }
        }
    }

    }

