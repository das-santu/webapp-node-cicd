FROM node:20-alpine

MAINTAINER Santu Das

WORKDIR /app

COPY . /app

RUN npm install

EXPOSE 3000

CMD [ "npm", "start"]
