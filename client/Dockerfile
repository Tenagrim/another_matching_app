#
# FROM node:9.5 as builder
#
# LABEL maintainer="aevitas@aevitas.co.uk"
#
# RUN mkdir /usr/src/app
# WORKDIR /usr/src/app
#
# COPY . .
#
# ENV PATH /usr/src/app/node_modules/.bin:$PATH
#
# ADD package.json /usr/src/app/package.json
# RUN npm install
# RUN npm install -g react-scripts@1.1.1
# RUN npm run build
#
# CMD ["npm", "start"]






#DEBUG CONTAINER NEEDS BUILD CONTAINER ON RELEASE

FROM nginx:1.13.3-alpine

RUN rm -rf /usr/share/nginx/html/*

COPY config/nginx.conf /etc/nginx/conf.default/default.conf
#COPY app /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]