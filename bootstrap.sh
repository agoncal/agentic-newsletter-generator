#!/usr/bin/env bash

# This script bootstraps a set of Quarkus projects
mvn archetype:generate "-DgroupId=ai.agentic.newsletter.generator" "-DartifactId=commons" "-DarchetypeArtifactId=maven-archetype-quickstart" "-DarchetypeVersion=1.5" "-Dversion=1.0.0-SNAPSHOT" "-DjavaCompilerVersion=21" "-DinteractiveMode=false"

mvn io.quarkus:quarkus-maven-plugin:3.26.3:create \
    -DplatformVersion=3.26.3 \
    -DprojectGroupId=ai.agentic.newsletter.generator \
    -DprojectArtifactId=agent-statistics \
    -DclassName="ai.agentic.newslettergen.statistics.StatisticsResource" \
    -Dpath="statistics"

mvn io.quarkus:quarkus-maven-plugin:3.26.3:create \
    -DplatformVersion=3.26.3 \
    -DprojectGroupId=ai.agentic.newsletter.generator \
    -DprojectArtifactId=agent-release \
    -DclassName="ai.agentic.newslettergen.release.ReleaseResource" \
    -Dpath="releases"


mvn io.quarkus:quarkus-maven-plugin:3.26.3:create \
    -DplatformVersion=3.26.3 \
    -DprojectGroupId=ai.agentic.newsletter.generator \
    -DprojectArtifactId=agent-code-sample \
    -DclassName="ai.agentic.newslettergen.codesample.CodeSampleResource" \
    -Dpath="codesamples"

mvn io.quarkus:quarkus-maven-plugin:3.26.3:create \
    -DplatformVersion=3.26.3 \
    -DprojectGroupId=ai.agentic.newsletter.generator \
    -DprojectArtifactId=agent-reference \
    -DclassName="ai.agentic.newslettergen.reference.ReferenceResource" \
    -Dpath="references"

mvn io.quarkus:quarkus-maven-plugin:3.26.3:create \
    -DplatformVersion=3.26.3 \
    -DprojectGroupId=ai.agentic.newsletter.generator \
    -DprojectArtifactId=workflow-newsletter \
    -DclassName="ai.agentic.newslettergen.workflow.NewsletterResource" \
    -Dpath="workflow"

