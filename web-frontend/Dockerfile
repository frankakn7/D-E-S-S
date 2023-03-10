# == CONFIGURE ==
FROM node:19-alpine
WORKDIR /app
COPY . .
#== BUILD ==
RUN npm install
RUN npm run build
#== RUN ==
ENV NODE_ENV production
EXPOSE 3000
CMD ["npx","serve","build"]