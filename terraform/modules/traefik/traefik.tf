locals {
  client_auth_secrets_provided = try(length(var.client_auth_secret_names) > 0, false)
  middleware_pass_tls_client_cert = "pass-tls-client-cert"
  middleware_security_headers = "security-headers"
}

resource "helm_release" "traefik" {
  name = "traefik"
  chart = "${path.module}/charts/traefik"
  namespace = var.namespace

  values = [
    templatefile("${path.module}/values.template", {
      custom_entrypoints = var.custom_entrypoints,
      traefik_config = var.traefik_config,
      annotations = var.annotations
    })
  ]
}

resource "helm_release" "traefik-security" {
  depends_on = [
    helm_release.traefik
  ]

  name = "traefik-security"
  namespace = var.namespace
  chart = "${path.module}/charts/traefik-security"

  set {
    name = "sniStrict"
    value = var.sniStrict
  }

  set {
    name = "clientAuth.secretNames"
    value = local.client_auth_secrets_provided ? "{${join(",", var.client_auth_secret_names)}}" : ""
  }

  set {
    name = "middleware_security_headers"
    value = local.middleware_security_headers
  }

  set {
    name = "middleware_pass_tls_client_cert"
    value = local.middleware_pass_tls_client_cert
  }
}

# resource "helm_release" "traefik" {
#   name = "traefik"
#   repository = "https://helm.traefik.io/traefik"
#   chart = "traefik"
#   namespace = var.namespace
#   version = "10.3.2"

#   values = [
#     templatefile("${path.module}/values.template", {
#       custom_entrypoints = var.custom_entrypoints,
#       traefik_config = var.traefik_config,
#       annotations = var.annotations
#     })
#   ]
# }