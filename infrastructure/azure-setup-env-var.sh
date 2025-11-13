#!/usr/bin/env bash

# Check Azure naming conventions here: https://learn.microsoft.com/en-us/azure/cloud-adoption-framework/ready/azure-best-practices/resource-abbreviations

printf "%s\n" "-----------------------------------"
printf "%s\n" "Setting up environment variables..."
printf "%s\n" "-----------------------------------"
export UNIQUE_IDENTIFIER=${GITHUB_USER:-$(whoami)}
export PROJECT="agenticnewsletter"
export RESOURCE_GROUP="rg-$PROJECT"
export LOCATION="swedencentral" # check https://learn.microsoft.com/azure/ai-foundry/reference/region-support
export TAG="$PROJECT"
# Azure AI Foundry
export AZURE_AI_FOUNDRY_NAME="ai-$PROJECT"
# Azure Container Apps
export CONTAINERAPPS_ENVIRONMENT="cae-$PROJECT"
export LOG_ANALYTICS_WORKSPACE="log-$PROJECT"
export REGISTRY="cr$PROJECT"
export IMAGES_TAG="1.0"
# Newsletter Generator App
export NEWSLETTER_GENERATOR_APP="newsletter-generator-app"
export NEWSLETTER_GENERATOR_IMAGE="${REGISTRY_URL}/${NEWSLETTER_GENERATOR_APP}:${IMAGES_TAG}"

# Agent Code Sample Writer (writes Java code)
export AGENT_CODE_SAMPLE="agent-code-sample-writer"
export AGENT_CODE_SAMPLE_DEPLOYMENT="$AGENT_CODE_SAMPLE-model"
export AGENT_CODE_SAMPLE_MODEL_FORMAT="OpenAI"
export AGENT_CODE_SAMPLE_MODEL_NAME="o4-mini"
export AGENT_CODE_SAMPLE_MODEL_VERSION="2025-04-16"
export AGENT_CODE_SAMPLE_SKU_CAPACITY="10"
export AGENT_CODE_SAMPLE_SKU_NAME="GlobalStandard"

# Agent Reference Writer (searches the web)
export AGENT_REFERENCE="agent-reference-writer"
export AGENT_REFERENCE_DEPLOYMENT="$AGENT_REFERENCE-model"
export AGENT_REFERENCE_MODEL_FORMAT="Microsoft"
export AGENT_REFERENCE_MODEL_NAME="Phi-4"
export AGENT_REFERENCE_MODEL_VERSION="7"
export AGENT_REFERENCE_SKU_CAPACITY="1"
export AGENT_REFERENCE_SKU_NAME="GlobalStandard"

# Agent Release Writer (uses tools, needs summarization)
export AGENT_RELEASE="agent-release-writer"
export AGENT_RELEASE_DEPLOYMENT="$AGENT_RELEASE-model"
export AGENT_RELEASE_MODEL_FORMAT="OpenAI"
export AGENT_RELEASE_MODEL_NAME="gpt-5-mini"
export AGENT_RELEASE_MODEL_VERSION="2025-08-07"
export AGENT_RELEASE_SKU_CAPACITY="10"
export AGENT_RELEASE_SKU_NAME="GlobalStandard"

# Agent Statistics Writer (uses tools)
export AGENT_STATISTICS="agent-statistics-writer"
export AGENT_STATISTICS_DEPLOYMENT="$AGENT_STATISTICS-model"
export AGENT_STATISTICS_MODEL_FORMAT="OpenAI"
export AGENT_STATISTICS_MODEL_NAME="gpt-5-mini"
export AGENT_STATISTICS_MODEL_VERSION="2025-08-07"
export AGENT_STATISTICS_SKU_CAPACITY="10"
export AGENT_STATISTICS_SKU_NAME="GlobalStandard"

# Agent Newsletter Editor
export AGENT_NEWSLETTER="agent-newsletter-editor"
export AGENT_NEWSLETTER_DEPLOYMENT="$AGENT_NEWSLETTER-model"
export AGENT_NEWSLETTER_MODEL_FORMAT="OpenAI"
export AGENT_NEWSLETTER_MODEL_NAME="gpt-5-chat"
export AGENT_NEWSLETTER_MODEL_VERSION="2025-08-07"
export AGENT_NEWSLETTER_SKU_CAPACITY="10"
export AGENT_NEWSLETTER_SKU_NAME="GlobalStandard"

# Setting verbose to true will display extra information
export verbose=false
