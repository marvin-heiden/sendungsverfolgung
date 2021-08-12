# Local Variables
locals {
  location = "West Europe"
  resource_group_name = "tracking_resources"
}

# Resource Group
resource "azurerm_resource_group" "tracking_resources" {
  name     = local.resource_group_name
  location = local.location
}
