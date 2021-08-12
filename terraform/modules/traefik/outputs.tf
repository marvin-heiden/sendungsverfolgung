

output "revision" {
  value = helm_release.traefik.metadata[0].revision
}

output "middleware_pass_tls_client_cert" {
  value = local.middleware_pass_tls_client_cert
}

output "middleware_security_headers" {
  value = local.middleware_security_headers
}

output "namespace" {
  value = helm_release.traefik.metadata[0].namespace
}