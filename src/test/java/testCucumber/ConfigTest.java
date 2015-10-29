package testCucumber;

import java.util.concurrent.Semaphore;

import org.ramon.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;



public class ConfigTest {

    Semaphore semaphore  = new Semaphore(1);
    private ConfigurableApplicationContext context = null;
    
    public synchronized void setUp() throws InterruptedException {
        semaphore.acquire();
        if(context == null){
            context = SpringApplication.run(Application.class);
        }
    }
    
    public synchronized void setDown() {
        if(context != null){
            SpringApplication.exit(context);
            context = null;            
        }
        semaphore.release();
    }
}