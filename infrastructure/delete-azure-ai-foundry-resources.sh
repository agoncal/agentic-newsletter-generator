#!/usr/bin/env bash

# Execute this script to delete the Azure AI Foundry resources used by the application.
# For this, you need Azure CLI installed: https://learn.microsoft.com/cli/azure/install-azure-cli


echo "----------------------------------"
echo "Setting up environment variables..."
echo "----------------------------------"
PROJECT="hack2025agenticnews"
RESOURCE_GROUP="rg-$PROJECT"

echo "Deleting the resource group..."
echo "------------------------------"
az group delete \
  --name "$RESOURCE_GROUP" \
  --yes

