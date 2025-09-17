package ai.agentic.newslettergen.release;

import dev.langchain4j.agentic.AgenticServices;

import static ai.agentic.newslettergen.release.ReleaseAgent.releaseModel;

public class ReleaseResource {

    public static void main(final String[] args) {
        final var releaseAgent = AgenticServices.agentBuilder(ReleaseAgent.class)
            .chatModel(releaseModel())
            .outputName("releaseNotes")
            .build();

        final var releaseAgentData = releaseAgent.write(RELEASE_NOTES, "2.4.4");

        System.out.println(releaseAgentData);
    }

    private static final String RELEASE_NOTES = """
        ## [v2.4.4](https://dev.azure.com/msazuredev/CoreTech%20Security%20and%20Biometrics/_git/oc-service-media-connector-n?version=GBrelease%2F2.4.4) (TBD)
        ### What's New
        
        #### Features:
        - [2242100]: [MC] update media connector to support using ipstation for resume recording
        - [5503855]: [All] Spring boot upgrade 3.5.0 impacts on management endpoints
        - [5545504]: [MC] when MC receives duplicate start recordings in CiscoCube mode
        - [5548527]: NullPointerException in RecordingUdpChannel when RCC in CiscoCube
        #### Fixes:
        #### Parent Pom version: v3.0.21
        #### Connector Common version: v1.14.3
        #### Recorder Proto version: 1.9.1
        
        ## [v2.4.3](https://dev.azure.com/msazuredev/CoreTech%20Security%20and%20Biometrics/_git/oc-service-media-connector-n?version=GBrelease%2F2.4.3) (2025-08-1)
        ### What's New
        
        #### Features:
        #### Fixes:
        - [2215664]: [OnPrem] Defender: spring-web (Medium)
        - [SECURITY] Bump org.apache.tomcat.embed:tomcat-embed-core from 10.1.35 to 10.1.42 in /service
        
        #### Parent Pom version: v3.0.20
        #### Connector Common version: v1.14.2
        #### Recorder Proto version: 1.8.1
        
        ## [v2.4.2](https://dev.azure.com/msazuredev/CoreTech%20Security%20and%20Biometrics/_git/oc-service-media-connector-n?version=GBrelease%2F2.4.2) (2025-04-16)
        
        ### What's New
        
        #### Features:
        #### Fixes:
        - [1023897]: Create NOTICE file for Open Source used in Nuance Connectors Self Hosted
        - [1953182]: [ALL] remove threadPoolSize in all application yml files
        - [1543295]: [All] Replace default value for OAUTH2_TOKEN_URI
        - [1593208]: [MC] Stop sending pause/resume recording command to siprec if another is already completed
        - [1907591]: [MC] Allow the new ROC handling to work in NON Cisco Cube mode
        - [2048319]: [Nusenda] Media connector onPrem did not restart itself after stopping to receive mm status pings
        
        #### Config Changes:
        - [1953182]: removal of threadPoolSize
        """;
}
