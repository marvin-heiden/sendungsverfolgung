# Local Variables
locals {
  location            = "West Europe"
  resource_group_name = "tracking_resources"
  registry_name       = "trackingACR"
}

# Container Registry
resource "azurerm_container_registry" "acr" {
  name                = local.registry_name
  resource_group_name = local.resource_group_name
  location            = local.location
  sku                 = "Premium"
  admin_enabled       = false
}
