#!/bin/bash

export PATH=$PATH:/usr/local/bin
sudo yum install net-tools -y

# ###############################################################
# Docker installtion:
# Ref:  https://docs.docker.com/engine/install/centos/

sudo yum install -y yum-utils
sudo yum-config-manager \
  --add-repo \
  https://download.docker.com/linux/centos/docker-ce.repo
sudo yum install docker-ce docker-ce-cli containerd.io docker-compose-plugin \
-y
#sudo groupadd docker
sudo usermod -aG docker vagrant
sudo systemctl start docker

# #############
# Node
export PATH="/vagrant/client/app/.bin:$PATH"
curl -sL https://rpm.nodesource.com/setup_10.x | sudo bash -
sudo yum install nodejs -y

# ###############################################################
# Kubectl install
# Ref: https://kubernetes.io/ru/docs/tasks/tools/install-kubectl/

curl -LO https://storage.googleapis.com/kubernetes-release/release/`curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl && \
chmod +x ./kubectl && \
sudo mv ./kubectl /usr/local/bin/kubectl

# ###############################################################
# K3D install
# Ref: https://k3d.io/v5.4.6/

curl -s https://raw.githubusercontent.com/k3d-io/k3d/main/install.sh | bash

