
# Install Traefik as Ingress Provider
module "traefik" {
  depends_on = [
    azurerm_kubernetes_cluster.tracking_aks
  ]
  source    = "../../modules/traefik"
  namespace = "default"
  sniStrict = false
  custom_entrypoints = [ {
    name = "mfs"
    port = 8082
    protocol = "https"
  },
  {
    name = "mrs"
    port = 8083
    protocol = "https"
  } ]
}