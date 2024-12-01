# 🍽️ Foodie Finds

**Foodie Finds** é um aplicativo de recomendação de restaurantes que permite explorar cardápios, favoritar estabelecimentos e acessar dados em tempo real. A aplicação possui uma API desenvolvida com **NestJS** para autenticação e busca de dados.

## 📸 Demonstração

![Demonstração do Aplicativo](images/FoodieFinds.gif)  

---

## 🚀 Funcionalidades

- **Exploração de Restaurantes:** Visualize uma lista de restaurantes próximos e seus respectivos cardápios.
- **Favoritar Restaurantes:** Adicione restaurantes aos favoritos para acessá-los rapidamente.
- **Autenticação Segura:** Login através da API NestJS com autenticação JWT.
- **Atualização em Tempo Real:** Dados dos restaurantes sincronizados em tempo real com o backend.

---

## 🛠️ Tecnologias Utilizadas

### **Frontend:**
- **Jetpack Compose** - Interface moderna e declarativa para Android.
- **Kotlin** - Linguagem principal para desenvolvimento Android.

### **Backend:**
- **NestJS** - Framework Node.js para criação de APIs escaláveis.
- **PostgreSQL** - Banco de dados relacional para armazenamento dos dados.

---

## 🔧 Instalação e Configuração

### 🚀 Backend::
1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/foodapp-backend.git
   cd foodapp-backend
    ```
2. Instale as dependências e inicie o backend:
   ```bash
   cd api
   yarn install
    yarn build
    yarn start
    ```
    > **Importante:**  
    > Crie um arquivo `.env` na raiz do projeto, baseado no `.env.example`. Configure as variáveis de ambiente, especialmente as informações de conexão com o banco de dados.
3. Popular o banco de dados: Para adicionar dados iniciais (restaurantes e produtos) ao banco de dados, acesse o seguinte endpoint no navegador ou ferramenta como Postman:

### 📱 Instalação e Configuração do Aplicativo (Frontend):
1. Instale o APK em um dispositivo Android:

    - Baixe o APK gerado e instale-o no seu dispositivo.
2. Configuração da URL da API: Caso queira testar o frontend em conjunto com o backend local, ajuste a URL no arquivo ApiClient.kt:

    **Localização do arquivo:**

    ```css
    FoodApp/app/src/main/java/com/example/foodapp/service/ApiClient.kt
    ```
    **Exemplo de configuração:***

    ```kotlin
    defaultRequest {
        url("http://192.168.100.6:3000/") // Altere para o IP do seu backend local
        header("Content-Type", "application/json")
    }
    ```
    > **Dica:**  
    > Certifique-se de que o dispositivo Android e a máquina rodando o backend estejam na mesma rede, e ajuste o IP para o endereço correto do servidor local.

## 💻 Uso
- Abra o aplicativo e faça o login com sua conta.
- Explore os restaurantes disponíveis.
- Visualize os cardápios de cada restaurante.
- Favorite seus restaurantes preferidos para acessá-los rapidamente
  
##  📱 Contato & Redes Sociais

[![GitHub](https://img.shields.io/badge/GitHub-Profile-%23121011?style=flat&logo=github)](https://github.com/BrunoHiago)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-%230077B5?style=flat&logo=linkedin)](https://www.linkedin.com/in/bruno-hiago/)