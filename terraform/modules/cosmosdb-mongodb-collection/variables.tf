

variable "shard_key" {
  type = string
}
variable "resource_group_name" {
  type = string
}
variable "collection_name" {
  type = string
}
variable "account_name" {
  type = string
}
variable "db_name" {
  type = string
}
variable "enable_autoscaling" {
  type = bool
}
variable "throughput" {
  type = number
}
variable "default_ttl_seconds" {
  type = string
}
variable "indexes" {
  type = set(string)
  default = []
}
variable "indexes_unique" {
  type = set(string)
  default = []
}
