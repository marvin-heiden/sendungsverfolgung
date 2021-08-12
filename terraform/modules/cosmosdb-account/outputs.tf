

output "account_name" {
  value = azurerm_cosmosdb_account.db-account.name
}

output "account_endpoint" {
  value = azurerm_cosmosdb_account.db-account.endpoint
}

output "account_primary_key" {
  value = azurerm_cosmosdb_account.db-account.primary_master_key
}




