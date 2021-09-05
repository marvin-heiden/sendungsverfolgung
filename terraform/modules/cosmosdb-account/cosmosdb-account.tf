

resource "azurerm_cosmosdb_account" "db-account" {
  name = var.name
  location = var.location
  resource_group_name = var.resource_group_name
  offer_type = "Standard"
  kind = var.kind
  // enable_automatic_failover = true
  is_virtual_network_filter_enabled = true
  key_vault_key_id = var.encryption_key_kid
  ip_range_filter= var.ip_range_filter

  virtual_network_rule {
    id = var.subnet_id
  }

  consistency_policy {
    consistency_level = "Session"
  }

  geo_location {
    location = var.location
    failover_priority = 0
  }

  geo_location {
    location = var.location_secondary
    failover_priority = 1
  }

  // This will set the MongoDB Version to 3.6
  capabilities {
    name = "EnableMongo"
  }

  tags = {
    Name = var.name
    Environment = var.env
    Terraform = "true"
  }
}
