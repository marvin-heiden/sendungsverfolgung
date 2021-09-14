locals {
  location            = "West Europe"
  resource_group_name = "tracking_resources"
  vnet                = "tracking_vnet"
}

# Event Hubs Namespace (corresponds to Kafka Cluster)
resource "azurerm_eventhub_namespace" "event_ns" {
  name                 = "trackingeventns"
  location             = local.location
  resource_group_name  = local.resource_group_name
  sku                  = "Standard"
  capacity             = 2
  auto_inflate_enabled = false
  zone_redundant       = false

  identity {
    type = "SystemAssigned"
  }

  tags = {
    terraform = true
  }
}

# Event Hub (corresponds to Kafka Topic)
resource "azurerm_eventhub" "input" {
  name                = "input"
  namespace_name      = azurerm_eventhub_namespace.event_ns.name
  resource_group_name = local.resource_group_name
  partition_count     = 32
  message_retention   = 3 # day(s)
}

resource "azurerm_eventhub" "error" {
  name                = "error"
  namespace_name      = azurerm_eventhub_namespace.event_ns.name
  resource_group_name = local.resource_group_name
  partition_count     = 32
  message_retention   = 7 # day(s)
}
