

variable "name" {
  type = string
}
variable "env" {
  type = string
}
variable "location" {
  type = string
}
variable "location_secondary" {
  type = string
}
variable "kind" {
  type = string
}
variable "resource_group_name" {
  type = string
}
variable "subnet_id" {
  type = string
}
variable "encryption_key_kid" {
  type = string
  default = null
}
variable "ip_range_filter" {
  type = string
  default = null
}