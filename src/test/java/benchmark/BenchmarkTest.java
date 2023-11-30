package benchmark;

import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;


public class BenchmarkTest {
    public static void main(String[] args) throws Exception {

            Options opt = new OptionsBuilder()
                    .include(VDBenchmark.class.getSimpleName())
                    .measurementIterations(1)
                    .warmupIterations(0)
                    .shouldFailOnError(true)
                  //  .addProfiler(GCProfiler.class)
                    .build();
            new Runner(opt).run();

    }
}


