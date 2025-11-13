#!/usr/bin/env bash

# Register the ContainerRegistry provider if it's not registered with `az provider register --namespace 'Microsoft.ContainerRegistry'`
# Register the Insights provider if it's not registered with `az provider register --namespace 'Microsoft.Insights'`

source ./azure-setup-env-var.sh

#printf "\n%s\n" "Logging in..."
#printf "%s\n"   "-------------"
#az login


if [ "$verbose" = true ]; then  
    printf "\n%s\n" "Checking the SKUs..."
    printf "%s\n"   "--------------------"
    az cognitiveservices account list-skus --location "$LOCATION" --output table
fi


printf "\n%s\n" "Creating Log Analytics Workspace..."
printf "%s\n"   "-----------------------------------"
az monitor log-analytics workspace create \
  --resource-group "$RESOURCE_GROUP" \
  --location "$LOCATION" \
  --tags system="$TAG" \
  --name "$LOG_ANALYTICS_WORKSPACE"


printf "\n%s\n" "Retrieving Log Analytics Secrets..."
printf "%s\n"   "-----------------------------------"
export LOG_ANALYTICS_WORKSPACE_CLIENT_ID=$(az monitor log-analytics workspace show  \
  --resource-group "$RESOURCE_GROUP" \
  --workspace-name "$LOG_ANALYTICS_WORKSPACE" \
  --query customerId  \
  --output tsv | tr -d '[:space:]')
printf "LOG_ANALYTICS_WORKSPACE_CLIENT_ID=%s\n" "$LOG_ANALYTICS_WORKSPACE_CLIENT_ID"


export LOG_ANALYTICS_WORKSPACE_CLIENT_SECRET=$(az monitor log-analytics workspace get-shared-keys \
  --resource-group "$RESOURCE_GROUP" \
  --workspace-name "$LOG_ANALYTICS_WORKSPACE" \
  --query primarySharedKey \
  --output tsv | tr -d '[:space:]')
printf "LOG_ANALYTICS_WORKSPACE_CLIENT_SECRET=%s\n" "$LOG_ANALYTICS_WORKSPACE_CLIENT_SECRET"

printf "\n%s\n" "Creating Azure Container Registry..."
printf "%s\n"   "------------------------------------"
az acr create \
  --resource-group "$RESOURCE_GROUP" \
  --location "$LOCATION" \
  --tags system="$TAG" \
  --name "$REGISTRY" \
  --workspace "$LOG_ANALYTICS_WORKSPACE" \
  --sku Premium \
  --admin-enabled true \
  --public-network-enabled true

az acr update \
  --resource-group "$RESOURCE_GROUP" \
  --name "$REGISTRY" \
  --anonymous-pull-enabled true

printf "\n%s\n" "Retrieving Container Registry URL..."
printf "%s\n"   "-----------------------------------"
export REGISTRY_URL=$(az acr show \
                      --resource-group "$RESOURCE_GROUP" \
                      --name "$REGISTRY" \
                      --query "loginServer" \
                      --output tsv)
printf "REGISTRY_URL=%s\n" "$REGISTRY_URL"
