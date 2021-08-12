# Local Variables
locals {
  location               = "West Europe"
  resource_group_name    = "tracking_resources"
  enable_ddos_protection = false
  subnet_name            = "tracking_subnet"
}

# Vnet
resource "azurerm_virtual_network" "vnet" {
  name                = "tracking_vnet"
  location            = local.location
  resource_group_name = local.resource_group_name
  address_space       = ["30.0.0.0/24"]
  dns_servers         = ["168.63.129.16"] # Azure provided DNS - must be 168.63.129.16

  tags = {
    terraform = true
  }
}

# Subnet
resource "azurerm_subnet" "subnet" {
  name                 = local.subnet_name
  resource_group_name  = local.resource_group_name
  virtual_network_name = azurerm_virtual_network.vnet.name
  address_prefixes     = ["30.0.0.0/24"]
  service_endpoints = [ "Microsoft.AzureCosmosDB", "Microsoft.EventHub" ]
}
