locals {
  location               = "West Europe"
  resource_group_name    = "tracking_resources"
  cluster_name           = "tracking-aks-cluster" # AlNum + hyphen
  nsg_name               = "tracking_services_nsg"
  container_registry_url = "trackingacr.azurecr.io"
  vnet                   = "tracking_vnet"
  subnet                 = "tracking_subnet"
}

# AKS Cluster
resource "random_integer" "aks_id" {
  min = 1
  max = 50000
}

resource "azurerm_kubernetes_cluster" "tracking_aks" {
  name                = "${local.cluster_name}-${random_integer.aks_id.result}"
  location            = local.location
  resource_group_name = local.resource_group_name
  dns_prefix          = "example"

  network_profile {
    network_plugin = "azure"
  }

  default_node_pool {
    name            = "default"
    node_count      = 2
    vm_size         = "Standard_B2s"
    os_disk_size_gb = 64
    os_disk_type    = "Managed"
    vnet_subnet_id  = data.azurerm_subnet.tracking_subnet.id
  }

  service_principal {
    client_id     = var.client_id
    client_secret = var.client_secret
  }

  tags = {
    Environment = "Production"
  }
}
