FROM nginx:alpine
COPY ./config.docker.js ./config.js
COPY . /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80