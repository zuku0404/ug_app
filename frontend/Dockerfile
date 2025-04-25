FROM nginx:alpine
COPY . /usr/share/nginx/html
COPY ./config.docker.js /usr/share/nginx/html/config.js
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80