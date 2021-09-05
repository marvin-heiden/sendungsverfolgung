

variable "location" {
  type = string
}
variable "location_secondary" {
  type = string
}
variable "env" {
  type = string
}
variable "resource_group_name" {
  type = string
}
variable "db_name" {
  type = string
}
variable "subnet_id" {
  type = string
}
variable "account_name" {
  type = string
}
variable "enable_custom_encryption" {
  type = bool
}
variable "encryption_key_name" {
  type    = string
  default = null
}
variable "vault_name" {
  type    = string
  default = null
}
variable "ip_range_filter" {
  type    = string
  default = null
}
