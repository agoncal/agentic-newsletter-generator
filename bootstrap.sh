#!/usr/bin/env bash

# This script bootstraps a set of Quarkus projects
mvn archetype:generate "-DgroupId=ai.agentic.newsletter.generator" "-DartifactId=commons" "-DarchetypeArtifactId=maven-archetype-quickstart" "-DarchetypeVersion=1.5" "-Dversion=1.0.0-SNAPSHOT" "-DjavaCompilerVersion=21" "-DinteractiveMode=false"

mvn io.quarkus:quarkus-maven-plugin:3.24.4:create \
    -DplatformVersion=3.24.4 \
    -DprojectGroupId=ai.agentic.newsletter.generator \
    -DprojectArtifactId=statistics-agent \
    -DclassName="ai.agentic.newslettergen.statistics.StatisticsAgent" \
    -Dpath="statistics"

mvn io.quarkus:quarkus-maven-plugin:3.24.4:create \
    -DplatformVersion=3.24.4 \
    -DprojectGroupId=ai.agentic.newsletter.generator \
    -DprojectArtifactId=release-agent \
    -DclassName="ai.agentic.newslettergen.release.ReleaseAgent" \
    -Dpath="releases"


mvn io.quarkus:quarkus-maven-plugin:3.24.4:create \
    -DplatformVersion=3.24.4 \
    -DprojectGroupId=ai.agentic.newsletter.generator \
    -DprojectArtifactId=code-sample-agent \
    -DclassName="ai.agentic.newslettergen.codesample.CodeSampleAgent" \
    -Dpath="codesamples"

mvn io.quarkus:quarkus-maven-plugin:3.24.4:create \
    -DplatformVersion=3.24.4 \
    -DprojectGroupId=ai.agentic.newsletter.generator \
    -DprojectArtifactId=reference-agent \
    -DclassName="ai.agentic.newslettergen.reference.ReferenceAgent" \
    -Dpath="references"

