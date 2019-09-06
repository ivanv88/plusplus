package main;

import connector.PlusPlusConnector;
import connector.SyncInput;
import dao.DummyDao;
import domain.DummyEntity;
import init.PlusPlusInit;
import service.PackageThread;
import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public class Main {

        public static void main(String[] args) {
            PlusPlusInit.getInstance().createSessionFactory();
            DummyDao dummyDao = DummyDao.getInstance();
            List<DummyEntity> expired = dummyDao.getExpiredDummies();

            if(expired.isEmpty()){
                System.out.println("No expired packages from the LAST RUN!");
            } else {
                System.out.println();
                System.out.println("The following packages from the LAST RUN expired but were not sent back to the server!");
                System.out.println();
                System.out.println("ID_PAKETA \t\t LEN \t\t ID \t\t DELAY \t\t EXPIRED");
            }
            for (DummyEntity dummy : expired) {
                System.out.println("\t" + dummy.getIdPaketa() + "\t\t\t" + dummy.getLen() + "\t\t" + dummy.getId() + "\t\t\t" + dummy.getDelay() + "\t\t\t" + dummy.isExpired());
                dummy.setSeen(true);
                dummyDao.updateDummy(dummy);
                }

            System.out.println("Enter any text to connect to the server!");
            Scanner scanner = new Scanner(System.in);
            String s = scanner.next();

            try {
                PlusPlusInit.getInstance().appInit("matilda.plusplus.rs", 4000);
                System.out.println("Connected to server ...");
                InputStream in = SyncInput.getInstance().getInputStream();
                ExecutorService pool = Executors.newFixedThreadPool(10);
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    try {
                        pool.shutdownNow();
                        Thread.sleep(3000);
                        System.out.println("Thread pool shutdown");
                        System.out.println("Application safe shutdown");
                    } catch (InterruptedException e) {
                        System.err.println("Shutdown error");
                    }
                }));
             int counter = 1;
            while(!PlusPlusConnector.getInstance().getSocket().isClosed()) {
                byte[] data = new byte[16];
                int count = in.read(data);

                System.out.println("Task number: " + counter);

                Runnable runnable = new PackageThread(data);
                pool.execute(runnable);

                System.out.println();
                counter++;
            }
            pool.shutdown();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (RejectedExecutionException e) {
                System.err.println("Thread executor has been shut down, new task rejected!");
            }
        }
}
