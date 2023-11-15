#!/bin/bash

sudo apt-get update -y
sudo apt-get upgrade -y

echo "Installing jdk"
sudo apt-get install openjdk-11-jdk -y
sudo apt-get install openjdk-21-jdk -y

# install jenkins
sudo wget -O /usr/share/keyrings/jenkins-keyring.asc \
  https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key
echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] \
  https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
  /etc/apt/sources.list.d/jenkins.list > /dev/null
sudo apt-get update -y
sudo apt-get install jenkins -y

sudo systemctl start jenkins
sudo systemctl enable jenkins