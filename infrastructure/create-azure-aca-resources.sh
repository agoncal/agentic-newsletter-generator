#!/usr/bin/env bash

# Register the ContainerRegistry provider if it's not registered with `az provider register --namespace 'Microsoft.ContainerRegistry'`
# Register the Insights provider if it's not registered with `az provider register --namespace 'Microsoft.Insights'`

source ./azure-setup-env-var.sh

#printf "\n%s\n" "Logging in..."
#printf "%s\n"   "-------------"
#az login

REGISTRY_URL=$(az acr show \
  --resource-group "$RESOURCE_GROUP" \
  --name "$REGISTRY" \
  --query "loginServer" \
  --output tsv)

NEWSLETTER_GENERATOR_IMAGE="${REGISTRY_URL}/${NEWSLETTER_GENERATOR_APP}:${IMAGES_TAG}"

printf "\n%s\n" "Creating Newsletter Generator App in Azure Container Apps..."
printf "%s\n"   "------------------------------------------------------------"
az containerapp create \
  --resource-group "$RESOURCE_GROUP" \
  --tags system="$TAG" application="$NEWSLETTER_GENERATOR_APP" \
  --image "$NEWSLETTER_GENERATOR_IMAGE" \
  --name "$NEWSLETTER_GENERATOR_APP" \
  --environment "$CONTAINERAPPS_ENVIRONMENT" \
  --ingress external \
  --target-port 8083 \
  --min-replicas 0 \
  --env-vars QUARKUS_HIBERNATE_ORM_DATABASE_GENERATION=validate \
             QUARKUS_HIBERNATE_ORM_SQL_LOAD_SCRIPT=no-file \
             QUARKUS_DATASOURCE_USERNAME="$POSTGRES_DB_ADMIN" \
             QUARKUS_DATASOURCE_PASSWORD="$POSTGRES_DB_PWD" \
             QUARKUS_DATASOURCE_REACTIVE_URL="$HEROES_DB_CONNECT_STRING"


printf "\n%s\n" "Retrieving the URL of the Newsletter Generator App..."
printf "%s\n"   "-----------------------------------------------------"
NEWSLETTER_GENERATOR_URL="https://$(az containerapp ingress show \
    --resource-group "$RESOURCE_GROUP" \
    --name "$NEWSLETTER_GENERATOR_APP" \
    --output json | jq -r .fqdn)"
printf "NEWSLETTER_GENERATOR_URL=%s\n" "$NEWSLETTER_GENERATOR_URL"


printf "\n%s\n" "Retrieving Logs of the Newsletter Generator App..."
printf "%s\n"   "--------------------------------------------------"
az monitor log-analytics query \
  --workspace $LOG_ANALYTICS_WORKSPACE_CLIENT_ID \
  --analytics-query "ContainerAppConsoleLogs_CL | where ContainerAppName_s == '$NEWSLETTER_GENERATOR_APP' | project ContainerAppName_s, Log_s, TimeGenerated " \
  --output table
