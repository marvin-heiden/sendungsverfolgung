#!/bin/bash



# Check all needed parameters were provided
if [[ $# -ne 4 ]]
then
  echo "Usage: source ./01_set_environment_variables.sh <PARAMS>"
  echo "  <PARAMS> must be in the following order:"
  echo "      <subscription_id>"
  echo "      <client_id>"
  echo "      <client_secret>"
  echo "      <tenant_id>"
  echo ""
  echo "NOTE: Use the following azure cli commands to find the right subscription id and to login to az first:"
  echo "  az login                                          => Login to azure cli."
  echo "  az account list --output table                    => Find the right subscription id"
  return 1
fi

# Disable echoing the output of this script
set +x

# Enable all export mode, to export all environment variables from sourced shell script to the shell executing this script
set -o allexport

# Azure Subscription
export ARM_SUBSCRIPTION_ID=$1
export TF_VAR_subscription_id=$1
export ARM_CLIENT_ID=$2
export TF_VAR_client_id=$2
export ARM_CLIENT_SECRET=$3
export TF_VAR_client_secret=$3
export ARM_TENANT_ID=$4
export TF_VAR_tenant_id=$4

# Disable all export mode
set +o allexport
