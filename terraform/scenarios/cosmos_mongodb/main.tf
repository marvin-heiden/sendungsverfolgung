# Local Variables
locals {
  resource_group_name = "tracking_resources"
  location            = "West Europe"
  location_secondary  = "Germany West Central"
  vnet                = "tracking_vnet"
  subnet              = "tracking_subnet"
  db_name             = "storage"
}

# Cosmos MongoDB
module "cosmosdb-mongodb" {
  source = "../../modules/cosmosdb-mongodb"

  db_name                  = local.db_name
  resource_group_name      = local.resource_group_name
  subnet_id                = data.azurerm_subnet.tracking_subnet.id
  location                 = local.location
  location_secondary       = local.location_secondary
  env                      = "dev"
  account_name             = "tracking-cosmos-account"
  enable_custom_encryption = false
  ip_range_filter          = "104.42.195.92,92.116.67.244"
}

# MongoDB Collections
module "cosmosb-mongodb-collection-trackinghistory" {
  source = "../../modules/cosmosdb-mongodb-collection"

  collection_name     = "trackingHistory"
  account_name        = module.cosmosdb-mongodb.account_name
  db_name             = module.cosmosdb-mongodb.db_name
  resource_group_name = local.resource_group_name

  throughput          = 5000
  default_ttl_seconds = "7776000"
  enable_autoscaling  = true
  shard_key           = "_id"
}

module "cosmosb-mongodb-collection-identifierlookup" {
  source = "../../modules/cosmosdb-mongodb-collection"

  collection_name     = "identifierLookup"
  account_name        = module.cosmosdb-mongodb.account_name
  db_name             = module.cosmosdb-mongodb.db_name
  resource_group_name = local.resource_group_name

  throughput          = 5000
  default_ttl_seconds = "7776000"
  enable_autoscaling  = true
  shard_key           = "_id"
}
