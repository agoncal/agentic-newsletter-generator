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
    printf "\n%s\n" "Checking the Azure account..."
    printf "%s\n"   "-----------------------------"
    az account show
fi


if [ "$verbose" = true ]; then  
    printf "\n%s\n" "Displaying Azure locations..."
    printf "%s\n"   "-----------------------------"
    az account list-locations \
      --query "sort_by([].{Name:name, DisplayName:displayName, RegionalDisplayName:regionalDisplayName}, &Name)" --output table
fi


printf "\n%s\n" "Creating the resource group..."
printf "%s\n"   "------------------------------"
az group create \
  --name "$RESOURCE_GROUP" \
  --location "$LOCATION" \
  --tags system="$TAG"
