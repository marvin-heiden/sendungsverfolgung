terraform {
  required_providers {
    local = {
      source  = "hashicorp/local"
      version = "2.0.0"
    }
    random = {
      source  = "hashicorp/random"
      version = "3.0.1"
    }
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "2.44.0"
    }
    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = "2.0.1"
    }
    helm = {
      source = "hashicorp/helm"
      version = "2.2.0"
    }
  }
}

provider "azurerm" {
  skip_provider_registration = true
  features {}

  subscription_id = var.subscription_id
  client_id       = var.client_id
  client_secret   = var.client_secret
  tenant_id       = var.tenant_id
}

provider "kubernetes" {
  config_path = local_file.kube_config_file.filename
}

provider "helm" {
  kubernetes {
    config_path = format("%s/%s", path.root, "kube_config.yaml")
  }
}