package benchmark;

import com.example.nbd.Nbd2023KmMwApplication;
import com.example.nbd.managers.virtualdevicemanagers.IVirtualDeviceManager;
import com.example.nbd.managers.virtualdevicemanagers.RedisVirtualDeviceManager;
import com.example.nbd.managers.virtualdevicemanagers.VirtualDeviceManager;
import com.example.nbd.repositories.VirtualDeviceRepository;
import org.openjdk.jmh.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class VDBenchmark {

    private ConfigurableApplicationContext context;
    private IVirtualDeviceManager iVirtualDeviceManager;

    private VirtualDeviceRepository virtualDeviceRepository;

    @Setup
    public void setContext() {
        context = SpringApplication.run(Nbd2023KmMwApplication.class);
        virtualDeviceRepository = context.getBean("virtualDeviceRepository",VirtualDeviceRepository.class);
        virtualDeviceRepository.deleteAll();
    }
    @Benchmark
    public void mongoTest() {
        iVirtualDeviceManager = context.getBean("virtualDeviceManager", VirtualDeviceManager.class);
        for (int i = 0; i < 500; i++) {
            iVirtualDeviceManager.addVirtualPhone(2,2,3,22);
        }
        for (int i = 0; i < 100; i++) {
            iVirtualDeviceManager.findAllVirtualDevices();
        }
        virtualDeviceRepository.deleteAll();
    }

    @Benchmark
    public void redisCacheTest() {
        iVirtualDeviceManager = context.getBean("redisVirtualDeviceManager", RedisVirtualDeviceManager.class);
        for (int i = 0; i < 500; i++) {
            iVirtualDeviceManager.addVirtualPhone(2,2,3,22);
        }
        for (int i = 0; i < 100; i++) {
            iVirtualDeviceManager.findAllVirtualDevices();
        }
        virtualDeviceRepository.deleteAll();
    }
    @TearDown
    public void close() {
        context.close();
    }

}
