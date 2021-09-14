# Terraform Setup
## Create App & Credentials

https://registry.terraform.io/providers/hashicorp/azurerm/latest/docs/guides/service_principal_client_secret#creating-a-service-principal-in-the-azure-portal


aks_cluster % az account set --subscription 43570659-1acd-4976-8e2e-1d5e91582b89
aks_cluster % az aks get-credentials --resource-group tracking_resources --name tracking-aks-cluster-22442