
# Install Traefik as Ingress Provider
module "traefik" {
  depends_on = [
    azurerm_kubernetes_cluster.tracking_aks
  ]
  source    = "../../modules/traefik"
  namespace = "default"
  sniStrict = false
}