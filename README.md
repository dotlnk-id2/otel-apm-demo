```json
{
    "configurations": [
        {
            "type": "java",
            "name": "RunApplication",
            "request": "launch",
            "cwd": "${workspaceFolder}",
            "mainClass": "com.example.apm.ApmDemoApplication",
            "projectName": "apm-demo",
            "args": [
            ],
            "envFile": "${workspaceFolder}/.env",
            "vmArgs": [
                "-javaagent:opentelemetry-javaagent.jar",
                "-Dotel.service.name=apm-dome-s1",
                "-Dotel.javaagent.configuration-file=sdk-config.yaml",
                "-Dspring.jmx.enabled=false",
                "-Dio.netty.resolver.dns.defaultNameServerFallback=223.5.5.5,223.6.6.6"
            ]
        }
    ]
}
```

* settings.json
```json
{
    "java.compile.nullAnalysis.mode": "automatic",
    "java.configuration.updateBuildConfiguration": "interactive"
}
```