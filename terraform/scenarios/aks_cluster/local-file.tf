
resource "local_file" "kube_config_file" {
  filename = format("%s/%s", path.root, "kube_config.yaml")
  sensitive_content = azurerm_kubernetes_cluster.tracking_aks.kube_config_raw
}