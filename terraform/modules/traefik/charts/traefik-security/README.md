This local helm chart's sole purpose is to install a Traefik Resources for increased security:
 
- A Traefik Middleware named `pass-tls-client-cert ` which can be referenced in order to instruct Traefik to pass the Subject CN to the Backend
- Several Traefik TLSOptions for TLS Configuration 