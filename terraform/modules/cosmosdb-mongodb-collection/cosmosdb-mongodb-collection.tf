


resource "azurerm_cosmosdb_mongo_collection" "mongo_collection_static" {
  name = var.collection_name
  resource_group_name = var.resource_group_name
  account_name = var.account_name
  database_name = var.db_name
  default_ttl_seconds = var.default_ttl_seconds
  shard_key = var.shard_key
  throughput = var.throughput
  count = var.enable_autoscaling ? 0 : 1

  dynamic "index" {
    for_each = var.indexes_unique

    content {
      keys = [index.key]
      unique = true
    }
  }

  dynamic "index" {
    for_each = var.indexes

    content {
      keys = [index.key]
    }
  }
}

resource "azurerm_cosmosdb_mongo_collection" "mongo_collection_autoscaled" {
  name = var.collection_name
  resource_group_name = var.resource_group_name
  account_name = var.account_name
  database_name = var.db_name
  default_ttl_seconds = var.default_ttl_seconds
  shard_key = var.shard_key
  count = var.enable_autoscaling ? 1 : 0

  autoscale_settings{
    max_throughput = var.throughput
  }

  dynamic "index" {
    for_each = var.indexes_unique

    content {
      keys = [index.key]
      unique = true
    }
  }

  dynamic "index" {
    for_each = var.indexes

    content {
      keys = [index.key]
    }
  }
}
