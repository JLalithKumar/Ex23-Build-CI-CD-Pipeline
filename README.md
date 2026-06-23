# Ex23 – Build CI/CD Pipeline with GitHub Actions, ECR, ArgoCD and EKS

## Objective

Build a complete CI/CD pipeline that:

* Builds a Spring Boot application
* Runs JUnit tests
* Creates a Docker image
* Pushes the image to Amazon ECR
* Deploys the application to Amazon EKS using ArgoCD GitOps

---

## Architecture

Spring Boot Application

↓

JUnit Testing

↓

GitHub Actions

↓

Docker Build

↓

Amazon ECR

↓

GitOps Repository

↓

ArgoCD

↓

Amazon EKS

↓

Kubernetes Deployment & Service

---

## Technologies Used

* Java 17
* Spring Boot 3
* Maven
* Docker
* GitHub Actions
* Amazon ECR
* Amazon EKS
* ArgoCD
* Kubernetes

---

## Step 1 – Build Application

```bash
mvn clean package
```

Run tests:

```bash
mvn test
```

---

## Step 2 – Build Docker Image

```bash
docker build -t lalithkumarj/payment-service:v3 .
```

Run locally:

```bash
docker run -p 8082:8080 lalithkumarj/payment-service:v3
```

Verify:

```bash
curl http://localhost:8082/
```

Expected Output:

```text
Payment Service Running
```

---

## Step 3 – Push Image to Docker Hub

```bash
docker login

docker push lalithkumarj/payment-service:v3
```

---

## Step 4 – Create ECR Repository

```bash
aws ecr create-repository ^
--repository-name payment-service ^
--region us-east-1
```

---

## Step 5 – Authenticate Docker to ECR

```bash
aws ecr get-login-password ^
--region us-east-1 ^
| docker login ^
--username AWS ^
--password-stdin 810648236794.dkr.ecr.us-east-1.amazonaws.com
```

---

## Step 6 – Tag and Push Image to ECR

```bash
docker tag lalithkumarj/payment-service:v3 ^
810648236794.dkr.ecr.us-east-1.amazonaws.com/payment-service:latest

docker push ^
810648236794.dkr.ecr.us-east-1.amazonaws.com/payment-service:latest
```

Verify:

```bash
aws ecr list-images ^
--repository-name payment-service ^
--region us-east-1
```

---

## Step 7 – GitHub Actions CI Pipeline

Workflow file:

```text
.github/workflows/cicd.yaml
```

Pipeline stages:

* Checkout Code
* Setup Java
* Run Tests
* Build Application
* Build Docker Image
* Push Docker Image

---

## Step 8 – GitOps Deployment

Deployment manifest:

```text
gitops-demo/dev/payment-service.yaml
```

Service manifest:

```text
gitops-demo/dev/service.yaml
```

Commit changes:

```bash
git add .
git commit -m "Deploy payment service"
git push origin main
```

---

## Step 9 – Verify ArgoCD

```bash
kubectl get applications -n argocd
```

Expected:

```text
SYNC STATUS   HEALTH STATUS
Synced        Healthy
```

---

## Step 10 – Verify Deployment

```bash
kubectl get pods -n dev
```

Expected:

```text
1/1 Running
```

---

## Step 11 – Verify Service

```bash
kubectl get svc -n dev
```

Expected:

```text
TYPE           EXTERNAL-IP
LoadBalancer   <aws-elb>
```

---

## Result

Successfully implemented a complete CI/CD pipeline using GitHub Actions, Docker, Amazon ECR, Amazon EKS and ArgoCD GitOps deployment.
