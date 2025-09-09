#!/usr/bin/env bash

# Execute this script to deploy the needed Azure AI Foundry resources to execute the application.
# For this, you need Azure CLI installed: https://learn.microsoft.com/cli/azure/install-azure-cli
# If already installed, check the version with `az --version` and make sure it is up to date with `az upgrade`
# Check the resource providers that are installed with `az provider list --query "[?registrationState=='Registered'].{Namespace:namespace,State:registrationState}" --output table`
# Register the CognitiveServices provider if it's not registered with `az provider register --namespace 'Microsoft.CognitiveServices'`


echo "Setting up environment variables..."
echo "----------------------------------"
PROJECT="hack2025agenticnews"
RESOURCE_GROUP="rg-$PROJECT"
LOCATION="swedencentral" # check https://learn.microsoft.com/azure/ai-foundry/reference/region-support
TAG="$PROJECT"
UNIQUE_IDENTIFIER=${GITHUB_USER:-$(whoami)}
COGNITIVE_SERVICES_NAME="ai-$PROJECT"
# Agent Code Sample
AGENT_CODE_SAMPLE_NAME="agent-code-sample"
AGENT_CODE_SAMPLE_MODEL_FORMAT="Microsoft"
AGENT_CODE_SAMPLE_MODEL_NAME="Phi-4"
AGENT_CODE_SAMPLE_MODEL_VERSION="7"
AGENT_CODE_SAMPLE_SKU_CAPACITY="1"
AGENT_CODE_SAMPLE_SKU_NAME="GlobalStandard"


echo "Logging in..."
echo "----------------------------------"
az login
az account show


echo "Creating the resource group..."
echo "------------------------------"
az group create \
  --name "$RESOURCE_GROUP" \
  --location "$LOCATION" \
  --tags system="$TAG"


echo "Creating Cognitive Service..."
echo "------------------------------"
# Check the SKUs with `az cognitiveservices account list-skus --location "$LOCATION" --output table`
az cognitiveservices account create \
  --resource-group "$RESOURCE_GROUP" \
  --location "$LOCATION" \
  --name "$COGNITIVE_SERVICES_NAME" \
  --custom-domain "$COGNITIVE_SERVICES_NAME" \
  --kind AIServices \
  --sku S0

COGNITIVE_SERVICES_KEY=$(az cognitiveservices account keys list \
  --name "$COGNITIVE_SERVICES_NAME" \
  --resource-group "$RESOURCE_GROUP" \
  --query "key1" \
  --output tsv)
echo "$COGNITIVE_SERVICES_KEY"

COGNITIVE_SERVICES_URL=$(az cognitiveservices account show \
  --name "$COGNITIVE_SERVICES_NAME" \
  --resource-group "$RESOURCE_GROUP" \
  --query "properties.endpoint" \
  --output tsv)
echo "$COGNITIVE_SERVICES_URL"

  
echo "Checking all the available models..."
echo "------------------------------"
az cognitiveservices account list-models \
  --resource-group "$RESOURCE_GROUP" \
  --name "$COGNITIVE_SERVICES_NAME" \
  --query "sort_by(@, &format)[].{Format:format,Name:name,Version:version,Sku:skus[0].name,Capacity:skus[0].capacity.default}" \
  --output table


echo "Deploying the model for the Agent Code Sample..."
echo "------------------------------"
az cognitiveservices account deployment create \
  --resource-group "$RESOURCE_GROUP" \
  --name "$COGNITIVE_SERVICES_NAME" \
  --deployment-name "$AGENT_CODE_SAMPLE_NAME" \
  --model-format "$AGENT_CODE_SAMPLE_MODEL_FORMAT" \
  --model-name "$AGENT_CODE_SAMPLE_MODEL_NAME" \
  --model-version "$AGENT_CODE_SAMPLE_MODEL_VERSION" \
  --sku-capacity "$AGENT_CODE_SAMPLE_SKU_CAPACITY" \
  --sku-name "$AGENT_CODE_SAMPLE_SKU_NAME"


