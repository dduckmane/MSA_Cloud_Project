# MSA_Cloud_Project

## 프로젝트 개요

- Cloud Native Architecture 의 지식을 함양하기 위한 토이 프로젝트
- 토큰 기반 로그인 진행
- 유저가 이력서를 작성 후 해당 회사의 지원자 수가 증가하는 로직
- Docker Hub : https://hub.docker.com/u/kms199711 

## Architecture (아키텍쳐 구성)

![Component 1-2](https://user-images.githubusercontent.com/108928206/229526520-56067ee4-d1dc-47e1-a689-97046013a300.png)

## Application Structure

![Untitled-12](https://user-images.githubusercontent.com/108928206/229410913-a372159a-d6b6-48ae-b6aa-2de669773f49.png)

## 적용 기술

- Java 11
- Spring Cloud Bus, Spring Cloud Config, `RabbitMQ`
- Spring Api Gateway, Eureka
- Spring Security, `JWT`
- Spring Data JPA
- `Kafka`, Zookeer
- MariaDB, H2
- Redis
- Micrometer, Prometheus, `Grafana`
- `Docker`

## Advanced Feature

- GatewayFilter 를 이용한 인가처리
- Resilience4j를 이용한 Feign Client 연쇄 오류 방지
- Kafka 를 이용한 데이터 동기화
- Docker 이용하여 배포 Prometheus & Grafana를 이용한 모니터링
- 상세 링크 : https://zinc-humerus-7d2.notion.site/Cloud-Native-Architecture-64d3cc7527bd4149bdafdd023361a1af


