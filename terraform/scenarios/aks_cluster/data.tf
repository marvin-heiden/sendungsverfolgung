data "azurerm_virtual_network" "tracking_vnet" {
  name = local.vnet
  resource_group_name = local.resource_group_name
}

data "azurerm_subnet" "tracking_subnet" {
  name = local.subnet
  virtual_network_name = local.vnet
  resource_group_name = local.resource_group_name
}

# Traefik Service
data "kubernetes_service" "traefik" {
  depends_on = [
    azurerm_kubernetes_cluster.tracking_aks,
    module.traefik
  ]

  metadata {
    name      = "traefik"
    namespace = "default"
  }
}