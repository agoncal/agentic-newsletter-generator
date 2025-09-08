#!/usr/bin/env bash

# Execute this script to deploy the needed Azure AI Foundry resources to execute the application.
# For this, you need Azure CLI installed: https://learn.microsoft.com/cli/azure/install-azure-cli
# If already installed, check the version with `az --version` and make sure it is up to date with `az upgrade`

echo "Setting up environment variables..."
echo "----------------------------------"
PROJECT="hack2025agenticnews"
RESOURCE_GROUP="rg-$PROJECT"
LOCATION="swedencentral" # check https://learn.microsoft.com/azure/ai-foundry/reference/region-support
TAG="$PROJECT"
UNIQUE_IDENTIFIER=${GITHUB_USER:-$(whoami)}


echo "Logging in..."
echo "----------------------------------"
az login

echo "Creating the resource group..."
echo "------------------------------"
az group create \
  --name "$RESOURCE_GROUP" \
  --location "$LOCATION" \
  --tags system="$TAG"
