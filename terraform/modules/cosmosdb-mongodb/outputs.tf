

output "account_name" {
  value = module.db-account.account_name
}
output "db_name" {
  value = azurerm_cosmosdb_mongo_database.mongodb.name
}
output "resource_group_name" {
  value = azurerm_cosmosdb_mongo_database.mongodb.resource_group_name
}

output "account_endpoint" {
  value = module.db-account.account_endpoint
}

output "account_primary_key" {
  sensitive = true
  value = module.db-account.account_primary_key
}
