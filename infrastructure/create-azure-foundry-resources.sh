#!/usr/bin/env bash

# Execute this script to deploy the needed Azure AI Foundry resources to execute the application.
# For this, you need Azure CLI installed: https://learn.microsoft.com/cli/azure/install-azure-cli
# If already installed, check the version with `az --version` and make sure it is up to date with `az upgrade`
# Check the resource providers that are installed with `az provider list --query "[?registrationState=='Registered'].{Namespace:namespace,State:registrationState}" --output table`
# Register the CognitiveServices provider if it's not registered with `az provider register --namespace 'Microsoft.CognitiveServices'`

source ./azure-setup-env-var.sh

#printf "\n%s\n" "Logging in..."
#printf "%s\n"   "-------------"
#az login


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


printf "\n%s\n" "Deploying the model for the Agent Code Sample Writer..."
printf "%s\n"   "-------------------------------------------------------"
az cognitiveservices account deployment create \
  --resource-group "$RESOURCE_GROUP" \
  --name "$AZURE_AI_FOUNDRY_NAME" \
  --deployment-name "$AGENT_CODE_SAMPLE_DEPLOYMENT" \
  --model-format "$AGENT_CODE_SAMPLE_MODEL_FORMAT" \
  --model-name "$AGENT_CODE_SAMPLE_MODEL_NAME" \
  --model-version "$AGENT_CODE_SAMPLE_MODEL_VERSION" \
  --sku-capacity "$AGENT_CODE_SAMPLE_SKU_CAPACITY" \
  --sku-name "$AGENT_CODE_SAMPLE_SKU_NAME"


printf "\n%s\n" "Deploying the model for the Agent Reference Writer..."
printf "%s\n"   "-----------------------------------------------------"
az cognitiveservices account deployment create \
  --resource-group "$RESOURCE_GROUP" \
  --name "$AZURE_AI_FOUNDRY_NAME" \
  --deployment-name "$AGENT_REFERENCE_DEPLOYMENT" \
  --model-format "$AGENT_REFERENCE_MODEL_FORMAT" \
  --model-name "$AGENT_REFERENCE_MODEL_NAME" \
  --model-version "$AGENT_REFERENCE_MODEL_VERSION" \
  --sku-capacity "$AGENT_REFERENCE_SKU_CAPACITY" \
  --sku-name "$AGENT_REFERENCE_SKU_NAME"


printf "\n%s\n" "Deploying the model for the Agent Release Writer..."
printf "%s\n"   "---------------------------------------------------"
az cognitiveservices account deployment create \
  --resource-group "$RESOURCE_GROUP" \
  --name "$AZURE_AI_FOUNDRY_NAME" \
  --deployment-name "$AGENT_RELEASE_DEPLOYMENT" \
  --model-format "$AGENT_RELEASE_MODEL_FORMAT" \
  --model-name "$AGENT_RELEASE_MODEL_NAME" \
  --model-version "$AGENT_RELEASE_MODEL_VERSION" \
  --sku-capacity "$AGENT_RELEASE_SKU_CAPACITY" \
  --sku-name "$AGENT_RELEASE_SKU_NAME"


printf "\n%s\n" "Deploying the model for the Agent Statistics Writer..."
printf "%s\n"   "------------------------------------------------------"
az cognitiveservices account deployment create \
  --resource-group "$RESOURCE_GROUP" \
  --name "$AZURE_AI_FOUNDRY_NAME" \
  --deployment-name "$AGENT_STATISTICS_DEPLOYMENT" \
  --model-format "$AGENT_STATISTICS_MODEL_FORMAT" \
  --model-name "$AGENT_STATISTICS_MODEL_NAME" \
  --model-version "$AGENT_STATISTICS_MODEL_VERSION" \
  --sku-capacity "$AGENT_STATISTICS_SKU_CAPACITY" \
  --sku-name "$AGENT_STATISTICS_SKU_NAME"


printf "\n%s\n" "Deploying the model for the Agent Newsletter Editor..."
printf "%s\n"   "------------------------------------------------------"
az cognitiveservices account deployment create \
  --resource-group "$RESOURCE_GROUP" \
  --name "$AZURE_AI_FOUNDRY_NAME" \
  --deployment-name "$AGENT_NEWSLETTER_DEPLOYMENT" \
  --model-format "$AGENT_NEWSLETTER_MODEL_FORMAT" \
  --model-name "$AGENT_NEWSLETTER_MODEL_NAME" \
  --model-version "$AGENT_NEWSLETTER_MODEL_VERSION" \
  --sku-capacity "$AGENT_NEWSLETTER_SKU_CAPACITY" \
  --sku-name "$AGENT_NEWSLETTER_SKU_NAME"


printf "\n%s\n" "Retrieving Azure AI Foundray Variables..."
printf "%s\n"   "-----------------------------------------"
export AZURE_AI_FOUNDRY_KEY=$(az cognitiveservices account keys list \
  --name "$AZURE_AI_FOUNDRY_NAME" \
  --resource-group "$RESOURCE_GROUP" \
  --query "key1" \
  --output tsv)
printf "\nAZURE_AI_FOUNDRY_KEY=%s\n" "$AZURE_AI_FOUNDRY_KEY"

# Appending `models` at the end of the URL
export AZURE_AI_FOUNDRY_ENDPOINT=$(az cognitiveservices account show \
  --name "$AZURE_AI_FOUNDRY_NAME" \
  --resource-group "$RESOURCE_GROUP" \
  --query "properties.endpoint" \
  --output tsv)models
printf "AZURE_AI_FOUNDRY_ENDPOINT=%s\n" "$AZURE_AI_FOUNDRY_ENDPOINT"
