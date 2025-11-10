FROM node:18

WORKDIR /app

ENV NODE_OPTIONS=--openssl-legacy-provider

# Copia apenas arquivos de dependência primeiro (melhora cache)
COPY package*.json ./

# Instala dependências com flag para evitar conflitos
RUN npm install --legacy-peer-deps

# Copia o restante do código
COPY . .

EXPOSE 5173

# Melhor forma: adicionar "--host" direto no package.json (script dev)
CMD ["npm", "run", "dev"]
