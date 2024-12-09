# Start with a base image containing Java and Gradle
FROM alpine:3.7
FROM openjdk:11-jre
ADD ./build/distributions/sprintdue-1.0.tar /
WORKDIR /sprintdue-1.0/bin
COPY ./*.pl ./
COPY ./*.json ./
COPY ./*.bin ./
COPY ./*.txt ./
CMD ["sh", "./sprintdue"]
