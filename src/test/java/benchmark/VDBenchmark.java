package benchmark;

import com.example.nbd.Nbd2023KmMwApplication;
import com.example.nbd.managers.virtualdevicemanagers.IVirtualDeviceManager;
import com.example.nbd.managers.virtualdevicemanagers.RedisVirtualDeviceManager;
import com.example.nbd.managers.virtualdevicemanagers.VirtualDeviceManager;
import com.example.nbd.repositories.VirtualDeviceRepository;
import org.openjdk.jmh.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.CacheManager;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class VDBenchmark {

    private ConfigurableApplicationContext context;
    private IVirtualDeviceManager iVirtualDeviceManager;

    private CacheManager cacheManager;

    private VirtualDeviceRepository virtualDeviceRepository;

    @Setup
    public void setContext() {
        context = SpringApplication.run(Nbd2023KmMwApplication.class);
        virtualDeviceRepository = context.getBean("virtualDeviceRepository",VirtualDeviceRepository.class);
        cacheManager = context.getBean(CacheManager.class);
        cacheManager.getCache("virtualDevices").clear();
        cacheManager.getCache("virtualDevice").clear();
        virtualDeviceRepository.deleteAll();
        iVirtualDeviceManager = context.getBean("virtualDeviceManager", VirtualDeviceManager.class);
        for (int i = 0; i < 500; i++) {
            iVirtualDeviceManager.addVirtualPhone(2,2,3,22);
        }
    }
    @Benchmark
    public void mongoTest() {
        iVirtualDeviceManager = context.getBean("virtualDeviceManager", VirtualDeviceManager.class);
        for (int i = 0; i < 100; i++) {
            iVirtualDeviceManager.findAllVirtualDevices();
        }
    }

    @Benchmark
    public void redisCacheTest() {
        iVirtualDeviceManager = context.getBean("redisVirtualDeviceManager", RedisVirtualDeviceManager.class);
        for (int i = 0; i < 100; i++) {
            iVirtualDeviceManager.findAllVirtualDevices();
        }
    }
    @TearDown
    public void close() {
        virtualDeviceRepository.deleteAll();
        cacheManager.getCache("virtualDevices").clear();
        cacheManager.getCache("virtualDevice").clear();
        context.close();
    }

}
