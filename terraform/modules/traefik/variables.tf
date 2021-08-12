

variable "namespace" {
  type = string
}

variable "custom_entrypoints" {
  type = list(
    object(
      {
        name = string
        port = number
        protocol = string
      }
    )
  )
  default = []
}

variable "traefik_config" {
  type = object(
    {
      configmap_name = string
      configmap_namespace = string
      configmap_key = string
    }
  )
  default = null
}

variable "annotations" {
  type = object(
    {
      azure-load-balancer-internal = string
      azure-load-balancer-internal-subnet = string
      azure-load-balancer-mode = string
    }
  )
  default = null
}

variable "client_auth_secret_names" {
  type = list(string)
  default = null
}
variable "sniStrict" {
  type = bool
}
