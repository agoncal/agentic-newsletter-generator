#!/usr/bin/env bash

# Execute this script to deploy the needed Azure AI Foundry resources to execute the application.
# For this, you need Azure CLI installed: https://learn.microsoft.com/cli/azure/install-azure-cli
# If already installed, check the version with `az --version` and make sure it is up to date with `az upgrade`
# Check the resource providers that are installed with `az provider list --query "[?registrationState=='Registered'].{Namespace:namespace,State:registrationState}" --output table`
# Register the CognitiveServices provider if it's not registered with `az provider register --namespace 'Microsoft.CognitiveServices'`


printf "%s\n" "-----------------------------------"
printf "%s\n" "Setting up environment variables..."
printf "%s\n" "-----------------------------------"
export UNIQUE_IDENTIFIER=${GITHUB_USER:-$(whoami)}
export PROJECT="hack2025agenticnews$UNIQUE_IDENTIFIER"
export RESOURCE_GROUP="rg-$PROJECT"
export LOCATION="swedencentral" # check https://learn.microsoft.com/azure/ai-foundry/reference/region-support
export TAG="$PROJECT"
export AZURE_AI_FOUNDRY_NAME="ai-$PROJECT"

# Agent Code Sample Writer
export AGENT_CODE_SAMPLE="agent-code-sample"
export AGENT_CODE_SAMPLE_DEPLOYMENT="$AGENT_CODE_SAMPLE-model"
export AGENT_CODE_SAMPLE_MODEL_FORMAT="Microsoft"
export AGENT_CODE_SAMPLE_MODEL_NAME="Phi-4"
export AGENT_CODE_SAMPLE_MODEL_VERSION="7"
export AGENT_CODE_SAMPLE_SKU_CAPACITY="1"
export AGENT_CODE_SAMPLE_SKU_NAME="GlobalStandard"

# Agent Reference Writer
export AGENT_REFERENCE="agent-reference"
export AGENT_REFERENCE_DEPLOYMENT="$AGENT_REFERENCE-model"
export AGENT_REFERENCE_MODEL_FORMAT="Microsoft"
export AGENT_REFERENCE_MODEL_NAME="Phi-4"
export AGENT_REFERENCE_MODEL_VERSION="7"
export AGENT_REFERENCE_SKU_CAPACITY="1"
export AGENT_REFERENCE_SKU_NAME="GlobalStandard"

# Agent Release Writer
export AGENT_RELEASE="agent-release"
export AGENT_RELEASE_DEPLOYMENT="$AGENT_RELEASE-model"
export AGENT_RELEASE_MODEL_FORMAT="OpenAI"
export AGENT_RELEASE_MODEL_NAME="gpt-5-mini"
export AGENT_RELEASE_MODEL_VERSION="2025-08-07"
export AGENT_RELEASE_SKU_CAPACITY="10"
export AGENT_RELEASE_SKU_NAME="GlobalStandard"

# Agent Statistics Writer
export AGENT_STATISTICS="agent-statistics"
export AGENT_STATISTICS_DEPLOYMENT="$AGENT_STATISTICS-model"
export AGENT_STATISTICS_MODEL_FORMAT="Microsoft"
export AGENT_STATISTICS_MODEL_NAME="Phi-4"
export AGENT_STATISTICS_MODEL_VERSION="7"
export AGENT_STATISTICS_SKU_CAPACITY="1"
export AGENT_STATISTICS_SKU_NAME="GlobalStandard"

# Agent Newsletter Editor
export AGENT_NEWSLETTER="agent-newsletter"
export AGENT_NEWSLETTER_DEPLOYMENT="$AGENT_NEWSLETTER-model"
export AGENT_NEWSLETTER_MODEL_FORMAT="Microsoft"
export AGENT_NEWSLETTER_MODEL_NAME="Phi-4"
export AGENT_NEWSLETTER_MODEL_VERSION="7"
export AGENT_NEWSLETTER_SKU_CAPACITY="1"
export AGENT_NEWSLETTER_SKU_NAME="GlobalStandard"

# Setting verbose to true will display extra information
verbose=false

#printf "\n%s\n" "Logging in..."
#printf "%s\n"   "-------------"
#az login


if [ "$verbose" = true ]; then  
    printf "\n%s\n" "Checking the Azure account..."
    printf "%s\n"   "-----------------------------"
    az account show
fi


printf "\n%s\n" "Creating the resource group..."
printf "%s\n"   "------------------------------"
az group create \
  --name "$RESOURCE_GROUP" \
  --location "$LOCATION" \
  --tags system="$TAG"


if [ "$verbose" = true ]; then  
    printf "\n%s\n" "Checking the SKUs..."
    printf "%s\n"   "--------------------"
    az cognitiveservices account list-skus --location "$LOCATION" --output table
fi


printf "\n%s\n" "Creating Azure AI Foundry service..."
printf "%s\n"   "------------------------------------"
az cognitiveservices account create \
  --resource-group "$RESOURCE_GROUP" \
  --location "$LOCATION" \
  --name "$AZURE_AI_FOUNDRY_NAME" \
  --custom-domain "$AZURE_AI_FOUNDRY_NAME" \
  --kind AIServices \
  --sku S0

if [ "$verbose" = true ]; then  
    printf "\n%s\n" "Checking all the available models..."
    printf "%s\n"   "------------------------------------"
    az cognitiveservices account list-models \
      --resource-group "$RESOURCE_GROUP" \
      --name "$AZURE_AI_FOUNDRY_NAME" \
      --query "sort_by(@, &format)[].{Format:format,Name:name,Version:version,Sku:skus[0].name,Capacity:skus[0].capacity.default}" \
      --output table
fi


printf "\n%s\n" "Deploying the model for the Agent Code Sample..."
printf "%s\n"   "------------------------------------------------"
az cognitiveservices account deployment create \
  --resource-group "$RESOURCE_GROUP" \
  --name "$AZURE_AI_FOUNDRY_NAME" \
  --deployment-name "$AGENT_CODE_SAMPLE_DEPLOYMENT" \
  --model-format "$AGENT_CODE_SAMPLE_MODEL_FORMAT" \
  --model-name "$AGENT_CODE_SAMPLE_MODEL_NAME" \
  --model-version "$AGENT_CODE_SAMPLE_MODEL_VERSION" \
  --sku-capacity "$AGENT_CODE_SAMPLE_SKU_CAPACITY" \
  --sku-name "$AGENT_CODE_SAMPLE_SKU_NAME"


printf "\n%s\n" "Deploying the model for the Agent Reference..."
printf "%s\n"   "------------------------------------------------"
az cognitiveservices account deployment create \
  --resource-group "$RESOURCE_GROUP" \
  --name "$AZURE_AI_FOUNDRY_NAME" \
  --deployment-name "$AGENT_REFERENCE_DEPLOYMENT" \
  --model-format "$AGENT_REFERENCE_MODEL_FORMAT" \
  --model-name "$AGENT_REFERENCE_MODEL_NAME" \
  --model-version "$AGENT_REFERENCE_MODEL_VERSION" \
  --sku-capacity "$AGENT_REFERENCE_SKU_CAPACITY" \
  --sku-name "$AGENT_REFERENCE_SKU_NAME"


printf "\n%s\n" "Deploying the model for the Agent Reference..."
printf "%s\n"   "------------------------------------------------"
az cognitiveservices account deployment create \
  --resource-group "$RESOURCE_GROUP" \
  --name "$AZURE_AI_FOUNDRY_NAME" \
  --deployment-name "$AGENT_RELEASE_DEPLOYMENT" \
  --model-format "$AGENT_RELEASE_MODEL_FORMAT" \
  --model-name "$AGENT_RELEASE_MODEL_NAME" \
  --model-version "$AGENT_RELEASE_MODEL_VERSION" \
  --sku-capacity "$AGENT_RELEASE_SKU_CAPACITY" \
  --sku-name "$AGENT_RELEASE_SKU_NAME"


printf "\n%s\n" "Deploying the model for the Agent Reference..."
printf "%s\n"   "------------------------------------------------"
az cognitiveservices account deployment create \
  --resource-group "$RESOURCE_GROUP" \
  --name "$AZURE_AI_FOUNDRY_NAME" \
  --deployment-name "$AGENT_STATISTICS_DEPLOYMENT" \
  --model-format "$AGENT_STATISTICS_MODEL_FORMAT" \
  --model-name "$AGENT_STATISTICS_MODEL_NAME" \
  --model-version "$AGENT_STATISTICS_MODEL_VERSION" \
  --sku-capacity "$AGENT_STATISTICS_SKU_CAPACITY" \
  --sku-name "$AGENT_STATISTICS_SKU_NAME"


printf "\n%s\n" "Deploying the model for the Agent Reference..."
printf "%s\n"   "------------------------------------------------"
az cognitiveservices account deployment create \
  --resource-group "$RESOURCE_GROUP" \
  --name "$AZURE_AI_FOUNDRY_NAME" \
  --deployment-name "$AGENT_NEWSLETTER_DEPLOYMENT" \
  --model-format "$AGENT_NEWSLETTER_MODEL_FORMAT" \
  --model-name "$AGENT_NEWSLETTER_MODEL_NAME" \
  --model-version "$AGENT_NEWSLETTER_MODEL_VERSION" \
  --sku-capacity "$AGENT_NEWSLETTER_SKU_CAPACITY" \
  --sku-name "$AGENT_NEWSLETTER_SKU_NAME"


printf "\n%s\n" "Displaying environment variables..."
printf "%s\n"   "-----------------------------------"
export AZURE_AI_FOUNDRY_KEY=$(az cognitiveservices account keys list \
  --name "$AZURE_AI_FOUNDRY_NAME" \
  --resource-group "$RESOURCE_GROUP" \
  --query "key1" \
  --output tsv)
printf "\n%s\n" "$AZURE_AI_FOUNDRY_KEY"

# Appending `models` at the end of the URL
export AZURE_AI_FOUNDRY_ENDPOINT=$(az cognitiveservices account show \
  --name "$AZURE_AI_FOUNDRY_NAME" \
  --resource-group "$RESOURCE_GROUP" \
  --query "properties.endpoint" \
  --output tsv)models
printf "%s\n" "$AZURE_AI_FOUNDRY_ENDPOINT"