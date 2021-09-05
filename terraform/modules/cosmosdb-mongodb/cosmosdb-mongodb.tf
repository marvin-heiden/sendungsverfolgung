

module "db-account" {
  source              = "../cosmosdb-account"
  env                 = var.env
  kind                = "MongoDB"
  location            = var.location
  location_secondary  = var.location_secondary
  name                = var.account_name
  resource_group_name = var.resource_group_name
  subnet_id           = var.subnet_id
  encryption_key_kid  = var.enable_custom_encryption ? "https://${var.vault_name}.vault.azure.net/keys/${var.encryption_key_name}" : null
  ip_range_filter     = var.ip_range_filter
}


resource "azurerm_cosmosdb_mongo_database" "mongodb" {
  name                = var.db_name
  resource_group_name = var.resource_group_name
  account_name        = module.db-account.account_name
}
