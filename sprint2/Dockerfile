# Start with a base image containing Java and Gradle
FROM openjdk:11-jre-slim

# Install Python and specify the version
RUN apt-get update && \
    apt-get install -y python3 && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Add your application files
ADD ./build/distributions/sprintdue-1.0.tar /
WORKDIR /sprintdue-1.0/bin
COPY ./*.pl ./
COPY ./*.json ./
COPY ./*.bin ./
COPY ./*.txt ./
COPY ./src/main/resources/* ./  
# Specify the command to run your application
CMD ["sh", "./sprintdue"]
