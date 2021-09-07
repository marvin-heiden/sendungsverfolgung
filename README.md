# BA-Projekt: Sendungsverfolgungssystem

![Architekturdiagramm](./architecture.png)

For deployment in AKS-cluster:

0. Create new App registration in Azure Portal

1. Set Azure environment:
    cd terraform/scripts
    sh set_environment_variables.sh <subscription_id> <client_id> <client_secret> <tenant_id>

2. Log in to Azure account:
    az login

3. Deploy infrastructure:
    cd terraform/<global / scenarios>
    cd <component>
    terraform init
    terraform apply --auto-approve

4. Compile Project:
    cd Message<Read / Filter>Service
    mvn clean package

5. Make Docker image and push to Azure container registry
    docker build -t <registry_name>.azurecr.io/message-<read / filter>-service .
    az acr login -n <registry_name>
    docker push <registry_name>.azurecr.io/message-<read / filter>-service

6. Get Kubeconfig for created cluster
    az aks get-credentials --resource-group tracking_resources --name tracking-aks-cluster-<random_number>

7. Install services into AKS-cluster with Helm:
    (OPTIONAL) set new user credentials in <mfs / mrs>-secret:
        htpasswd -nbBC 10 mrs-admin mrs-password
    helm install message-<read / filter>-service helm/message<Read / Filter>Service

8. Generate TLS-secret for HTTPS encryption:
    OpenSSL genrsa -out ca.key 2048
    openssl req -x509 -new -nodes -days 365 -key ca.key -out ca.crt -subj "/CN=<domain_name>"
    kubectl create secret tls traefik-tls-cert --key ca.key --cert ca.crt

