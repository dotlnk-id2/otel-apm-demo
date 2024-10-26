export OTEL_EXPORTER_OTLP_TRACES_ENDPOINT=http://127.0.0.1:4318/v1/traces
export OTEL_METRICS_EXPORTER=logging
export OTEL_LOGS_EXPORTER=logging
java -server -XX:+UseG1GC \
-Xms2g -Xmx2g -XX:MetaspaceSize=256m  -XX:MaxMetaspaceSize=256m  \
-XX:MaxGCPauseMillis=20 -XX:G1HeapRegionSize=1m -XX:G1MixedGCCountTarget=10 -XX:ParallelGCThreads=9 -XX:ConcGCThreads=9 -XX:+ParallelRefProcEnabled \
-Xloggc:./log/gc.log -javaagent:/opt/opentelemetry-javaagent.jar -Dotel.service.name=apm-dome-s1 -Dotel.javaagent.configuration-file=/opt/sdk-config.yaml \
-jar target/apm-demo-0.0.1-SNAPSHOT.jar
