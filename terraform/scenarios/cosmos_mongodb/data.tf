data "azurerm_subnet" "tracking_subnet" {
  name                 = local.subnet
  virtual_network_name = local.vnet
  resource_group_name  = local.resource_group_name
}